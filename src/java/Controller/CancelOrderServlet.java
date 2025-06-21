/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DAO.DBContext;
import DAO.NotificationDAO;
import DAO.OrderDAO;
import Model.Notification;
import Model.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import utils.NotificationUtils;

/**
 *
 * @author QuangAnh
 */
@WebServlet(name = "CancelOrderServlet", urlPatterns = {"/cancelOrder"})
public class CancelOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet CancelOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CancelOrderServlet at " + request.getContextPath() + "</h1>");
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
        String rawOrderId = request.getParameter("orderId");

        HttpSession session = request.getSession();
        Integer accId = (Integer) session.getAttribute("accId");
        if (accId == null) {
            accId = 1; // Gán mặc định khi test
            session.setAttribute("accId", accId);
        }

        if (rawOrderId == null || rawOrderId.isEmpty()) {
            request.setAttribute("error", "Thiếu mã đơn hàng.");
        } else {
            try {
                int orderId = Integer.parseInt(rawOrderId);
                OrderDAO dao = new OrderDAO();

                boolean success = dao.cancelOrderByOrderId(orderId);  // vẫn gọi cập nhật DB

                if (success) {
                    // Gửi thông báo nếu thành công
                    NotificationUtils.sendNotification(
                            accId, 0,
                            "Huỷ đơn hàng",
                            "Đơn hàng #" + orderId + " đã được huỷ.",
                            "order"
                    );

                    // ✅ Ẩn đơn hàng vừa huỷ khỏi bảng hiển thị bằng session
                    List<Integer> hiddenOrders = (List<Integer>) session.getAttribute("hiddenOrders");
                    if (hiddenOrders == null) {
                        hiddenOrders = new ArrayList<>();
                    }
                    hiddenOrders.add(orderId);
                    session.setAttribute("hiddenOrders", hiddenOrders);

                    request.setAttribute("message", "Đơn hàng đã được huỷ thành công");
                } else {
                    request.setAttribute("error", "Không thể huỷ đơn hàng. Có thể đơn đã được xử lý.");
                }

                // Load lại danh sách đơn hàng
                List<Order> orderList = dao.getAllOrders(); // bạn có thể dùng getByAccount(accId) nếu muốn

                // ✅ Lọc bỏ các đơn đã được “ẩn”
                List<Integer> hiddenOrders = (List<Integer>) session.getAttribute("hiddenOrders");
                if (hiddenOrders != null) {
                    orderList.removeIf(order -> hiddenOrders.contains(order.getOrderId()));
                }

                request.setAttribute("orderList", orderList);
                request.getRequestDispatcher("Order.jsp").forward(request, response);

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Mã đơn hàng không hợp lệ.");
                request.getRequestDispatcher("Order.jsp").forward(request, response);
            }

        }

//        NotificationDAO notiDAO = new NotificationDAO();
//        Notification n = new Notification();
//        n.setAccId(orderId); // hoặc người dùng đang đăng nhập
//        n.setTitle("Hủy đơn hàng");
//        n.setMessage("Đơn hàng #" + orderId + " đã được hủy.");
//        n.setNotifType("order");
//        notiDAO.addNotification(n);
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
