/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.InvoiceDAO;
import DAO.OrderDAO;
import Models.Account;
import Models.Invoice;
import Models.Order;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author HuyHoang
 */
public class Customer_Order_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Customer_Order_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Customer_Order_Servlet at " + request.getContextPath() + "</h1>");
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
        String status = request.getParameter("status");
        String keyword = request.getParameter("keyword");
        String view = request.getParameter("view");

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        Account acc = (Account) session.getAttribute("userAccount");
        int accId = acc.getAccId();
//        System.err.println(accId);
        OrderDAO odao = new OrderDAO();
        InvoiceDAO _invoicedao = new InvoiceDAO();
        if (status != null && keyword != null && !keyword.trim().isEmpty()) {
            try {
                int key = Integer.parseInt(keyword);
                List<Order> orderList = odao.getOrderCusSearch(accId, status, key);
                List<Order> orderPet = odao.getOrderCusPet(accId, status);

                request.setAttribute("orderList", orderList);
                request.setAttribute("orderPet", orderPet);
                request.setAttribute("keyword", keyword);
                request.setAttribute("status", status);
                request.getRequestDispatcher("customer_order_page.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Tìm kiếm theo Id chỉ chấp nhận dạng chữ số");
                List<Order> orderList = odao.getOrderCus(accId, status);
                request.setAttribute("orderList", orderList);
                request.setAttribute("status", status);
                request.getRequestDispatcher("customer_order_page.jsp").forward(request, response);
            }
        } else if (status != null && view != null) {
            int orderId = Integer.parseInt(view);
            Order order = odao.getOrderCusDetail(accId, status, orderId);
            Invoice invoice = _invoicedao.getInvoiceDetailById(_invoicedao.getInvoiceIdByOrderId(orderId));
            request.setAttribute("invoice", invoice);

            List<Order> orderPet = odao.getOrderCusPetDetail(accId, status, orderId);
            request.setAttribute("orderDetail", order);
            request.setAttribute("orderPet", orderPet);
            request.getRequestDispatcher("customer_orderDetail_page.jsp").forward(request, response);
        } else if ("Pending".equals(status) && "Cancelled".equals(action) && id != null) {
            int orderId = Integer.parseInt(id);
            Order order = odao.getOrderCusDetail(accId, status, orderId);
            List<Order> orderPet = odao.getOrderCusPetDetail(accId, status, orderId);
            request.setAttribute("orderDetail", order);
            request.setAttribute("orderPet", orderPet);
            request.getRequestDispatcher("customer_orderCancelled_page.jsp").forward(request, response);
        } else {
            List<Order> orderList = odao.getOrderCus(accId, status);

            List<Order> orderPet = odao.getOrderCusPet(accId, status);

            request.setAttribute("orderList", orderList);
            request.setAttribute("orderPet", orderPet);

            request.setAttribute("status", status);
            request.getRequestDispatcher("customer_order_page.jsp").forward(request, response);
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
        String status = request.getParameter("status");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("userAccount");
        OrderDAO odao = new OrderDAO();
        int accId = acc.getAccId();

        if ("Pending".equals(status) && "Cancelled".equals(action) && id != null) {
            int orderId = Integer.parseInt(id);
            Order order = odao.getOrderCusDetail(accId, status, orderId);
            List<Order> orderPet = odao.getOrderCusPetDetail(accId, status, orderId);

            String reasonCancel = request.getParameter("reasonCancel");
            String checkboxInfo = request.getParameter("confirmInfo");

            Timestamp timeOrder = order.getOrderDate();
            Timestamp timeCancelled = new Timestamp(System.currentTimeMillis());
            long time = 12 * 60 * 60 * 1000;

            Timestamp deadline = new Timestamp(timeOrder.getTime() + time);

            if (timeCancelled.before(deadline)) {
                if (checkboxInfo == null || checkboxInfo.trim().isEmpty()) {
                    session.setAttribute("errorMessage", "Bạn cần xác nhận thông tin trước khi hủy đơn");
                    request.setAttribute("orderDetail", order);
                    request.setAttribute("orderPet", orderPet);
                    String url = "orders?status=Pending&action=Cancelled&id=" + orderId;
                    response.sendRedirect(url);
                    return;

                } else {
                    if ("Cash on Delivery".equals(order.getPaymentMethod())) {
                        boolean updateCancelledOrder = odao.updateCancelledOrderByCus(orderId, reasonCancel);
                        if (updateCancelledOrder) {
                            EmailSender.sendCancelledOrder(order.getCustomerEmail(), orderId);
                            session.setAttribute("successMessage", "Hủy đơn thành công");
                        } else {
                            session.setAttribute("errorMessage", "Hủy đơn không thành công");

                        }
                    } else {
                        boolean updateCancelledOrderBanked = odao.updateCancelledOrderByCus(orderId, reasonCancel);
                        if (updateCancelledOrderBanked) {
                            EmailSender.sendCancelledOrderBanked(order.getCustomerEmail(), orderId);
                            session.setAttribute("successMessage", "Hủy đơn thành công, vui lòng kiểm tra Email để được hoàn tiền");
                        } else {
                            session.setAttribute("errorMessage", "Hủy đơn không thành công");
                        }
                    }
                    request.setAttribute("orderDetail", order);
                    request.setAttribute("orderPet", orderPet);
                    String url = "orders?status=Pending";
                    response.sendRedirect(url);
                }
            } else {
                session.setAttribute("errorMessage", "Bạn không thể hủy đơn sau 12 tiếng kể từ lúc đặt hàng");
                String url = "orders?status=Pending";
                response.sendRedirect(url);
            }

        } else {
            request.getRequestDispatcher("customer_order_page.jsp").forward(request, response);
        }
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
