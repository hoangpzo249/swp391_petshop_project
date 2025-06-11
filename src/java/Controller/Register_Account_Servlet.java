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
            String emailRegister = (String) session.getAttribute("emailRegister");

            if (emailRegister != null) {
                String otpResendRegis = otp();

                long curTimeSendOtp = (long) session.getAttribute("curTime");
                long nowTime = System.currentTimeMillis();

                long resendOtp = 60 * 1000;

                if (nowTime - curTimeSendOtp < resendOtp) {
                    long countTimeResend = (resendOtp - (nowTime - curTimeSendOtp)) / 1000;
                    request.setAttribute("errMessRegisOtp", "Bạn cần chờ " + countTimeResend + " GIÂY để gửi lại OTP");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                    return;
                }
                try {
                    EmailSender.sendOTP(emailRegister, otpResendRegis);

                    session.setAttribute("otpRegister", otpResendRegis);
                    session.setAttribute("curTime", nowTime);

                    request.setAttribute("successMessRegis", "Gửi lại OTP thành công");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("errMessRegisOtp", "Bạn cần thực hiện lại");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                }
            } else {
                session.invalidate();
                request.setAttribute("errMessRegisOtp", "Hết thời gian chờ, bạn cần thực hiện lại.");
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
            String fNameRegister = request.getParameter("fNameRegister");
            String lNameRegister = request.getParameter("lNameRegister");
            String emailRegister = request.getParameter("emailRegister");
            String passRegister = request.getParameter("passRegister");
            String confirm_passwordRegister = request.getParameter("confirm_passwordRegister");

            String fullNameRegis = fNameRegister + " " + lNameRegister;

            String checkName = "^[a-zA-ZÀ-ỹ\\s]+$";
            if (!fNameRegister.matches(checkName) || !lNameRegister.matches(checkName)) {
                request.setAttribute("errMessRegister", "Họ và Tên của bạn không được chứa kí tự đặc biệt và số");
                request.setAttribute("emailRegister", emailRegister);
                request.setAttribute("passRegister", passRegister);
                request.setAttribute("confirm_passwordRegister", confirm_passwordRegister);
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            AccountDAO daoAcc = new AccountDAO();

            boolean checkEmail = daoAcc.isEmailExist(emailRegister);
            if (checkEmail) {
                request.setAttribute("errMessRegister", "Email đã tồn tại, bạn cần nhập email khác.");

                request.setAttribute("fNameRegister", fNameRegister);
                request.setAttribute("lNameRegister", lNameRegister);
                request.setAttribute("passRegister", passRegister);
                request.setAttribute("confirm_passwordRegister", confirm_passwordRegister);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            if (!passRegister.equals(confirm_passwordRegister)) {
                request.setAttribute("errMessRegister", "Mật khẩu không khớp!");

                request.setAttribute("fNameRegister", fNameRegister);
                request.setAttribute("lNameRegister", lNameRegister);
                request.setAttribute("emailRegister", emailRegister);
                request.setAttribute("passRegister", passRegister);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            boolean checkPass = isValidPassword(passRegister);
            if (!checkPass) {
                request.setAttribute("errMessRegister", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số, kí tự đặc biệt và không chứa dấu cách.");

                request.setAttribute("fNameRegister", fNameRegister);
                request.setAttribute("lNameRegister", fNameRegister);
                request.setAttribute("emailRegister", emailRegister);

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            String usernameRegister = genUsername(emailRegister);

            String hashPassRegister = BCrypt.hashpw(passRegister, BCrypt.gensalt());

            Account tempAcc = new Account();
            tempAcc.setAccFname(fNameRegister);
            tempAcc.setAccLname(lNameRegister);
            tempAcc.setAccEmail(emailRegister);
            tempAcc.setAccPhoneNumber("Chưa cập nhật");
            tempAcc.setAccUsername(usernameRegister);
            tempAcc.setAccPassword(hashPassRegister);

            session.setAttribute("tempAccount", tempAcc);

            String otpRegister = otp();
            long curTime = System.currentTimeMillis();

            try {
                EmailSender.sendOTP(emailRegister, otpRegister);

                session.setAttribute("otpRegister", otpRegister);
                session.setAttribute("curTime", curTime);

                session.setAttribute("fullNameRegis", fullNameRegis);
                session.setAttribute("passRegister", passRegister);
                session.setAttribute("emailRegister", emailRegister);
                session.setAttribute("sendOtpSuccess", "true");

                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("errMessRegister", "Bạn cần thực hiện lại");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            }

        } else if ("otp".equals(action)) {

            String inputotpRegis = request.getParameter("inputotpRegis");

            String otpRegister = (String) session.getAttribute("otpRegister");
            long curTime = (long) session.getAttribute("curTime");

            String emailRegister = (String) session.getAttribute("emailRegister");
            String fullNameRegis = (String) session.getAttribute("fullNameRegis");

            String passRegister = (String) session.getAttribute("passRegister");

            Account account = (Account) session.getAttribute("tempAccount");

            long nowTime = System.currentTimeMillis();
            long time = 3 * 60 * 1000;

            if (nowTime - curTime > time) {
                request.setAttribute("errMessRegisOtp", "OTP hết hạn, bạn cần thực hiện lại.");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                return;
            }

            if (otpRegister == null || otpRegister.trim().isEmpty()
                    || emailRegister == null || emailRegister.trim().isEmpty()
                    || fullNameRegis == null || fullNameRegis.trim().isEmpty()
                    || passRegister == null || passRegister.trim().isEmpty()
                    || account == null) {
                session.invalidate();
                request.setAttribute("errMessRegisOtp", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
            }

            if (inputotpRegis != null && !inputotpRegis.trim().isEmpty()
                    && otpRegister != null && otpRegister.equals(inputotpRegis)) {

                AccountDAO accDao = new AccountDAO();
                try {
                    accDao.registerAcc(account);
                    EmailSender.registerSuccess(emailRegister, fullNameRegis);

                    Account acc = accDao.isLoginAcc(emailRegister, passRegister);
                    try {
                        session.setAttribute("userAccount", acc);
                        session.removeAttribute("otpRegister");
                        session.removeAttribute("curTime");
                        session.removeAttribute("emailRegister");
                        session.removeAttribute("fullNameRegis");
                        session.removeAttribute("passRegister");
                        session.removeAttribute("errMessRegisOtp");

                        session.setAttribute("loginSuccess", "Bạn đã tạo tài khoản thành công<br>Cập nhật địa chỉ và số điện thoại <a href='profile' style='color:#f26f21'>TẠI ĐÂY</a> để hoàn tất hồ sơ.");
                        response.sendRedirect("homepage");
                    } catch (Exception e) {
                        session.setAttribute("errMessRegisOtp", "Đăng kí thất bại");
                        request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                    }

                } catch (Exception e) {
                    session.setAttribute("errMessRegisOtp", "Tạo tài khoản không thành công");
                    request.getRequestDispatcher("register_account_page.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("errMessRegisOtp", "Mã OTP không hợp lệ");
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
            if (Character.isWhitespace(c)) {
                return false;
            }
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
        int otp = random.nextInt(1000000);
        return String.format("%06d", otp);
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
