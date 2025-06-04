/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AccountDAO;
import DAO.CartDAO;
import Models.Account;
import Models.Cart;
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
 * @author Lenovo
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("message", "Tài khoản đã được tạo! Xin vui lòng đăng nhập.");
        request.getRequestDispatcher("account_login.jsp")
                .forward(request, response);
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
        AccountDAO _dao = new AccountDAO();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (_dao.LoginUser(username, password)) {
            HttpSession session = request.getSession(false);
             Account account = _dao.FindUserInfo(username);
            session.setAttribute("account", account);
 List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
        if (guestCart != null && !guestCart.isEmpty()) {
            CartDAO cartDAO = new CartDAO();

            for (Cart c : guestCart) {
                if (!cartDAO.petInCart(account.getAccId(), c.getPetId())) {
                    cartDAO.addToPetCart(account.getAccId(), c.getPetId());
                }
            }

            
            session.removeAttribute("guestCart");
            session.setAttribute("cartcount", cartDAO.getTotalCartItems(account.getAccId()));
        }

            response.sendRedirect("listshoppet");
        } else {
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("errMessage", "Login unsuccessful");
            request.getRequestDispatcher("account_login.jsp").forward(request, response);
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
