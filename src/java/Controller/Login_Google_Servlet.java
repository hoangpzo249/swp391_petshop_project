/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import Model.Account;
import Utils.GoogleUser;
import Utils.GoogleUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author HuyHoang
 */
public class Login_Google_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Login_Google_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login_Google_Servlet at " + request.getContextPath() + "</h1>");
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
        String code = request.getParameter("code");

        if (code == null || code.trim().isEmpty()) {
            request.setAttribute("errMess", "Đăng nhập bằng Google thất bại. Bạn hãy thử cách khác.");
            request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();

        String accessToken = GoogleUtils.getToken(code);
        GoogleUser ggUser = GoogleUtils.getUserInfo(accessToken);

        AccountDAO accDao = new AccountDAO();
        Account acc = accDao.ggByEmail(ggUser.getEmail());

//        System.out.println("Email tồn tại" + acc);
        if (acc == null) {
            Account newAcc = new Account();
            newAcc.setAccEmail(ggUser.getEmail());
            newAcc.setAccFname(ggUser.getGiven_name());
            newAcc.setAccLname(ggUser.getFamily_name());
            newAcc.setAccPhoneNumber("Chưa cập nhật");

//            System.out.println("Email: " +ggUser.getEmail());
            try {
                accDao.registerAccByGG(newAcc);

                acc = accDao.ggByEmail(ggUser.getEmail());
                session.setAttribute("userAccount", acc);
                session.setAttribute("loginSuccess", "Đăng nhập thành công với tài khoản Google");
                response.sendRedirect("homepage");
            } catch (Exception e) {
                request.setAttribute("errMess", "Có lỗi xảy ra với đăng nhập bằng tài khoản Google");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            }

        } else {
            session.setAttribute("userAccount", acc);
            session.setAttribute("loginSuccess", "Đăng nhập thành công với tài khoản Google");
            response.sendRedirect("homepage");
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
