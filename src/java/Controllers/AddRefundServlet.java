/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.DiscountDAO;
import DAO.OrderDAO;
import DAO.RefundDAO;
import Models.Discount;
import Models.Order;
import Models.Refund;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;

/**
 *
 * @author ADMIN
 */
@MultipartConfig
public class AddRefundServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddRefundServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddRefundServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        OrderDAO _dao = new OrderDAO();
        RefundDAO dao = new RefundDAO();
        DiscountDAO discountDAO = new DiscountDAO();
        String preview = null;
        HttpSession session = request.getSession();

        try {
            String orderIdStr = request.getParameter("orderId");
            String accountHolderName = request.getParameter("accountHolderName");
            String bankName = request.getParameter("bankName");
            String bankAccountNumber = request.getParameter("bankAccountNumber");
            String refundReasonDescription = request.getParameter("refundReasonDescription");
            Part imagePart = request.getPart("proofImage");
            byte[] imageBytes = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                try (InputStream is = imagePart.getInputStream()) {
                    imageBytes = is.readAllBytes();
                }
                preview = java.util.Base64.getEncoder().encodeToString(imageBytes);
                session.setAttribute("preview", preview);
                
            } else {
                preview = (String) session.getAttribute("preview");
                if (preview != null && !preview.isEmpty()) {
                    imageBytes = java.util.Base64.getDecoder().decode(preview);
                   session.setAttribute("preview", preview);
                }
            }
            request.setAttribute("orderId", orderIdStr);
            request.setAttribute("accountHolderName", accountHolderName);
            request.setAttribute("bankName", bankName);
            request.setAttribute("bankAccountNumber", bankAccountNumber);
            request.setAttribute("refundReasonDescription", refundReasonDescription);

            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                request.setAttribute("errMess", "Mã đơn hàng không được để trống.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdStr.trim());
                if (orderId <= 0) {
                    request.setAttribute("errMess", "Mã đơn hàng phải là số nguyên dương.");
                    request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errMess", "Mã đơn hàng phải là số.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }

            String nameRegex = "^[a-zA-ZÀ-ỹ\\s]+$";
            if (accountHolderName == null || !accountHolderName.matches(nameRegex)) {
                request.setAttribute("errMess", "Họ tên chủ tài khoản không được chứa số hoặc ký tự đặc biệt.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }
            if (bankName == null || !bankName.matches(nameRegex)) {
                request.setAttribute("errMess", "Tên ngân hàng không hợp lệ (chỉ chứa chữ cái và khoảng trắng).");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }

            if (bankAccountNumber == null || !bankAccountNumber.matches("^[a-zA-Z0-9]{6,30}$")) {
                request.setAttribute("errMess", "Số tài khoản phải gồm 6–30 ký tự chữ/số.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }

            if (imageBytes == null) {
                request.setAttribute("errMess", "Vui lòng tải lên hình ảnh chứng minh đã thanh toán.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }

            if (_dao.getOrderById(orderId) == null) {

                request.setAttribute("errMess", "Mã đơn hàng không tồn tại trong hệ thống.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }
            double total = _dao.getOrderPriceById(orderId);
            Double discountAmount = _dao.getDiscountAmountAtApply(orderId);
            double refundAmount = 0;
            if (discountAmount != null) {
                refundAmount = total - discountAmount;
            } else {
                refundAmount = total;
            }

            if (dao.getRefundByOrderId(orderId) != null) {
                request.setAttribute("errMess", "Đơn hàng này đã được gửi yêu cầu hoàn tiền.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }
            String orderStatus = _dao.getOrderById(orderId).getOrderStatus();
            String paymentStatus = _dao.getOrderById(orderId).getPaymentStatus();
            if (!(orderStatus.equals("Cancelled") || orderStatus.equals("Rejected")) || !paymentStatus.equals("Paid")) {
                request.setAttribute("errMess", "Đơn hàng này chưa được hủy hoặc chưa được thanh toán");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }
            if (refundReasonDescription == null || refundReasonDescription.trim().length() < 5) {
                request.setAttribute("errMess", "Lý do hoàn tiền phải ít nhất 5 ký tự.");
                request.getRequestDispatcher("add_refund.jsp").forward(request, response);
                return;
            }
            Refund refund = new Refund();
            refund.setOrderId(orderId);
            refund.setBankAccountNumber(bankAccountNumber);
            refund.setBankName(bankName);
            refund.setAccountHolderName(accountHolderName);
            refund.setRefundAmount(refundAmount);
            refund.setProofImage(imageBytes);
            refund.setRefundStatus("Pending"); 
            refund.setRefundReasonDescription(refundReasonDescription);

            boolean success = dao.insertRefund(refund);
            if (success) {
                request.getSession().setAttribute("successMess", "Gửi yêu cầu hoàn tiền thành công.");
            } else {
                request.getSession().setAttribute("errMess", "Không thể gửi yêu cầu hoàn tiền.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errMess", "Lỗi xử lý dữ liệu.");
        }
        session.removeAttribute("preview");
        response.sendRedirect("displayrefund");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
