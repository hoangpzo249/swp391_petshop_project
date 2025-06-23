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
 * @author AD
 */
public class AddDiscountServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet AddDiscountServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddDiscountServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
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

        
        request.setAttribute("code", code);
        request.setAttribute("type", type);
        request.setAttribute("value", value);
        request.setAttribute("description", description);
        request.setAttribute("validFrom", from);
        request.setAttribute("validTo", to);
        request.setAttribute("minAmount", min);
        request.setAttribute("maxUsage", maxUsage);
        request.setAttribute("maxValue", maxValue);
        request.setAttribute("status", status);

        try {
            Discount d = new Discount();
            d.setDiscountCode(code);
            d.setDiscountType(type);
            d.setDiscountValue(Double.parseDouble(value.trim()));
            d.setDescription(description);
            d.setValidFrom(Date.valueOf(from));
            d.setValidTo(Date.valueOf(to));
            d.setMinOrderAmount(Double.parseDouble(min.trim()));

            if (maxUsage != null && !maxUsage.isEmpty()) {
                d.setMaxUsage(Integer.parseInt(maxUsage.trim()));
            } else {
                d.setMaxUsage(null);
            }

            if (maxValue != null && !maxValue.isEmpty()) {
                d.setMaxValue(Double.parseDouble(maxValue.trim()));
            } else {
                d.setMaxValue(null);
            }

            d.setUsageCount(0);
            d.setActive("1".equals(status));

            DiscountDAO dao = new DiscountDAO();
            boolean success = dao.addDiscount(d);

            if (success) {
                request.getSession().setAttribute("successMess", "Thêm mã giảm giá thành công.");
                response.sendRedirect("discountmanager");
            } else {
                request.setAttribute("errMess", "Không thể thêm mã giảm giá. Hãy kiểm tra lại dữ liệu.");
                request.getRequestDispatcher("adddiscount.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMess", "Lỗi nhập dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("adddiscount.jsp").forward(request, response);
        }
    }



    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
