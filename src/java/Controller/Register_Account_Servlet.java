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
public class Register_Account_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Register_Account_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register_Account_Servlet at " + request.getContextPath() + "</h1>");
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
        try {
            request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
        } catch (Exception e) {

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
        try {
            String fName = request.getParameter("firstname");
            String lName = request.getParameter("lastname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            String pass = request.getParameter("password");
            String comfirmPass = request.getParameter("confirm_password");

            if (fName == null || lName == null || email == null || phone == null || username == null || pass == null || comfirmPass == null
                    || fName.trim().isEmpty() || lName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || username.trim().isEmpty() || pass.trim().isEmpty() || comfirmPass.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin!");

                request.setAttribute("firstname", fName);
                request.setAttribute("lastname", lName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("username", username);
                request.setAttribute("password", pass);
                request.setAttribute("confirm_password", comfirmPass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            AccountDAO daoAcc = new AccountDAO();

            boolean checkEmail = daoAcc.isEmailExist(email);
            if (checkEmail) {
                request.setAttribute("errMess", "Email đã tồn tại, bạn cần nhập email khác.");

                request.setAttribute("firstname", fName);
                request.setAttribute("lastname", lName);
                request.setAttribute("phone", phone);
                request.setAttribute("username", username);
                request.setAttribute("password", pass);
                request.setAttribute("confirm_password", comfirmPass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            boolean checkUsername = daoAcc.isUsernameExist(username);
            if (checkUsername) {
                request.setAttribute("errMess", "Username đã tồn tại, bạn cần nhập username khác.");

                request.setAttribute("firstname", fName);
                request.setAttribute("lastname", lName);
                request.setAttribute("phone", phone);
                request.setAttribute("email", email);
                request.setAttribute("password", pass);
                request.setAttribute("confirm_password", comfirmPass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            if (!pass.equals(comfirmPass)) {
                request.setAttribute("errMess", "Mật khẩu không khớp!");

                request.setAttribute("firstname", fName);
                request.setAttribute("lastname", lName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("username", username);
                request.setAttribute("password", pass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());

            Account tempAcc = new Account();
            tempAcc.setAccFname(fName);
            tempAcc.setAccLname(lName);
            tempAcc.setAccEmail(email);
            tempAcc.setAccPhoneNumber(phone);
            tempAcc.setAccUsername(username);
            tempAcc.setAccPassword(hashPass);

            HttpSession session = request.getSession();

            session.setAttribute("tempAccount", tempAcc);
                    
            String otp = otp();
            
            EmailSender.sendOTP(email, otp);
            session.setAttribute("otp", otp);
            
//            session.setAttribute("infor", "Gửi mã OTP thành công");
//            session.setAttribute("infor1", "Bạn cần nhập mã OTP để hoàn tất tạo tài khoản");
            
            session.setAttribute("email", email);
            response.sendRedirect("verify-otp");
            
//            session.setAttribute("firstname", fName);
//            session.setAttribute("lastname", lName);
//            session.setAttribute("phone", phone);
//            session.setAttribute("username", username);
//            request.getRequestDispatcher("register_account_page.jsp").forward(request, response);

        } catch (Exception e) {
        }
    }

    public String otp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
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
