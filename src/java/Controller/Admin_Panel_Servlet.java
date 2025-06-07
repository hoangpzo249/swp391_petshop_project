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
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HuyHoang
 */
public class Admin_Panel_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Admin_Panel_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Admin_Panel_Servlet at " + request.getContextPath() + "</h1>");
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
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String act = request.getParameter("act");

        AccountDAO accDao = new AccountDAO();
        List<Account> accList = accDao.getAllAccount();
        request.setAttribute("accList", accList);

        List<Account> accListNew = accDao.get10AccountNew();
        request.setAttribute("accListNew", accListNew);
        
        if ("account".equals(action) && "all".equals(type) && id != null && act == null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId);

            request.setAttribute("accDetail", acc);

            request.getRequestDispatcher("admin_accDetail_page.jsp").forward(request, response);

        } else if ("account".equals(action) && "all".equals(type) && "reset-pass".equals(act) && id != null) {
            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId);

            request.setAttribute("resetpass", acc);

            System.out.println("ID:" + acc.getAccId());
            request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);

        } else if ("account".equals(action) && "all".equals(type)) {
            request.getRequestDispatcher("admin_accList_page.jsp").forward(request, response);

        } else{
            request.getRequestDispatcher("admin_account_page.jsp").forward(request, response);
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
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String act = request.getParameter("act");

        AccountDAO accDao = new AccountDAO();

        if ("account".equals(action) && "all".equals(type) && "reset-pass".equals(act) && id != null) {

            int accId = Integer.parseInt(id);

            Account acc = accDao.getAccId(accId);

            request.setAttribute("resetpass", acc);

//            System.out.println("ID:" + acc.getAccId());
            String password = request.getParameter("password");
            String confirm_Password = request.getParameter("confirm_Password");
            String adminPass = request.getParameter("adminPassword");

            if (password == null || password.trim().isEmpty()
                    || confirm_Password == null || confirm_Password.trim().isEmpty()
                    || adminPass == null || adminPass.trim().isEmpty()) {
                request.setAttribute("errMess", "Bạn cần điền đủ thông tin");
                request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
                return;
            }

            if (!password.equals(confirm_Password)) {
                request.setAttribute("errMess", "Mật khẩu không khớp");
                request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
                return;
            }

            boolean checkPassStrong = isValidPassword(password);
            if (!checkPassStrong) {
                request.setAttribute("errMess", "Mật khẩu phải nhiều hơn 8 kí tự bao gồm chữ thường, chữ hoa, số và kí tự đặc biệt");
                request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
                return;
            }

            boolean checkPassAdmin = accDao.checkPassAdmin(adminPass);
            if (!checkPassAdmin) {
                request.setAttribute("errMess", "Mật khẩu Admin không hợp lệ");
                
                request.setAttribute("password", password);
                request.setAttribute("confirm_Password", confirm_Password);
                
                request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
                return;
            }
            
            String email = acc.getAccEmail();
            boolean checkPass = accDao.checkPass(email, password);
            if (!checkPass) {
                String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());
                
                boolean updateaAccPass = accDao.updatePass(email, hashPass);
                if (updateaAccPass) {
                    acc.setAccPassword(password);
                    request.setAttribute("successMess", "Mật khẩu cập nhật thành công");
                    request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
                } else {
                    request.setAttribute("errMess", "Đổi mật khẩu không thành công, bạn cần thử lại");
                    request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errMess", "Mật khẩu không được trùng với mật khẩu cũ");
                request.getRequestDispatcher("admin_accResetPass_Page.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("admin_account_page.jsp").forward(request, response);
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
