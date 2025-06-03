/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

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
            String email = (String) session.getAttribute("email");

            if (email != null || !email.trim().isEmpty()) {
                String otp = otp();
                try {
                    EmailSender.sendOTPRecover(email, otp);

                    session.setAttribute("otp", otp);
                    request.setAttribute("successMess", "Gửi lại OTP thành công");
                    request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("errMess", "Bạn cần thực hiện lại");
                    request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                }
            } else {
                session.invalidate();
                request.setAttribute("errMess", "Hết thời gian chờ, bạn cần thực hiện lại.");
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
            String email = request.getParameter("email");
            if (email == null || email.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            AccountDAO accDao = new AccountDAO();
            boolean success = accDao.isEmailExist(email);

            if (!success) {
                request.setAttribute("email", email);
                request.setAttribute("errMess", "Email của bạn không tồn tại trong hệ thống");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            String genOtp = otp();
            try {
                EmailSender.sendOTPRecover(email, genOtp);

                session.setAttribute("email", email);
                session.setAttribute("otp", genOtp);
                session.setAttribute("sendOtpSuccess", "true");

                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("email", email);
                request.setAttribute("errMess", "Lỗi");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

        } else if ("otp".equals(action)) {

            String inputotp = request.getParameter("inputotp");

            if (inputotp == null || inputotp.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            String otp = (String) session.getAttribute("otp");

            if (otp == null || otp.trim().isEmpty()) {
                session.invalidate();
                request.setAttribute("errMess", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

            if (inputotp.equals(otp)) {
                session.setAttribute("verifyOtpSuccess", "true");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            } else {
                request.setAttribute("errMess", "OTP không hợp lệ");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }
        } else if ("recover-pass".equals(action)) {
            String password = request.getParameter("password");
            String comfirm_password = request.getParameter("comfirm_password");
            String email = (String) session.getAttribute("email");

            if (password == null || password.trim().isEmpty()
                    || comfirm_password == null || comfirm_password.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            if (!password.equals(comfirm_password)) {
                request.setAttribute("errMess", "Mật khẩu không khớp");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                return;
            }

            if (email == null || email.trim().isEmpty()) {
                session.invalidate();
                request.setAttribute("errMess", "Hết thời gian chờ, bạn cần thực hiện lại.");
                request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
            }

            try {
                String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());

                AccountDAO accDao = new AccountDAO();
                boolean success = accDao.updatePass(email, hashPass);

                if (success) {
                    session.invalidate();
                    HttpSession sessionMess = request.getSession();
                    sessionMess.setAttribute("successMessRecover", "Cập nhật mật khẩu thành công");
                    response.sendRedirect("login");
                } else {
                    request.setAttribute("errMess", "Lỗi cập nhật mật khẩu");
                    request.getRequestDispatcher("recover_account_page.jsp").forward(request, response);
                }
            } catch (Exception e) {
            }
        } else {
            response.sendRedirect("login");
        }
    }

    public String otp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(1000000);
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
