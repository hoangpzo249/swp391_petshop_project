/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AccountDAO;
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null || action.trim().isEmpty()) {
            request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);

        } else if ("resend-otp".equals(action)) {
            String emailRecover = (String) session.getAttribute("emailRecoverSendOtp");

            if (emailRecover == null || emailRecover.trim().isEmpty()) {
                session.invalidate();
                request.setAttribute("errMessRecover", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }
            
            String otpResendRecover = otp();

            long curTimeSendOtp = (long) session.getAttribute("curTime");
            long nowTime = System.currentTimeMillis();
            long resendOtp = 60 * 1000;

            if (nowTime - curTimeSendOtp < resendOtp) {
                long countTimeRecover = (resendOtp - (nowTime - curTimeSendOtp)) / 1000;
                request.setAttribute("errMessOtpRecover", "Bạn cần chờ " + countTimeRecover + " GIÂY để gửi lại OTP");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            try {
                EmailSender.sendOTPRecover(emailRecover, otpResendRecover);

                session.setAttribute("otpRecover", otpResendRecover);
                session.setAttribute("curTime", nowTime);
                request.setAttribute("successMessResend", "Gửi lại OTP thành công");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);

            } catch (Exception e) {
                request.setAttribute("errMessOtpRecover", "Bạn cần thực hiện lại");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
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
            String emailRecover = request.getParameter("emailRecover");

            AccountDAO accDao = new AccountDAO();
            boolean success = accDao.isEmailExist(emailRecover);

            if (!success) {
                request.setAttribute("emailRecover", emailRecover);
                request.setAttribute("errMessRecover", "Email của bạn không tồn tại trong hệ thống");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            String genOtp = otp();
            long curTime = System.currentTimeMillis();

            try {
                EmailSender.sendOTPRecover(emailRecover, genOtp);

                session.setAttribute("emailRecoverSendOtp", emailRecover);
                session.setAttribute("otpRecover", genOtp);
                session.setAttribute("curTime", curTime);

                session.setAttribute("sendOtpSuccess", "true");

                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("errMessRecover", "Lỗi");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

        } else if ("otp".equals(action)) {

            String inputotpRecover = request.getParameter("inputotpRecover");

            String otpRecover = (String) session.getAttribute("otpRecover");
            long curTime = (long) session.getAttribute("curTime");
            long nowTime = System.currentTimeMillis();
            long time = 3 * 60 * 1000;

            String emailRecoverSendOtp = (String) session.getAttribute("emailRecoverSendOtp");
            if (nowTime - curTime > time) {
                request.setAttribute("errMessOtpRecover", "OTP hết hạn, bạn cần thực hiện lại.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            if (emailRecoverSendOtp == null || emailRecoverSendOtp.trim().isEmpty() || otpRecover == null || otpRecover.trim().isEmpty()) {
                session.invalidate();
                request.setAttribute("errMessOtpRecover", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            if (inputotpRecover.equals(otpRecover)) {
                session.setAttribute("verifyOtpSuccess", "true");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            } else {
                request.setAttribute("emailRecoverSendOtp", emailRecoverSendOtp);
                request.setAttribute("errMessOtpRecover", "OTP không hợp lệ");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

        } else if ("recover-pass".equals(action)) {
            String passwordRecover = request.getParameter("passwordRecover");
            String comfirm_passwordRecover = request.getParameter("comfirm_passwordRecover");
            String emailRecoverSuccess = (String) session.getAttribute("emailRecoverSendOtp");

            boolean checkPass = isValidPassword(passwordRecover);
            if (!checkPass) {
                request.setAttribute("errMessPassRecover", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số, kí tự đặc biệt và không chứa dấu cách");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            if (!passwordRecover.equals(comfirm_passwordRecover)) {
                request.setAttribute("errMessPassRecover", "Mật khẩu không khớp");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            if (emailRecoverSuccess == null || emailRecoverSuccess.trim().isEmpty()) {
                session.invalidate();
                request.setAttribute("errMessPassRecover", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

            try {
                String hashPass = BCrypt.hashpw(passwordRecover, BCrypt.gensalt());

                AccountDAO accDao = new AccountDAO();
                boolean success = accDao.updatePass(emailRecoverSuccess, hashPass);

                if (success) {
                    session.invalidate();

                    HttpSession sessionMess = request.getSession();
                    sessionMess.setAttribute("successMessRecover", "Cập nhật mật khẩu thành công");
                    response.sendRedirect("login");

                } else {
                    request.setAttribute("errMessPassRecover", "Lỗi cập nhật mật khẩu");
                    request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                }
            } catch (Exception e) {
            }
        } else {
            response.sendRedirect("login");
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
