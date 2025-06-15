/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.AccountDAO;
import DAO.CartDAO;
import Models.Account;
import Models.Cart;
import Utils.GoogleUser;
import Utils.GoogleUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
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

        if (accessToken == null || accessToken.isEmpty()) {
            request.setAttribute("errMess", "Không có Token");
            request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            return;

        }
        GoogleUser ggUser = GoogleUtils.getUserInfo(accessToken);

        if (ggUser == null || ggUser.getEmail() == null) {
            request.setAttribute("errMess", "Không có thông tin User");
            request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            return;

        }

        AccountDAO accDao = new AccountDAO();

        String fullName = ggUser.getGiven_name();

        boolean checkEmail = accDao.isEmailExist(ggUser.getEmail());
        if (checkEmail) {
            Account acc = accDao.ggByEmail(ggUser.getEmail());
            String status = acc.getAccStatus();
            if ("Inactive".equals(status)) {
                request.setAttribute("errMess", "Tài khoản của bạn đã bị vô hiệu hóa.");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
                return;
            }

            session.setAttribute("userAccount", acc);
            List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
            if (guestCart != null && !guestCart.isEmpty()) {
                CartDAO cartDAO = new CartDAO();
                for (Cart c : guestCart) {
                    if (!cartDAO.petInCart(acc.getAccId(), c.getPetId())) {
                        cartDAO.addToPetCart(acc.getAccId(), c.getPetId());
                    }
                }
                session.removeAttribute("guestCart");
            }

            String role = acc.getAccRole();

            if ("Admin".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Admin!");
                response.sendRedirect("homepage");
            } else if ("Manager".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Manager!");
                response.sendRedirect("homepage");
            } else if ("Seller".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Seller!");
                response.sendRedirect("homepage");
            } else if ("Shipper".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Shipper!");
                response.sendRedirect("homepage");
            } else if ("Customer".equals(role)) {
                session.setAttribute("loginSuccess", "Chào mừng " + fullName + "!");
                response.sendRedirect("homepage");
            } else {
                request.setAttribute("errMess", "Bạn cần có quyền truy cập");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            }
        } else {
            String username = genUsername(ggUser.getEmail());
            Account newAcc = new Account();

            newAcc.setAccUsername(username);
            newAcc.setAccEmail(ggUser.getEmail());
            newAcc.setAccFname(ggUser.getGiven_name());
            newAcc.setAccLname(ggUser.getFamily_name());
            newAcc.setAccPhoneNumber("Chưa cập nhật");

            accDao.registerAccByGG(newAcc);

            Account acc = accDao.ggByEmail(ggUser.getEmail());
            session.setAttribute("userAccount", acc);
            String role = acc.getAccRole();

//            System.out.println(acc.getAccPhoneNumber());
            if ("Admin".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Admin!");
                response.sendRedirect("homepage");
            } else if ("Manager".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Manager!");
                response.sendRedirect("homepage");
            } else if ("Seller".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Seller!");
                response.sendRedirect("homepage");
            } else if ("Shipper".equals(role)) {
                session.setAttribute("loginSuccess", "Chào Shipper!");
                response.sendRedirect("homepage");
            } else if ("Customer".equals(role)) {
                session.setAttribute("loginSuccess", "Chào mừng " + fullName + "!");
                response.sendRedirect("homepage");
            } else {
                request.setAttribute("errMess", "Bạn cần có quyền truy cập");
                request.getRequestDispatcher("login_account_page.jsp").forward(request, response);
            }
        }

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
