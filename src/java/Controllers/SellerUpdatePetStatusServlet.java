/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.PetDAO;
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
public class SellerUpdatePetStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdatePetStatus</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePetStatus at " + request.getContextPath() + "</h1>");
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
        PetDAO _daopet = new PetDAO();
        HttpSession session = request.getSession();
        Account account=(Account) session.getAttribute("userAccount");
        if (account==null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        int petId = Integer.parseInt(request.getParameter("petId"));
        int targetStatus = Integer.parseInt(request.getParameter("status"));
        String referer = request.getHeader("referer");
        int pendingOrderId = _daopet.getOrderIdForPet(petId);

        switch (targetStatus) {
            case 1:
                if (_daopet.updatePetStatusById(petId, targetStatus)) {
                    session.setAttribute("successMess", "Thú cưng #" + petId + " đã hiển thị thành công.");
                } else {
                    session.setAttribute("errMess", "Hiển thị thú cưng #" + petId + " thất bại");
                }
                break;
            case 0:
                if (pendingOrderId == 0) {
                    if (_daopet.updatePetStatusById(petId, targetStatus)) {
                        session.setAttribute("successMess", "Thú cưng #" + petId + " đã ẩn thành công.");
                    } else {
                        session.setAttribute("errMess", "Ẩn thú cưng #" + petId + " thất bại");
                    }
                } else {
                    session.setAttribute("errMess", "Không thể ẩn thú cưng #" + petId + ", Thú cưng ở trong đơn hàng #" + pendingOrderId);
                }
                break;
            default:
                throw new AssertionError();
        }
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("displayallpet");
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
