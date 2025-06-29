/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.CartDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Cart;
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
public class AddToCartServlet extends HttpServlet {

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
            out.println("<title>Servlet AddToCartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToCartServlet at " + request.getContextPath() + "</h1>");
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
        CartDAO dao = new CartDAO();
        PetDAO petDAO = new PetDAO();
        HttpSession session = request.getSession();

        String idStr = request.getParameter("id");

        if (idStr != null) {
            int petId = Integer.parseInt(idStr);
            Account acc = (Account) session.getAttribute("userAccount");

            if (acc != null) {
                int accId = acc.getAccId();
               
                if (dao.petInCart(accId, petId)) {
                    session.setAttribute("cartMessage", "Thú cưng đã tồn tại trong giỏ hàng!");
                } else {
                    dao.addToPetCart(accId, petId);
                    session.setAttribute("cartMessage", "Đã thêm thú cưng vào giỏ hàng!");
                }

            } else {
                List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
                if (guestCart == null) {
                    guestCart = new ArrayList<>();
                }
                boolean exists = false;
                for (Cart c : guestCart) {
                    if (c.getPetId() == petId) {
                        exists = true;
                        break;
                    }
                }
                if (exists) {
                    session.setAttribute("cartMessage", "Thú cưng đã tồn tại trong giỏ hàng!");
                } else {
                    guestCart.add(new Cart(-1, petId, 1, petDAO.getPetById(petId).getPetPrice()));
                    session.setAttribute("cartMessage", "Đã thêm thú cưng vào giỏ hàng!");
                }
                session.setAttribute("guestCart", guestCart);
            }
        }
        response.sendRedirect("displaypet?id=" + idStr);

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

    /*
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
