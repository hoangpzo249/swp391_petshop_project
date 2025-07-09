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
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class RetryAddDiscountServlet extends HttpServlet {

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
            out.println("<title>Servlet RetryAddDiscountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RetryAddDiscountServlet at " + request.getContextPath() + "</h1>");
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

        List<Discount> failedDiscounts = (List<Discount>) request.getSession().getAttribute("failedDiscounts");
        String successMess = (String) request.getSession().getAttribute("successMess");
        String errMess = (String) request.getSession().getAttribute("errMess");

        request.setAttribute("failedDiscounts", failedDiscounts);
        request.setAttribute("successMess", successMess);
        request.setAttribute("errMess", errMess);

        request.getSession().removeAttribute("successMess");
        request.getSession().removeAttribute("errMess");

        request.getRequestDispatcher("update_failed_discount.jsp").forward(request, response);
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

        List<Discount> failedDiscounts = (List<Discount>) request.getSession().getAttribute("failedDiscounts");
        if (failedDiscounts == null) {
            response.sendRedirect("discountmanager");
            return;
        }

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
        String indexParam = request.getParameter("index");
        int index=Integer.parseInt(indexParam);
        Discount d = failedDiscounts.get(index);
        d.setActive("1".equals(status));
        d.setDescription(description);
        d.setDiscountCode(code);
        d.setDiscountType(type);

        boolean hasError = false;

        Discount existing = dao.getActiveDiscountByCode(code);
        if (existing != null) {
            d.setDiscountCodeErr("Mã giảm giá đã tồn tại.");
            hasError = true;
        } else {
            d.setDiscountCodeErr(null);
        }

        try {
            double val = Double.parseDouble(value.trim());
            d.setDiscountValue(val);
            if (val <= 0) {
                d.setDiscountValueErr("Giá trị giảm phải lớn hơn 0.");
                hasError = true;
            } else if ("Percent".equals(type) && val > 100) {
                d.setDiscountValueErr("Phần trăm giảm không được vượt quá 100%.");
                hasError = true;
            } else {
                d.setDiscountValueErr(null);
            }
        } catch (NumberFormatException e) {
            d.setDiscountValueErr("Giá trị giảm không hợp lệ.");
            hasError = true;
        }

        try {
            double minAmt = Double.parseDouble(min.trim());
            d.setMinOrderAmount(minAmt);
            if (minAmt < 0) {
                d.setMinOrderAmountErr("Đơn hàng tối thiểu phải >= 0.");
                hasError = true;
            } else {
                d.setMinOrderAmountErr(null);
            }
        } catch (NumberFormatException e) {
            d.setMinOrderAmountErr("Đơn hàng tối thiểu không hợp lệ.");
            hasError = true;
        }

        if ("Percent".equals(type)) {
            if (maxValue != null && !maxValue.trim().isEmpty()) {
                try {
                    double maxVal = Double.parseDouble(maxValue.trim());
                    d.setMaxValue(maxVal);
                    if (maxVal <= 0) {
                        d.setMaxValueErr("Giảm tối đa phải lớn hơn 0.");
                        hasError = true;
                    } else {
                        d.setMaxValueErr(null);
                    }
                } catch (NumberFormatException e) {
                    d.setMaxValueErr("Giảm tối đa không hợp lệ.");
                    hasError = true;
                }
            } else {
                d.setMaxValue(null);
                d.setMaxValueErr(null);
            }
        } else {
            d.setMaxValue(null);
            d.setMaxValueErr(null);
        }

        if (maxUsage != null && !maxUsage.trim().isEmpty()) {
            try {
                int maxUse = Integer.parseInt(maxUsage.trim());
                d.setMaxUsage(maxUse);
                if (maxUse <= 0) {
                    d.setMaxUsageErr("Số lần sử dụng phải lớn hơn 0.");
                    hasError = true;
                } else {
                    d.setMaxUsageErr(null);
                }
            } catch (NumberFormatException e) {
                d.setMaxUsageErr("Số lần sử dụng không hợp lệ.");
                hasError = true;
            }
        } else {
            d.setMaxUsage(null);
            d.setMaxUsageErr(null);
        }

        try {
            Date fromDate = Date.valueOf(from);
            Date toDate = Date.valueOf(to);
            d.setValidFrom(fromDate);
            d.setValidTo(toDate);

            if (!toDate.after(fromDate)) {
                d.setValidToErr("Ngày kết thúc phải sau ngày bắt đầu.");
                hasError = true;
            } else if (toDate.before(new Date(System.currentTimeMillis()))) {
                d.setValidToErr("Ngày hết hạn không được trong quá khứ.");
                hasError = true;
            } else {
                d.setValidToErr(null);
            }
            d.setValidFromErr(null);
        } catch (IllegalArgumentException e) {
            d.setValidFromErr("Ngày không hợp lệ.");
            hasError = true;
        }

        if (hasError) {
            response.sendRedirect("retryadddiscount");
            return;
        }

        dao.addDiscount(d);
        failedDiscounts.remove(index);

        if (failedDiscounts.isEmpty()) {
            request.getSession().removeAttribute("failedDiscounts");
            request.getSession().setAttribute("successMess", "Tất cả mã đã được lưu thành công.");
            response.sendRedirect("discountmanager");
        } else {
            request.getSession().setAttribute("successMess", "Lưu thành công mã " + code + ".");
            response.sendRedirect("retryadddiscount");
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
