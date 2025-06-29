/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DiscountDAO;
import DAO.OrderDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Discount;
import Models.Order;
import Models.Pet;
import Utils.Config;
import Utils.EmailSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author CTT VNPAY
 */
public class purchaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        Integer accId = account != null ? account.getAccId() : null;
        String paymentMethod = (String) session.getAttribute("paymentMethod");
        String name = (String) session.getAttribute("guestName");
        String email = (String) session.getAttribute("email");
        String phone = (String) session.getAttribute("guestPhone");
        String address = (String) session.getAttribute("guestAddress");
        String[] selectedPetIds = (String[]) session.getAttribute("selectedPets");
        double totalPrice = Double.parseDouble((String) session.getAttribute("amount"));
        double discountAmount = Double.parseDouble((String) session.getAttribute("discountAmount"));
        String discountCode = (String) session.getAttribute("discountCode");

        session.setAttribute("guestName", name);
        session.setAttribute("email", email);
        session.setAttribute("guestPhone", phone);
        session.setAttribute("guestAddress", address);
        session.setAttribute("accId", accId);

        PetDAO petDao = new PetDAO();
        boolean available = true;
        if (selectedPetIds != null) {
            for (String petIdStr : selectedPetIds) {
                int petId = Integer.parseInt(petIdStr);
                Pet pet = petDao.getPetById(petId);

                if (pet == null || pet.getPetAvailability() == 0 || pet.getPetStatus() == 0) {
                    available = false;
                    break;

                }

            }
        }
        if (!available) {
            session.removeAttribute("selectedPets");
            session.removeAttribute("discountAmount");
            session.removeAttribute("amount");
            session.removeAttribute("discountCode");
            req.getRequestDispatcher("pet_unavailable.jsp").forward(req, resp);
            return;
        }

        if ("cod".equals(paymentMethod)) {

            OrderDAO dao = new OrderDAO();
            Order order = new Order();
            order.setAccId(accId);
            order.setCustomerName(name);
            order.setCustomerEmail(email);
            order.setCustomerPhone(phone);
            order.setCustomerAddress(address);
            order.setShipperId(null);
            order.setPaymentMethod("Cash on Delivery");
            order.setPaymentStatus("Unpaid");
            order.setOrderStatus("Pending");

            if (discountCode != null && !discountCode.trim().isEmpty()) {
                DiscountDAO ddao = new DiscountDAO();
                Integer discountId = ddao.getActiveDiscountByCode(discountCode).getDiscountId();
                order.setDiscountId(discountId);
                order.setDiscountAmountAtApply(discountAmount);
                ddao.increaseUsageCount(discountCode);
                Discount d = ddao.getDiscountById(discountId);
                if ((d.getUsageCount() >= d.getMaxUsage()) && d.isActive()) {
                    ddao.updateStatus(discountId);
                }
            } else {
                order.setDiscountId(null);
            }
            int orderId = dao.addOrder(order);
            List<Pet> orderedPets = new ArrayList<>();
            if (selectedPetIds != null) {
                for (int i = 0; i < selectedPetIds.length; i++) {
                    int petId = Integer.parseInt(selectedPetIds[i]);
                    dao.addOrderContent(orderId, petId);
                    new PetDAO().updatePetAvailability(petId, 0);
                    Pet p = petDao.getPetById(petId);
                    orderedPets.add(p);
                }
            }
            EmailSender.sendConfirmOrderCustomer(email, orderId, orderedPets, discountAmount, totalPrice);

            req.setAttribute("status", "success");
            req.setAttribute("orderId", orderId);
            req.setAttribute("method", "Trả tiền mặt khi nhận hàng");
            Date now = new Date();
            String orderDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(now);
            req.setAttribute("date", orderDate);
            session.removeAttribute("selectedPets");
            session.removeAttribute("discountAmount");
            session.removeAttribute("amount");
            session.removeAttribute("discountCode");
            req.getRequestDispatcher("confirm.jsp").forward(req, resp);
        } else if ("bank".equals(paymentMethod)) {
            session.setAttribute("guestName", name);
            session.setAttribute("guestPhone", phone);
            session.setAttribute("guestAddress", address);
            session.setAttribute("email", email);
            session.setAttribute("discountCode", discountCode);
            session.setAttribute("totalPrice", totalPrice);
            session.setAttribute("discountAmount", discountAmount);
            session.setAttribute("selectedPetIds", selectedPetIds);

            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            long amount = (long) (totalPrice * 100);
            String bankCode = req.getParameter("bankCode");
            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = Config.getIpAddress(req);

            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }

            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);

            vnp_Params.put("vnp_OrderType", orderType);

            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();

            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && fieldValue.length() > 0) {
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            query.append("&vnp_SecureHash=").append(vnp_SecureHash);
            String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
            System.out.println("Redirecting to VNPAY: " + paymentUrl);

            resp.sendRedirect(paymentUrl);
        }

    }
}
