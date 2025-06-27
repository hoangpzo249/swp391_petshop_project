/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.DiscountDAO;
import Models.Discount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class UpdateDiscountServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateDiscountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateDiscountServlet at " + request.getContextPath() + "</h1>");
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

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            DiscountDAO dao = new DiscountDAO();
            Discount d = dao.getDiscountById(id);
            if (d != null) {
                request.setAttribute("discount", d);
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
            } else {
                response.sendRedirect("discountmanager");
            }
        } catch (Exception e) {
            response.sendRedirect("discountmanager");
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

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String code = request.getParameter("code");
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        String description = request.getParameter("description");
        String from = request.getParameter("validFrom");
        String to = request.getParameter("validTo");
        String min = request.getParameter("minAmount");
        String maxUsage = request.getParameter("maxUsage");
        String maxValue = request.getParameter("maxValue");
        String status = request.getParameter("status");

        DiscountDAO dao = new DiscountDAO();

        try {
            Discount d = new Discount();
            d.setDiscountId(Integer.parseInt(id));
            d.setDiscountCode(code.trim());
            d.setDiscountType(type);
            d.setDiscountValue(Double.parseDouble(value));
            d.setDescription(description);
            d.setValidFrom(Date.valueOf(from));
            d.setValidTo(Date.valueOf(to));
            d.setMinOrderAmount(Double.parseDouble(min));
            d.setActive("1".equals(status));

            if (maxUsage != null && !maxUsage.isEmpty()) {
                d.setMaxUsage(Integer.parseInt(maxUsage));
            } else {
                d.setMaxUsage(null);
            }

            if (maxValue != null && !maxValue.isEmpty()) {
                d.setMaxValue(Double.parseDouble(maxValue));
            } else {
                d.setMaxValue(null);
            }

            
            Discount existing = dao.getActiveDiscountByCode(code);
            if (existing != null && existing.getDiscountId() != d.getDiscountId()) {
                request.setAttribute("errMess", "Mã giảm giá này đã tồn tại. Vui lòng chọn mã khác.");
                request.setAttribute("discount", d); 
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }

            boolean success = dao.updateDiscount(d);

            if (success) {
                request.getSession().setAttribute("successMess", "Cập nhật mã giảm giá thành công.");
                response.sendRedirect("discountmanager");
            } else {
                request.setAttribute("errMess", "Không thể cập nhật mã giảm giá.");
                request.setAttribute("discount", d);
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMess", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("update_discount.jsp").forward(request, response);
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
