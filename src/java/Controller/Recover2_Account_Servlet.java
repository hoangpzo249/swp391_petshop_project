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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HuyHoang
 */
public class Recover2_Account_Servlet extends HttpServlet {

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
            out.println("<title>Servlet RecoverPass_Account_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RecoverPass_Account_Servlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("recover2_account_page.jsp").forward(request, response);
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
            String pass = request.getParameter("password");
            String cpass = request.getParameter("comfirm_password");

            if (pass == null || pass.trim().isEmpty()
                    || cpass == null || cpass.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                request.setAttribute("password", pass);
                request.setAttribute("comfirm_password", cpass);

                request.getRequestDispatcher("recover2_account_page.jsp").forward(request, response);
                return;
            }

            if (!pass.equals(cpass)) {
                request.setAttribute("errMess", "Mật khẩu không khớp");
                request.setAttribute("password", pass);
                request.setAttribute("comfirm_password", cpass);

                request.getRequestDispatcher("recover2_account_page.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            System.out.println(email);
            String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());

            Account acc = new Account();
            acc.setAccEmail(email);
            acc.setAccPassword(hashPass);

            AccountDAO accDao = new AccountDAO();
            boolean success = accDao.updatePass(acc);
            if (success) {
                session.setAttribute("successMessRecover", "Cập nhật mật khẩu thành công");
                response.sendRedirect("login");
            } else {
                session.setAttribute("errMess", "Lỗi.");
                response.sendRedirect("login");
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
