/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.DiscountDAO;
import DAO.PetDAO;
import Models.Discount;
import Models.Pet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author AD
 */
public class DiscountCheckServlet extends HttpServlet {

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
            out.println("<title>Servlet DiscountCheckServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DiscountCheckServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        // 1. Lấy danh sách pet từ form
        String[] petIds = request.getParameterValues("selectedPets");
        List<Pet> selectedPetList = new ArrayList<>();
        PetDAO petDAO = new PetDAO();
        double total = 0.0;

        if (petIds != null) {
            for (String idStr : petIds) {
                try {
                    int id = Integer.parseInt(idStr);
                    Pet pet = petDAO.getPetById(id);
                    if (pet != null) {
                        selectedPetList.add(pet);
                        total += pet.getPetPrice();
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua ID không hợp lệ
                }
            }
        }

        // 2. Kiểm tra mã giảm giá
        String code = request.getParameter("discountCode");
        DiscountDAO dao = new DiscountDAO();
        Discount d = dao.getActiveDiscountByCode(code);

        double discountAmount = 0.0;
        boolean isValid = false;
        String message;

        if (d == null || !d.isActive()) {
            message = "Mã giảm giá không tồn tại hoặc đã bị khóa.";
        } else if (d.getValidTo().before(new Date())) {
            message = "Mã giảm giá đã hết hạn.";
        } else if (total < d.getMinOrderAmount()) {
            message = "Đơn hàng chưa đạt mức tối thiểu để dùng mã này.";
        } else {
            if (d.getDiscountType().equalsIgnoreCase("percent")) {
                discountAmount = total * d.getDiscountValue() / 100.0;
            } else if (d.getDiscountType().equalsIgnoreCase("fixed")) {
                discountAmount = d.getDiscountValue();
            }
            isValid = true;
            message = "Áp dụng mã thành công!";
            request.setAttribute("discountCode", d.getDiscountCode());
        }

        // 3. Lưu vào request scope
        request.setAttribute("discountMessage", message);
        request.setAttribute("discountValid", isValid);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("total", total);
        request.setAttribute("selectedPets", selectedPetList);

        // 4. Lưu lại thông tin người nhận (guest)
        request.setAttribute("guestName", request.getParameter("guestName"));
        request.setAttribute("guestPhone", request.getParameter("guestPhone"));
        request.setAttribute("guestAddress", request.getParameter("guestAddress"));
        request.setAttribute("email", request.getParameter("email"));

        // 5. Chuyển tiếp về checkout.jsp (giữ toàn bộ dữ liệu qua request)
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
