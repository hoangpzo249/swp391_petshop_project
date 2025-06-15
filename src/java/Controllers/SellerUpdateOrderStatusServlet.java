/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Pet;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class SellerUpdateOrderStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateOrderStatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateOrderStatusServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session=request.getSession();
        Account account=(Account) session.getAttribute("userAccount");
        if (account==null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
        OrderDAO _daoorder = new OrderDAO();
        PetDAO _daopet = new PetDAO();
        String status = request.getParameter("status");
        int id = Integer.parseInt(request.getParameter("orderId"));
        String reason = request.getParameter("reason");
        String referer = request.getHeader("referer");

        if (status.equals("Shipping")) {
            session.setAttribute("errMess", "Chức năng này chưa được hoàn thiện.");
            response.sendRedirect(referer);
            return;
        }

        if (_daoorder.updateOrderStatusById(id, status, reason)) {
            String email = _daoorder.getCustomerEmailByOrderId(id);
            List<Pet> listpet = new ArrayList<>();
            for (Integer i : _daoorder.getOrderContentById(id)) {
                listpet.add(_daopet.getPetById(i));
            }
            switch (status) {
                case "Confirmed":
                    session.setAttribute("successMess", "Xác nhận đơn hàng " + id + " thành công.");
                    if (email != null) {
                        EmailSender.sendConfirmOrder(email, id, listpet, _daoorder.getOrderPriceById(id));
                    }
                    break;
                case "Rejected":
                    session.setAttribute("successMess", "Từ chối đơn hàng " + id + " thành công.");
                    String hidePets = request.getParameter("hidePets");
                    if (hidePets != null && hidePets.equals("true")) {
                        if (!_daopet.updatePetStatusById(_daoorder.getOrderContentById(id), 0)) {
                            session.setAttribute("successMess", "Từ chối đơn hàng " + id + " thành công, nhưng xảy ra vấn đề khi cập nhật trạng thái thú cưng.");
                        }
                    }
                    if (!_daopet.updatePetAvailabilityById(_daoorder.getOrderContentById(id), 1)) {
                        session.setAttribute("successMess", "Từ chối đơn hàng " + id + " thành công, nhưng xảy ra vấn đề khi cập nhật trạng thái thú cưng.");
                    }
                    if (email != null) {
                        EmailSender.sendRejectOrder(email, id, listpet, _daoorder.getOrderPriceById(id), reason);
                    }
                    break;
                case "Shipping":
                    session.setAttribute("successMess", "Cập nhật trạng thái đơn hàng " + id + " thành công.");
                    if (email != null) {
                        EmailSender.sendShippingOrder(email, id, listpet, _daoorder.getOrderPriceById(id));
                    }
                    break;
                default:
                    throw new AssertionError();
            }

        } else {
            session.setAttribute("errMess", "Thực hiện hành động không thành công.");
        }

//        if (status.equals("Confirmed")) {
//            
//        }
//        else if (status.equals("Rejected")) {
//            
//        }
//        else if (status.equals("s")) {
//            
//        }
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("displayorder");
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

//        String orderId = request.getParameter("id");
//        String newStatus = request.getParameter("status");
//
//        OrderDAO orderDAO = new OrderDAO();
//        boolean success = orderDAO.updateOrderStatusById(orderId, newStatus);
//
//        if (success) {
//            request.setAttribute("message", "Order status updated successfully!");
//        } else {
//            request.setAttribute("error", "Failed to update order status");
//        }
//
//        response.sendRedirect("orderdetail?id=" + orderId);
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
