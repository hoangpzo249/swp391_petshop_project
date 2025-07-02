/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import Models.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
public class ManagerUpdateBreedStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerUpdateBreedStatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerUpdateBreedStatusServlet at " + request.getContextPath() + "</h1>");
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
        BreedDAO _dao = new BreedDAO();
        StringBuilder errMess = new StringBuilder();
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Manager")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
        String referer = request.getHeader("referer");

        String breedId = request.getParameter("breedId");
        String breedStatus = request.getParameter("status");

        errMess.append(validateBreedInput(breedId, breedStatus));

        if (errMess.length() > 0) {
            session.setAttribute("errMess", errMess.toString());
            if (!referer.isBlank() && referer != null) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect("displaybreed");
            }
        }

        switch (breedStatus) {
            case "1":
                if (_dao.updateBreedStatusById(Integer.parseInt(breedId), Integer.parseInt(breedStatus))) {
                    session.setAttribute("successMess", "Giống #" + breedId + " đã hiển thị thành công.");
                } else {
                    session.setAttribute("errMess", "Hiển thị giống #" + breedId + " thất bại.");
                }
                break;
            case "0":
                if (_dao.updateBreedStatusById(Integer.parseInt(breedId), Integer.parseInt(breedStatus))) {
                    session.setAttribute("successMess", "Giống #" + breedId + " đã ẩn thành công.");
                } else {
                    session.setAttribute("errMess", "Ẩn giống #" + breedId + " thất bại.");
                }
                break;
            default:
                throw new AssertionError();
        }
        if (!referer.isBlank() && referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("displaybreed");
        }
    }

    public static String validateBreedInput(String id, String status) {
        StringBuilder stringCheck = new StringBuilder();

        if (id.isEmpty() || !id.matches("^[0-9]+$")) {
            stringCheck.append("ID, ");
        }
        if (status.isEmpty() || !status.equals("1") && !status.equals("0")) {
            stringCheck.append("trạng thái, ");
        }

        if (stringCheck.length() > 0) {
            stringCheck.setLength(stringCheck.length() - 2);
            stringCheck.append(" của giống thú cưng không hợp lệ.");

            String result = stringCheck.toString();
            return result.substring(0, 1).toUpperCase() + result.substring(1);
        }

        return "";
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
