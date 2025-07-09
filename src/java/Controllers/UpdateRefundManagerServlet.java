/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import DAO.RefundDAO;
import Models.Account;
import Models.Refund;
import Utils.EmailSender;
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
import java.sql.Timestamp;
import java.util.Base64;

/**
 *
 * @author ADMIN
 */
@MultipartConfig
public class UpdateRefundManagerServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateRefundManagerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRefundManagerServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        int refundId = Integer.parseInt(request.getParameter("id"));
        RefundDAO dao = new RefundDAO();
        Refund refund = dao.getRefundById(refundId);
        request.setAttribute("refund", refund);
        if (refund.getProofImage() != null) {
            String previousImage = Base64.getEncoder().encodeToString(refund.getProofImage());
            session.setAttribute("previousImage", previousImage);
        }

        request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("userAccount");
        String role = acc.getAccRole();

        try {
            int refundId = Integer.parseInt(request.getParameter("refundId"));
            String orderIdStr = request.getParameter("orderId");
            int orderId=Integer.parseInt(orderIdStr);
            String accountHolderName = request.getParameter("accountHolderName");
            String bankName = request.getParameter("bankName");
            String bankAccountNumber = request.getParameter("bankAccountNumber");
            String refundStatus = request.getParameter("refundStatus");
            double refundAmount = Double.parseDouble(request.getParameter("refundAmount"));
            String refundReasonDescription = request.getParameter("refundReasonDescription");
            RefundDAO dao = new RefundDAO();
            OrderDAO orderDAO = new OrderDAO();
            request.setAttribute("refundId", refundId);
            request.setAttribute("orderId", orderIdStr);
            request.setAttribute("accountHolderName", accountHolderName);
            request.setAttribute("bankName", bankName);
            request.setAttribute("bankAccountNumber", bankAccountNumber);
            request.setAttribute("refundAmount", refundAmount);
            request.setAttribute("refundStatus", refundStatus);
            request.setAttribute("refundReasonDescription", refundReasonDescription);

            String previousStatus = dao.getRefundById(refundId).getRefundStatus();
            if ("Approved".equalsIgnoreCase(previousStatus) && !"Completed".equalsIgnoreCase(refundStatus)) {
                request.setAttribute("errMess", "Yêu cầu đã duyệt chỉ được phép chuyển sang trạng thái 'Completed'.");
                request.setAttribute("refundStatus", previousStatus);
                request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
                return;
            }
            
            if (!"Approved".equalsIgnoreCase(previousStatus) && "Completed".equalsIgnoreCase(refundStatus)) {
                request.setAttribute("errMess", "Vui lòng chuyển trạng thái sang 'Approved' trước khi sang 'Completed' ");
                request.setAttribute("refundStatus", previousStatus); 
                request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
                return;
            }
            

            String rejectReason = request.getParameter("rejectReason");
            if ("Rejected".equalsIgnoreCase(refundStatus)) {
                if (rejectReason == null || rejectReason.trim().isEmpty()) {
                    request.setAttribute("errMess", "Bạn phải nhập lý do từ chối.");
                    request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
                    return;
                }
            }
            byte[] proofRefundedImage = null;
            if ("Manager".equalsIgnoreCase(role) && "Completed".equalsIgnoreCase(refundStatus)) {
                Part refundedImagePart = request.getPart("proofRefundedImage");
                if (refundedImagePart == null || refundedImagePart.getSize() == 0) {
                    request.setAttribute("errMess", "Khi chuyển sang 'Completed', bắt buộc phải tải ảnh hoàn tiền.");
                    request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
                    return;
                } else {
                    String contentType = refundedImagePart.getContentType();
                    if (contentType == null || !contentType.toLowerCase().matches("image/(jpeg|jpg|png|webp)")) {
                        request.setAttribute("errMess", "Ảnh hoàn tiền chỉ chấp nhận JPG, JPEG, PNG hoặc WEBP.");
                        request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
                        return;
                    }
                    if (refundedImagePart.getSize() > 5 * 1024 * 1024) {
                        request.setAttribute("errMess", "Ảnh hoàn tiền không được lớn hơn 5MB.");
                        request.getRequestDispatcher("update_refund_manager.jsp").forward(request, response);
                        return;
                    }
                    try (InputStream is = refundedImagePart.getInputStream()) {
                        proofRefundedImage = is.readAllBytes();
                    }
                }
            } else {
                proofRefundedImage = dao.getRefundById(refundId).getProofRefundedImage();
            }

            Timestamp processedDate = null;
            if ("Manager".equalsIgnoreCase(role) && !refundStatus.equalsIgnoreCase(previousStatus)) {
                processedDate = new Timestamp(System.currentTimeMillis());
            }

            Refund updatedRefund = new Refund();
            updatedRefund.setRefundId(refundId);
            updatedRefund.setRefundStatus(refundStatus);
            updatedRefund.setRefundProcessedDate(processedDate);
            updatedRefund.setProofRefundedImage(proofRefundedImage);
            updatedRefund.setRejectReason("Rejected".equalsIgnoreCase(refundStatus) ? rejectReason : null);

            boolean success = dao.updateRefundManager(updatedRefund);

            if (success) {
                session.setAttribute("successMess", "Cập nhật hoàn tiền thành công.");
                if (!refundStatus.equalsIgnoreCase(previousStatus)) {
                    String customerEmail = orderDAO.getOrderById(orderId).getCustomerEmail();
                    if (customerEmail != null && !customerEmail.isEmpty()) {
                        if (!"Rejected".equalsIgnoreCase(refundStatus)) {
                            rejectReason = null;
                        }
                        EmailSender.sendRefundStatusUpdate(
                                customerEmail,
                                Integer.parseInt(orderIdStr),
                                refundStatus,
                                rejectReason,
                                proofRefundedImage
                        );
                    }
                }

            } else {
                session.setAttribute("errMess", "Không thể lưu cập nhật yêu cầu hoàn tiền.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errMess", "Đã xảy ra lỗi xử lý dữ liệu.");
        }
        session.removeAttribute("previousImage");
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
