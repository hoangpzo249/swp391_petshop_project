/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AccountDAO;
import DAO.CartDAO;
import Models.Account;
import Models.Cart;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null || action.trim().isEmpty()) {
            request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
        } else if ("resend-otp".equals(action)) {
            String emailLogin = (String) session.getAttribute("emailLogin");
            String roleLogin = (String) session.getAttribute("roleLogin");

            if (emailLogin != null) {
                String otpResendLogin = otp();

                long curTimeSendOtp = (long) session.getAttribute("curTimeSendOtp");
                long nowTime = System.currentTimeMillis();

                long resendOtp = 60 * 1000;

                if (nowTime - curTimeSendOtp < resendOtp) {
                    long countTimeResend = (resendOtp - (nowTime - curTimeSendOtp)) / 1000;
                    request.setAttribute("errMessLoginOtp", "Bạn cần chờ " + countTimeResend + " GIÂY để gửi lại OTP");
                    request.setAttribute("sendOTP2FA", "true");
                    request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                    return;
                }

                try {
                    request.setAttribute("sendOTP2FA", "true");
                    EmailSender.sendOTP2FALogin(emailLogin, otpResendLogin, roleLogin);

                    session.setAttribute("otplogin", otpResendLogin);

                    session.setAttribute("curTimeSendOtp", nowTime);

                    request.setAttribute("successMessLogin", "Gửi lại OTP thành công");
                } catch (Exception e) {
                    request.setAttribute("errMessLoginOtp", "Bạn cần thực hiện lại");
                }
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            } else {
                session.invalidate();
                response.sendRedirect("login");
            }
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

        if ("otp".equals(action)) {
            String inputotpLogin = request.getParameter("inputotpLogin");
            String otplogin = (String) session.getAttribute("otplogin");
            long curTimeSendOtp = (long) session.getAttribute("curTimeSendOtp");

            long nowTime = System.currentTimeMillis();
            long time = 5 * 60 * 1000;

            if (nowTime - curTimeSendOtp > time) {
                request.setAttribute("errMessLogin", "OTP hết hạn sau 5 phút, bạn cần thực hiện lại.");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                return;
            }

            if (otplogin == null || otplogin.trim().isEmpty()) {
                session.invalidate();
                request.setAttribute("errMessLogin", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                return;
            }

            if (inputotpLogin != null && !inputotpLogin.trim().isEmpty() && otplogin.equals(inputotpLogin)) {
                session.removeAttribute("otplogin");
                session.removeAttribute("curTimeSendOtp");
                session.removeAttribute("roleLogin");
                session.removeAttribute("emailLogin");

                Account acc = (Account) session.getAttribute("tempAcc");
                String role = acc.getAccRole();

                session.setAttribute("userAccount", acc);

                if ("Admin".equals(role)) {
                    session.setAttribute("successMessage", "Chào Admin!");
                    response.sendRedirect("admin-panel");
                } else if ("Manager".equals(role)) {
                    session.setAttribute("successMess", "Chào Manager!");
                    response.sendRedirect("displayrevenuestatistic");
                } else if ("Seller".equals(role)) {
                    session.setAttribute("successMess", "Chào Seller!");
                    response.sendRedirect("displaysalesstatistic");
                } else if ("Shipper".equals(role)) {
                    session.setAttribute("successMessage", "Chào Shipper!");
                    response.sendRedirect("shipper_panel");
                }
            } else {
                request.setAttribute("errMessLoginOtp", "Mã OTP không hợp lệ");
                request.setAttribute("sendOTP2FA", "true");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            }
            return;
        }

        try {
            String emailLogin = request.getParameter("emailLogin");
            String passwordLogin = request.getParameter("passwordLogin");
            String remember = request.getParameter("remember");

            AccountDAO accDao = new AccountDAO();
            Account acc = accDao.isLoginAcc(emailLogin, passwordLogin);
            CartDAO cartDAO = new CartDAO();

            if (acc != null) {

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

//
                if ("Admin".equals(role) || "Manager".equals(role) || "Seller".equals(role) || "Shipper".equals(role)) {
                    String otplogin = otp();
                    long curTimeSendOtp = System.currentTimeMillis();
                    try {
                        EmailSender.sendOTP2FALogin(emailLogin, otplogin, role);

                        request.setAttribute("sendOTP2FA", "true");
                        session.setAttribute("otplogin", otplogin);
                        session.setAttribute("sendemailLogin", emailLogin);
                        session.setAttribute("curTimeSendOtp", curTimeSendOtp);
                        session.setAttribute("emailLogin", emailLogin);
                        session.setAttribute("roleLogin", role);
                        session.setAttribute("tempAcc", acc);
                    } catch (Exception e) {
                        request.setAttribute("errMessLogin", "Gửi OTP không thành công, bạn cần thực hiện lại");
                    }

                    request.getRequestDispatcher("login_account_page.jsp").forward(request, response);

                } else {
                    session.setAttribute("userAccount", acc);

                    session.setAttribute("loginSuccess", "Chào mừng " + fname + " !");
                    response.sendRedirect("homepage");
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
