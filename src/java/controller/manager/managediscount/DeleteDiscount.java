/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager.managediscount;

import dal.DiscountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Discount;

/**
 *
 * @author Admin
 */
public class DeleteDiscount extends HttpServlet {

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
            out.println("<title>Servlet DeleteDiscount</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteDiscount at " + request.getContextPath() + "</h1>");
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
        ArrayList<String> errors = new ArrayList<>();
        DiscountDAO discountDao = new DiscountDAO();
        String deletedIdString = request.getParameter("deleteId");
        // validate input data
        int deleteId = 0;
        if (deletedIdString == null || deletedIdString.isBlank()) {
            errors.add("the id is not indentified");
        } else {
            try {
                deleteId = Integer.parseInt(deletedIdString);
                if (deleteId < 0) {
                    errors.add("the id must greater than 0");
                }
            } catch (Exception e) {
                errors.add("the delete id must be a number! ");
            }
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
        } else { //if it have no error in after the first validate
            if (discountDao.deleteDiscountInDataBase(deleteId)) {
                request.setAttribute("actionStatus", "delete sucessfully");
            } else {
                request.setAttribute("actionStatus", "delete failed");
            }
        }
        ArrayList<Discount> list = discountDao.getAllDiscountFromDatabase();
        request.setAttribute("discountList", list);
        request.getRequestDispatcher("managerpages/managediscount.jsp").forward(request, response);
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
        processRequest(request, response);
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
