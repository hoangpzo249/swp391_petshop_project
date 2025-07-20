/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.DiscountDAO;
import DAO.InvoiceDAO;
import DAO.OrderDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Order;
import Models.Pet;
import Utils.EmailSender;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class VnPayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseCode = req.getParameter("vnp_ResponseCode");
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("guestName");
        String email = (String) session.getAttribute("email");
        String phone = (String) session.getAttribute("guestPhone");
        String address = (String) session.getAttribute("guestAddress");
        String discountCode = (String) session.getAttribute("discountCode");
        Account account = (Account) session.getAttribute("userAccount");
        Integer accId = (account != null) ? account.getAccId() : null;
        String[] selectedPetIds = (String[]) session.getAttribute("selectedPets");
        double totalPrice = (Double) session.getAttribute("totalPrice");
        double discountAmount = (Double) session.getAttribute("discountAmount");
        PetDAO petDao = new PetDAO();
        boolean available = true;
        if ("00".equals(responseCode)) {
            if (selectedPetIds != null) {
                for (String petIdStr : selectedPetIds) {
                    int petId = Integer.parseInt(petIdStr);
                    Pet pet = petDao.getPetById(petId);

                    if (pet.getPetAvailability() == 0 || pet.getPetStatus() == 0) {
                        available = false;
                        break;

                    }

                }
            }
            if (!available) {
                OrderDAO dao = new OrderDAO();
                Order order = new Order();
                order.setAccId(accId);
                order.setCustomerName(name.trim());
                order.setCustomerEmail(email.trim());
                order.setCustomerPhone(phone.trim());
                order.setCustomerAddress(address.trim());
                order.setShipperId(null);
                order.setPaymentMethod("Credit Card");
                order.setPaymentStatus("Paid");
                order.setOrderStatus("Rejected");

                if (discountCode != null && !discountCode.trim().isEmpty()) {
                    DiscountDAO ddao = new DiscountDAO();
                    Integer discountId = ddao.getActiveDiscountByCode(discountCode).getDiscountId();
                    order.setDiscountId(discountId);
                    order.setDiscountAmountAtApply(discountAmount);
                    ddao.increaseUsageCount(discountCode);
                } else {
                    order.setDiscountId(null);
                }

                int orderId = dao.addOrder(order);

                if (selectedPetIds != null) {
                    for (int i = 0; i < selectedPetIds.length; i++) {
                        int petId = Integer.parseInt(selectedPetIds[i]);
                        dao.addOrderContent(orderId, petId);
                    }
                }

                EmailSender.sendRejectOrderRefund(email, orderId, totalPrice);
                session.removeAttribute("selectedPets");
                session.removeAttribute("discountAmount");
                session.removeAttribute("amount");
                session.removeAttribute("discountCode");

                req.setAttribute("unavailableMessage", "Đơn hàng đã được ghi nhận nhưng thú cưng không còn khả dụng. Chúng tôi sẽ hoàn tiền trong thời gian sớm nhất.");
                req.getRequestDispatcher("pet_unavailable.jsp").forward(req, resp);
                return;
            } else {
                OrderDAO dao = new OrderDAO();
                Order order = new Order();
                order.setAccId(accId);
                order.setCustomerName(name);
                order.setCustomerEmail(email);
                order.setCustomerPhone(phone);
                order.setCustomerAddress(address);
                order.setShipperId(null);
                order.setPaymentMethod("Credit Card");
                order.setPaymentStatus("Paid");
                order.setOrderStatus("Pending");

                if (discountCode != null && !discountCode.trim().isEmpty()) {
                    DiscountDAO ddao = new DiscountDAO();
                    Integer discountId = ddao.getActiveDiscountByCode(discountCode).getDiscountId();
                    order.setDiscountId(discountId);
                    order.setDiscountAmountAtApply(discountAmount);
                    ddao.increaseUsageCount(discountCode);
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
                InvoiceDAO invoiceDAO = new InvoiceDAO();
                invoiceDAO.addInvoice(orderId, "Credit Card");
                EmailSender.sendConfirmOrderCustomer(email, orderId, orderedPets, discountAmount, totalPrice);
                session.removeAttribute("selectedPets");
                session.removeAttribute("discountAmount");
                session.removeAttribute("amount");
                session.removeAttribute("discountCode");
                req.setAttribute("status", "success");
                req.setAttribute("orderId", orderId);
                req.setAttribute("method", "Chuyển khoản ngân hàng");
                Date now = new Date();
                String orderDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(now);
                req.setAttribute("date", orderDate);
                req.getRequestDispatcher("confirm.jsp").forward(req, resp);

            }
        } else {

            req.setAttribute("status", "fail");
            session.removeAttribute("selectedPets");
            session.removeAttribute("discountAmount");
            session.removeAttribute("amount");
            session.removeAttribute("discountCode");
            resp.sendRedirect("homepage");

        }

    }
}
