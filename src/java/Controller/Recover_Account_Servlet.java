/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AccountDAO;
import Model.Account;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HuyHoang
 */
public class Recover_Account_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Recover_Account_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Recover_Account_Servlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
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
            String inputEmail = request.getParameter("email");

            if (inputEmail == null || inputEmail.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            AccountDAO accDao = new AccountDAO();
            boolean checkEmail = accDao.isEmailExist(inputEmail);
            if (checkEmail) {
                String newPass = genPass();
                EmailSender.recoverPass(inputEmail, newPass);

                String hashPass = BCrypt.hashpw(newPass, BCrypt.gensalt());

                Account acc = new Account();
                acc.setAccEmail(inputEmail);
                acc.setAccPassword(hashPass);
                boolean success = accDao.updatePass(acc);
                if (success) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", inputEmail);
                    session.setAttribute("successMessRegister", "Mật khẩu mới đã được gửi về Email của bạn.");
                    response.sendRedirect("login");
                } else {
                    request.setAttribute("errMess", "Lỗi.");
                    request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("errMess", "Email của bạn không tồn tại trong hệ thống.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
        }
    }

    public String genPass() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "`~!@#$%^&*()_+";
        String all = upper + lower + digits + special;
        Random random = new Random();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            pass.append(all.charAt(random.nextInt(all.length())));

        }
        System.out.println(pass);
        return pass.toString();
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
