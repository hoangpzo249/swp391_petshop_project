/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import Model.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
            String email = request.getParameter("email");
            String username = request.getParameter("email");
            String password = request.getParameter("password");

            String remember = request.getParameter("remember");

            if (email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin.");
                request.setAttribute("email", email);
                request.setAttribute("password", password);
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();

            AccountDAO accDao = new AccountDAO();
            Account acc = accDao.isLoginAcc(email, username, password);

//            session.setAttribute("userAccount", acc);
//            if (!email.equals(acc.getAccEmail())) {
//                request.setAttribute("errMess", "Email của bạn chưa đăng kí tài khoản");
//                request.setAttribute("password", password);
//                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
//            }
            if (acc != null) {

                session.setAttribute("userAccount", acc);
                String role = acc.getAccRole();

                if ("on".equals(remember)) {
                    Cookie emailC = new Cookie("emailUser", email);
//                    Cookie usernameC = new Cookie("email", username);
                    Cookie passC = new Cookie("password", password);

                    emailC.setMaxAge(60 * 60 * 24 * 7);
//                    usernameC.setMaxAge(60 * 60 * 24 * 7);
                    passC.setMaxAge(60 * 60 * 24 * 7);

                    response.addCookie(emailC);
//                    response.addCookie(usernameC);
                    response.addCookie(passC);
                } else {
                    Cookie emailC = new Cookie("emailUser", email);
//                    Cookie usernameC = new Cookie("email", username);
                    Cookie passC = new Cookie("password", password);

                    emailC.setMaxAge(0);
//                    usernameC.setMaxAge(0);
                    passC.setMaxAge(0);

                    response.addCookie(emailC);
//                    response.addCookie(usernameC);
                    response.addCookie(passC);
                }
                
                if ("Admin".equals(role)) {
                    session.setAttribute("loginSuccess", "Chào Admin!");
                    response.sendRedirect("homepage");
                } else if ("Customer".equals(role)) {
                    session.setAttribute("loginSuccess", "Đăng nhập thành công");
                    response.sendRedirect("homepage");
                } else {
                    request.setAttribute("errMess", "Bạn cần có quyền truy cập");
//                    request.setAttribute("email", email);
//                    request.setAttribute("password", password);
                    request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("errMess", "Email hoặc mật khẩu của bạn không đúng.");

//                request.setAttribute("email", email);
//                request.setAttribute("password", password);

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
