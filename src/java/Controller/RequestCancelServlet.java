/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderDAO;
import Model.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import utils.NotificationUtils;

/**
 *
 * @author QuangAnh
 */

public class RequestCancelServlet extends HttpServlet {
private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }
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
            out.println("<title>Servlet RequestCancelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RequestCancelServlet at " + request.getContextPath() + "</h1>");
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
        
         String rawId = request.getParameter("orderId");

        HttpSession session = request.getSession();
        Integer accId = (Integer) session.getAttribute("accId");

        if (accId == null) {
            accId = 1; // Gán mặc định để test nếu chưa login
            session.setAttribute("accId", accId);
        }

        if (rawId == null || rawId.isEmpty()) {
            request.setAttribute("error", "Thiếu mã đơn hàng.");
        } else {
            try {
                int orderId = Integer.parseInt(rawId);
                OrderDAO dao = new OrderDAO();

                boolean success = dao.requestCancelOrder(orderId);

                if (success) {
                    // Gửi thông báo nếu thành công
                    NotificationUtils.sendNotification(
                        accId, 0,
                        "Yêu cầu huỷ đơn hàng",
                        "Bạn đã gửi yêu cầu huỷ cho đơn hàng #" + orderId,
                        "order"
                    );

                    request.setAttribute("message", "Đã gửi yêu cầu hủy đơn hàng.");
                } else {
                    request.setAttribute("error", "Không thể gửi yêu cầu hủy. Đơn chưa thanh toán hoặc đã xử lý.");
                }

                // Load lại danh sách đơn hàng
                List<Order> orderList = dao.getAllOrders();
                request.setAttribute("orderList", orderList);

                // Forward lại trang hiển thị đơn hàng
                request.getRequestDispatcher("Order.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Mã đơn hàng không hợp lệ.");
                request.getRequestDispatcher("Order.jsp").forward(request, response);
            }
        }
    
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
