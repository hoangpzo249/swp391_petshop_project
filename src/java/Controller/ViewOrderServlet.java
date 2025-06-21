/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.OrderContentDAO;
import DAO.OrderDAO;
import DAO.PetDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import Model.Order;
import Model.OrderContent;
import Model.Pet;
import utils.NotificationUtils;

/**
 *
 * @author QuangAnh
 */
public class ViewOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet ViewOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewOrderServlet at " + request.getContextPath() + "</h1>");
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
//        HttpSession session = request.getSession(false);
//        Integer accId = (Integer) session.getAttribute("accId");
//        if (accId == null) {
//        response.sendRedirect("login.jsp");
//        return;
//    }

        HttpSession session = request.getSession();
        Integer accId = (Integer) session.getAttribute("accId");
        if (accId == null) {
            accId = 1; // gán tạm để test
            session.setAttribute("accId", accId);
        }

        // Lấy danh sách đơn hàng
        OrderDAO dao = new OrderDAO();
        OrderContentDAO ocDAO = new OrderContentDAO();
        PetDAO petDAO = new PetDAO();

        List<Order> orders = dao.getAllOrders();
        for (Order order : orders) {
            List<OrderContent> contents = ocDAO.getByOrderId(order.getOrderId());
            for (OrderContent oc : contents) {
                Pet pet = petDAO.getPetById(oc.getPetId());
                oc.setPet(pet);
            }
            order.setOrderContents(contents);
        }

        // Lấy thông báo từ session (nếu có) và chuyển sang request
        String message = (String) session.getAttribute("message");
        String error = (String) session.getAttribute("error");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        request.setAttribute("orderList", orders);
        request.getRequestDispatcher("Order.jsp").forward(request, response);
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
