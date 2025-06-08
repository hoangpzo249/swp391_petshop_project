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

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null || action.trim().isEmpty()) {
            request.getRequestDispatcher("register_account_page.jsp").forward(request, response);

        } else if ("resend-otp".equals(action)) {
            String email = (String) session.getAttribute("email");

            if (email != null) {
                String otp = otp();
                long curTime = System.currentTimeMillis();

                long curTimeSendOtp = (long) session.getAttribute("curTime");
                long nowTime = System.currentTimeMillis();

                long resendOtp = 60 * 1000;

                if (nowTime - curTimeSendOtp < resendOtp) {
                    request.setAttribute("errMess", "Bạn cần chờ 60s để gửi lại OTP");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                    return;
                }
                try {
                    EmailSender.sendOTP(email, otp);

                    session.setAttribute("otp", otp);
                    session.setAttribute("curTime", curTime);

                    request.setAttribute("successMess", "Gửi lại OTP thành công");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("errMess", "Bạn cần thực hiện lại");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                }
            } else {
                session.invalidate();
                request.setAttribute("errMess", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null || action.trim().isEmpty()) {
            String fName = request.getParameter("firstname");
            String lName = request.getParameter("lastname");
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            String comfirmPass = request.getParameter("confirm_password");

            String fullName = fName + " " + lName;

            if (fName == null || lName == null || email == null || pass == null || comfirmPass == null
                    || fName.trim().isEmpty() || lName.trim().isEmpty() || email.trim().isEmpty() || pass.trim().isEmpty() || comfirmPass.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin!");

                request.setAttribute("firstname", fName);
                request.setAttribute("lastname", lName);
                request.setAttribute("email", email);
                request.setAttribute("password", pass);
                request.setAttribute("confirm_password", comfirmPass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            String checkName = "^[a-zA-ZÀ-ỹ\\s]+$";
            if (!fName.matches(checkName)) {
                request.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt và số");
                request.setAttribute("email", email);
                request.setAttribute("password", pass);
                request.setAttribute("confirm_password", comfirmPass);
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            if (!lName.matches(checkName)) {
                request.setAttribute("errMess", "Tên của bạn không được chứa kí tự đặc biệt");
                request.setAttribute("email", email);
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
                request.setAttribute("password", pass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            boolean checkPass = isValidPassword(pass);
            if (!checkPass) {
                request.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt");

                request.setAttribute("firstname", fName);
                request.setAttribute("lastname", lName);
                request.setAttribute("email", email);
//                request.setAttribute("password", pass);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            String username = genUsername(email);

            String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());

            Account tempAcc = new Account();
            tempAcc.setAccFname(fName);
            tempAcc.setAccLname(lName);
            tempAcc.setAccEmail(email);
            tempAcc.setAccPhoneNumber("Chưa cập nhật");
            tempAcc.setAccUsername(username);
            tempAcc.setAccPassword(hashPass);

            session.setAttribute("tempAccount", tempAcc);

            String otp = otp();
            long curTime = System.currentTimeMillis();

            try {
                EmailSender.sendOTP(email, otp);

                session.setAttribute("otp", otp);
                session.setAttribute("curTime", curTime);

                session.setAttribute("fullName", fullName);
                session.setAttribute("pass", pass);
                session.setAttribute("email", email);
                session.setAttribute("sendOtpSuccess", "true");

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("errMess", "Bạn cần thực hiện lại");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            }

        } else if ("otp".equals(action)) {

            String inputOTP = request.getParameter("inputotp");

            String otp = (String) session.getAttribute("otp");
            long curTime = (long) session.getAttribute("curTime");

            String email = (String) session.getAttribute("email");
            String fullName = (String) session.getAttribute("fullName");

            String pass = (String) session.getAttribute("pass");

            Account account = (Account) session.getAttribute("tempAccount");

            long nowTime = System.currentTimeMillis();
            long time = 3 * 60 * 1000;

            if (nowTime - curTime > time) {
                request.setAttribute("errMess", "OTP hết hạn, bạn cần thực hiện lại.");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            if (otp == null || otp.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || fullName == null || fullName.trim().isEmpty()
                    || pass == null || pass.trim().isEmpty()
                    || account == null) {
                session.invalidate();
                request.setAttribute("errMess", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            }

            if (inputOTP != null && !inputOTP.trim().isEmpty()
                    && otp != null && otp.equals(inputOTP)) {

                AccountDAO accDao = new AccountDAO();
                try {
                    accDao.registerAcc(account);
                    EmailSender.registerSuccess(email, fullName);

                    Account acc = accDao.isLoginAcc(email, pass);
                    try {
                        session.setAttribute("userAccount", acc);
                        session.removeAttribute("otp");
                        session.removeAttribute("curTime");
                        session.removeAttribute("email");
                        session.removeAttribute("fullName");
                        session.removeAttribute("pass");
                        session.removeAttribute("errMess");

                        session.setAttribute("loginSuccess", "Bạn đã tạo tài khoản thành công<br>Cập nhật địa chỉ và số điện thoại <a href='profile' style='color:#f26f21'>TẠI ĐÂY</a> để hoàn tất hồ sơ.");
                        response.sendRedirect("homepage");
                    } catch (Exception e) {
                        session.setAttribute("errMess", "Đăng kí thất bại");
                        request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                    }

                } catch (Exception e) {
                    session.setAttribute("errMess", "Tạo tài khoản không thành công");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("errMess", "Mã OTP không hợp lệ");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("register");
        }

    }

    public boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if ("!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~".indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public String otp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(1000000);
        return String.valueOf(otp);
    }

    public String genUsername(String email) {
        String username = email.substring(0, email.indexOf("@"));
        AccountDAO accdao = new AccountDAO();
        String newUsername = "";

        boolean existUsername = true;

        while (existUsername) {
            Random random = new Random();
            int randomNumber = 100000 + random.nextInt(1000000);

            newUsername = username + randomNumber;

            existUsername = accdao.isUsernameExist(newUsername);
        }
        return newUsername;
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
