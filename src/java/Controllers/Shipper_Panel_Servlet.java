/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import DAO.ShipperDAO;
import Models.Account;
import Models.Order;
import Models.Shipper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author HuyHoang
 */
public class Shipper_Panel_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Shipper_Panel_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Shipper_Panel_Servlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("userAccount");

        OrderDAO oDao = new OrderDAO();

        int pendingCount = oDao.countPendingShipper(acc.getAccId());
        request.setAttribute("pendingCount", pendingCount);

        int shippingCount = oDao.countPendingShipperShipping(acc.getAccId());
        request.setAttribute("shippingCount", shippingCount);

        int deliveredCount = oDao.countPendingShipperDelivered(acc.getAccId());
        request.setAttribute("deliveredCount", deliveredCount);

        if ("pickup".equals(action) & id != null) {
            Order orderDetail = oDao.getOrderDetailShipperId(acc.getAccId());
            List<Order> orderListDetail = oDao.getOrderDetailProductShipperId(acc.getAccId());
            
            request.setAttribute("orderDetail", orderDetail);
            request.setAttribute("orderListDetail", orderListDetail);
            request.getRequestDispatcher("shipper_orderPickup_page.jsp").forward(request, response);

        } else {
            ShipperDAO shipDao = new ShipperDAO();
            Shipper shipper = shipDao.getShipperById(acc.getAccId());
            request.setAttribute("shipper", shipper);

            List<Order> orderList = oDao.getOrderShipperId(acc.getAccId());
            request.setAttribute("orderList", orderList);
            request.getRequestDispatcher("shipper_account_page.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("userAccount");

        ShipperDAO shipDao = new ShipperDAO();
        OrderDAO oDao = new OrderDAO();

        String action = request.getParameter("action");
        if ("status".equals(action)) {
            String statusCheckbox = request.getParameter("statusCheckbox");
            String newStatus;

            if (statusCheckbox != null && statusCheckbox.equals("on")) {
                newStatus = "Online";
            } else {
                newStatus = "Offline";
            }

            boolean checkPenndingShippingShipper = oDao.checkPenndingShippingShipper(acc.getAccId());
            if (checkPenndingShippingShipper) {
                session.setAttribute("errorMessage", "Bạn cần hoàn thành hết đơn cần giao và đơn đang giao");
                String url = "shipper_panel?action=status";
                response.sendRedirect(url);
                return;
            }

            boolean updatestatus = shipDao.updateStatusShipper(acc.getAccId(), newStatus);
            if (updatestatus) {
                session.setAttribute("successMessage", "Cập nhật trạng thái thành công");
            } else {
                session.setAttribute("errorMessage", "Lỗi cập nhật trạng thái");
            }

            Shipper shipper = shipDao.getShipperById(acc.getAccId());
            request.setAttribute("shipper", shipper);

            List<Order> orderList = oDao.getOrderShipperId(acc.getAccId());
            request.setAttribute("orderList", orderList);
            String url = "shipper_panel?action=status";
            response.sendRedirect(url);
        } else {
            request.getRequestDispatcher("shipper_account_page.jsp").forward(request, response);

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
