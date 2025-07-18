/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AccountDAO;
import DAO.OrderDAO;
import Models.Account;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class SellerAssignShipperServlet extends HttpServlet {

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
            out.println("<title>Servlet SellerAssignShipperServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SellerAssignShipperServlet at " + request.getContextPath() + "</h1>");
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
        OrderDAO _orderdao = new OrderDAO();
        AccountDAO _accountdao = new AccountDAO();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String referer = request.getHeader("referer");
        if (!_orderdao.getOrderById(orderId).getOrderStatus().equals("Pending Shipper")) {
            session.setAttribute("errMess", "Không thể hủy chỉ định Shipper cho đơn hàng này.");
            response.sendRedirect(referer != null ? referer : "displayorder");
        } else {
            if (_orderdao.unassignShipper(orderId)) {
                session.setAttribute("successMess", "Đã hủy chỉ định Shipper cho đơn hàng #" + orderId);
                response.sendRedirect(referer != null ? referer : "displayorder");
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình hủy chỉ định đơn hàng");
                response.sendRedirect(referer != null ? referer : "displayorder");
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
        OrderDAO _orderdao = new OrderDAO();
        AccountDAO _accountdao = new AccountDAO();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int shipperId = Integer.parseInt(request.getParameter("shipperId"));
        String referer = request.getHeader("referer");
        String fullname = _accountdao.getUserFullnameById(shipperId);

        if (action != null && action.equals("unassign")) {
            if (_orderdao.unassignShipper(orderId)) {
                session.setAttribute("successMess", "Đã hủy chỉ định Shipper cho đơn hàng #" + orderId);
                response.sendRedirect(referer != null ? referer : "displayorder");
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình hủy chỉ định đơn hàng");
                response.sendRedirect(referer != null ? referer : "displayorder");
            }
        } else {
            if (_orderdao.assignShipper(orderId, shipperId)) {
                session.setAttribute("successMess", "Đã chỉ định đơn hàng #" + orderId + " cho " + fullname);
                EmailSender.sendNewDeliveryAssignment(_accountdao.getEmailById(shipperId), orderId);

                response.sendRedirect(referer != null ? referer : "displayorder");
            } else {
                session.setAttribute("errMess", "Đã có lỗi xảy ra trong quá trình chỉ định đơn hàng");
                response.sendRedirect(referer != null ? referer : "displayorder");
            }
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
