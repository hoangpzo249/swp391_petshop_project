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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author HuyHoang
 */
public class Login_Account_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Login_Account_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login_Account_Servlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
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
        try {
            String emailLogin = request.getParameter("emailLogin");
            String passwordLogin = request.getParameter("passwordLogin");

            String remember = request.getParameter("remember");

            HttpSession session = request.getSession();

            AccountDAO accDao = new AccountDAO();
            Account acc = accDao.isLoginAcc(emailLogin, passwordLogin);
            CartDAO cartDAO = new CartDAO();

            if (acc != null) {

                session.setAttribute("userAccount", acc);
                List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
                if (guestCart != null && !guestCart.isEmpty()) {
                    
                    for (Cart c : guestCart) {
                        if (!cartDAO.petInCart(acc.getAccId(), c.getPetId())) {
                            cartDAO.addToPetCart(acc.getAccId(), c.getPetId());
                        }
                    }
                    session.removeAttribute("guestCart");
                }
                List<Cart> customerCart = cartDAO.getCart(acc.getAccId());
                session.setAttribute("cartcount", customerCart.size());

                String role = acc.getAccRole();
                String status = acc.getAccStatus();

                String fname = acc.getAccFname();

                if ("Inactive".equals(status)) {
                    request.setAttribute("errMessLogin", "Tài khoản của bạn đã bị vô hiệu hóa.");
                    request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                    return;
                }

                if ("on".equals(remember)) {
                    Cookie emailC = new Cookie("email", emailLogin);
                    Cookie passC = new Cookie("password", passwordLogin);

                    emailC.setMaxAge(60 * 60 * 24 * 7);
                    passC.setMaxAge(60 * 60 * 24 * 7);

                    response.addCookie(emailC);
                    response.addCookie(passC);
                } else {
                    Cookie emailC = new Cookie("email", emailLogin);
                    Cookie passC = new Cookie("password", passwordLogin);

                    emailC.setMaxAge(0);
                    passC.setMaxAge(0);

                    response.addCookie(emailC);
                    response.addCookie(passC);
                }

                if ("Admin".equals(role)) {
                    session.setAttribute("loginSuccess", "Chào Admin!");
                    response.sendRedirect("homepage");
                } else if ("Manager".equals(role)) {
                    session.setAttribute("loginSuccess", "Chào Manager!");
                    response.sendRedirect("homepage");
                } else if ("Seller".equals(role)) {
                    session.setAttribute("loginSuccess", "Chào Seller!");
                    response.sendRedirect("homepage");
                } else if ("Shipper".equals(role)) {
                    session.setAttribute("loginSuccess", "Chào Shipper!");
                    response.sendRedirect("homepage");
                } else if ("Customer".equals(role)) {
                    session.setAttribute("loginSuccess", "Chào mừng " + fname + " !");
                    response.sendRedirect("homepage");
                } else {
                    request.setAttribute("errMess", "Bạn cần có quyền truy cập");
                    request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("errMessLogin", "Email hoặc mật khẩu của bạn không đúng.");
                request.setAttribute("emailLogin", emailLogin);
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
