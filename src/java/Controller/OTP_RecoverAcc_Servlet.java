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

/**
 *
 * @author HuyHoang
 */
public class OTP_RecoverAcc_Servlet extends HttpServlet {

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
            out.println("<title>Servlet OTP_RecoverAcc_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OTP_RecoverAcc_Servlet at " + request.getContextPath() + "</h1>");
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

        if ("resend-otp".equals(action)) {
            String email = (String) session.getAttribute("email");
            String newotp = otp();
            session.setAttribute("newotp", newotp);
            try {
//                session.setAttribute("email", email);
                EmailSender.sendOTP(email, newotp);
                request.setAttribute("successMess", "Mã OTP mới đã được gửi đến Email của bạn");
            } catch (Exception e) {
                request.setAttribute("errMess", "Không thể gửi lại OTP");
            }
        }

        request.getRequestDispatcher("otp_recoveracc_page.jsp").forward(request, response);
    }

    public String otp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(1000000);
        return String.valueOf(otp);
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
            String inputOTP = request.getParameter("inputotp");
            HttpSession session = request.getSession();

            String otp = (String) session.getAttribute("otp");
            String newotp = (String) session.getAttribute("newotp");

            if (inputOTP != null && !inputOTP.trim().isEmpty()
                    && (otp != null && otp.equals(inputOTP))
                    || (newotp != null && newotp.equals(inputOTP))) {
                session.setAttribute("successMess", "Nhập mật khẩu mới của bạn");
                response.sendRedirect("recover-password");

            } else {
                request.setAttribute("errMess", "Mã OTP không hợp lệ");
                request.getRequestDispatcher("otp_recoveracc_page.jsp").forward(request, response);
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
