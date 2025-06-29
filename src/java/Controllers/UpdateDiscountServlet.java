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

        Discount d = new Discount();
        DiscountDAO dao = new DiscountDAO();

        try {
            int discountId = Integer.parseInt(id);
            d.setDiscountId(discountId);
            d.setDiscountCode(code);
            d.setDiscountType(type);
            d.setDescription(description);
            d.setActive("1".equals(status));
            request.setAttribute("discount", d);

            double val;
            try {
                val = Double.parseDouble(value.trim());
            if (val <= 0) {
                request.setAttribute("errMess", "Giá trị giảm phải lớn hơn 0.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }
            } catch (NumberFormatException e) {
                request.setAttribute("errMess", "Giá trị giảm không hợp lệ. Vui lòng nhập số.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }
            if ("Percent".equals(type) && val > 100) {
                request.setAttribute("errMess", "Phần trăm giảm không được vượt quá 100%.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }
            d.setDiscountValue(val);

            double minAmt;
            try {
                minAmt = Double.parseDouble(min.trim());
                if (minAmt < 0) {
                    request.setAttribute("errMess", "Đơn hàng tối thiểu không được nhỏ hơn 0.");
                    request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errMess", "Đơn hàng tối thiểu không hợp lệ. Vui lòng nhập số.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }

            d.setMinOrderAmount(minAmt);

            if (maxValue != null && !maxValue.trim().isEmpty()) {
                try {
                    double maxVal = Double.parseDouble(maxValue.trim());
                    if (maxVal < 0) {
                        request.setAttribute("errMess", "Giảm tối đa phải là số không âm.");
                        request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                        return;
                    }
                    d.setMaxValue(maxVal);
                } catch (NumberFormatException e) {
                    request.setAttribute("errMess", "Giảm tối đa không hợp lệ. Vui lòng nhập số.");
                    request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                    return;
                }
            }

            if (maxUsage != null && !maxUsage.trim().isEmpty()) {
                try {
                    int maxUse = Integer.parseInt(maxUsage.trim());
                    if (maxUse < 0) {
                        request.setAttribute("errMess", "Số lần sử dụng không được nhỏ hơn 0.");
                        request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                        return;
                    }
                    d.setMaxUsage(maxUse);
                } catch (NumberFormatException e) {
                    request.setAttribute("errMess", "Số lần sử dụng không hợp lệ. Vui lòng nhập số nguyên.");
                    request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                    return;
                }
            }

            Date fromDate, toDate;
            fromDate = Date.valueOf(from);
            toDate = Date.valueOf(to);
            if (fromDate.after(toDate)) {
                request.setAttribute("errMess", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }
            Date today = new Date(System.currentTimeMillis());
            if (toDate.before(today)) {
                request.setAttribute("errMess", "Ngày hết hạn không được trong quá khứ.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
                return;
            }
            d.setValidFrom(fromDate);
            d.setValidTo(toDate);

            boolean updated = dao.updateDiscount(d);
            if (updated) {
                request.getSession().setAttribute("successMess", "Cập nhật mã giảm giá thành công.");
                response.sendRedirect("discountmanager");
            } else {
                request.setAttribute("errMess", "Không thể cập nhật mã giảm giá.");
                request.getRequestDispatcher("update_discount.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMess", "Lỗi hệ thống: " + e.getMessage());
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
