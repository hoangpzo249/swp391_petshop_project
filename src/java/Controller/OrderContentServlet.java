/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import DAO.OrderContentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import Model.OrderContent;

/**
 *
 * @author QuangAnh
 */
public class OrderContentServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet OrderContentServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderContentServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    String oid = request.getParameter("orderId");
    String deleteId = request.getParameter("deleteId"); // ID cần xóa
    List<OrderContent> list = null;

    try {
        // Nếu có yêu cầu xóa
        if (deleteId != null && !deleteId.isEmpty()) {
            int orderContentId = Integer.parseInt(deleteId);
            OrderContentDAO dao = new OrderContentDAO();
            dao.deleteOrderContent(orderContentId);
        }

        // Sau khi xóa (hoặc không), vẫn cần load danh sách
        if (oid != null && !oid.isEmpty()) {
            int orderId = Integer.parseInt(oid);
            OrderContentDAO dao = new OrderContentDAO();
            list = dao.getByOrderId(orderId);
        }

    } catch (NumberFormatException e) {
        e.printStackTrace();
        request.setAttribute("error", "Invalid parameter format.");
    }

    request.setAttribute("orderContents", list);
    request.setAttribute("orderId", oid); // Truyền lại orderId cho form
    request.getRequestDispatcher("order-content.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}