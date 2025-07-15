/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import DAO.RefundDAO;
import Models.Refund;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Base64;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
public class UpdateRefundSellerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int refundId = Integer.parseInt(request.getParameter("id"));
        RefundDAO dao = new RefundDAO();
        Refund refund = dao.getRefundById(refundId);
        request.setAttribute("refund", refund);
        request.getSession().setAttribute("previousImage", refund.getProofImage());
        request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        try {
            int refundId = Integer.parseInt(request.getParameter("refundId"));
            String orderIdStr = request.getParameter("orderId");
            String accountHolderName = request.getParameter("accountHolderName");
            String bankName = request.getParameter("bankName");
            String bankAccountNumber = request.getParameter("bankAccountNumber");
            String refundReasonDescription = request.getParameter("refundReasonDescription");

            RefundDAO dao = new RefundDAO();
            OrderDAO orderDAO = new OrderDAO();

            request.setAttribute("refundId", refundId);
            request.setAttribute("orderId", orderIdStr);
            request.setAttribute("accountHolderName", accountHolderName);
            request.setAttribute("bankName", bankName);
            request.setAttribute("bankAccountNumber", bankAccountNumber);
            request.setAttribute("refundReasonDescription", refundReasonDescription);

            Part imagePart = request.getPart("proofImage");
            byte[] proofImage = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                try (InputStream is = imagePart.getInputStream()) {
                    proofImage = is.readAllBytes();
                }
                String preview = Base64.getEncoder().encodeToString(proofImage);
                request.setAttribute("preview", preview);
            } else {
                proofImage = (byte[]) session.getAttribute("previousImage");
                String preview = Base64.getEncoder().encodeToString(proofImage);
                request.setAttribute("preview", preview);
            }

            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                request.setAttribute("errMess", "Mã đơn hàng không được để trống.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdStr.trim());
                if (orderId <= 0) {
                    request.setAttribute("errMess", "Mã đơn hàng phải là số nguyên dương.");
                    request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errMess", "Mã đơn hàng phải là số.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }

            if (orderDAO.getOrderById(orderId) == null) {
                request.setAttribute("errMess", "Mã đơn hàng không tồn tại.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }
            
            String orderStatus = orderDAO.getOrderById(orderId).getOrderStatus();
            String paymentStatus = orderDAO.getOrderById(orderId).getPaymentStatus();
            if (!(orderStatus.equals("Cancelled") || orderStatus.equals("Rejected")) || !paymentStatus.equals("Paid")) {
                request.setAttribute("errMess", "Đơn hàng này chưa được hủy hoặc chưa được thanh toán");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }

            String nameRegex = "^[a-zA-ZÀ-ỹ\\s]+$";
            if (accountHolderName == null || !accountHolderName.matches(nameRegex)) {
                request.setAttribute("errMess", "Họ tên chủ tài khoản không hợp lệ.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }
            if (bankName == null || !bankName.matches(nameRegex)) {
                request.setAttribute("errMess", "Tên ngân hàng không hợp lệ.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }
            if (bankAccountNumber == null || !bankAccountNumber.matches("^[a-zA-Z0-9]{6,30}$")) {
                request.setAttribute("errMess", "Số tài khoản không hợp lệ.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }
            if (refundReasonDescription == null || refundReasonDescription.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn phải nhập lý do hoàn tiền.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }

            double total = orderDAO.getOrderPriceById(orderId);
            Double discountAmount = orderDAO.getDiscountAmountAtApply(orderId);
            double refundAmount = 0;
            if (discountAmount != null) {
                refundAmount = total - discountAmount;
            } else {
                refundAmount = total;
            }

            Refund updatedRefund = new Refund();
            updatedRefund.setRefundId(refundId);
            updatedRefund.setOrderId(orderId);
            updatedRefund.setAccountHolderName(accountHolderName);
            updatedRefund.setBankName(bankName);
            updatedRefund.setBankAccountNumber(bankAccountNumber);
            updatedRefund.setRefundAmount(refundAmount);
            updatedRefund.setProofImage(proofImage);
            updatedRefund.setRefundReasonDescription(refundReasonDescription);
            
            if (dao.getRefundByOrderId(orderId) != null && dao.getRefundByOrderId(orderId).getOrderId() != updatedRefund.getOrderId() ) {
                request.setAttribute("errMess", "Đơn hàng này đã được gửi yêu cầu hoàn tiền.");
                request.getRequestDispatcher("update_refund_seller.jsp").forward(request, response);
                return;
            }

            boolean success = dao.updateRefundSeller(updatedRefund);

            if (success) {
                session.setAttribute("successMess", "Cập nhật thành công.");
            } else {
                session.setAttribute("errMess", "Không thể cập nhật.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.removeAttribute("previousImage");
        response.sendRedirect("displayrefund");
    }
}
