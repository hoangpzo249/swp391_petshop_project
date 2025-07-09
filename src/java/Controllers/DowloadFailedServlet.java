/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import Models.Discount;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class DowloadFailedServlet extends HttpServlet {

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
            out.println("<title>Servlet DowloadFailedServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DowloadFailedServlet at " + request.getContextPath() + "</h1>");
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
        if (failedDiscounts == null || failedDiscounts.isEmpty()) {
            response.sendRedirect("retryadddiscount");
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=failed_discounts.xlsx");

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Failed Discounts");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFont(headerFont);

            String[] columns = {
                "Mã Giảm Giá",
                "Loại",
                "Giá Trị",
                "Mô Tả",
                "Hiệu Lực Từ",
                "Hiệu Lực Đến",
                "Min Order",
                "Max Usage",
                "Trạng Thái",
                "Giảm Tối Đa",
                "Lý Do Lỗi"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 6000);
            }

            int rowIdx = 1;
            for (Discount d : failedDiscounts) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(d.getDiscountCode() != null ? d.getDiscountCode() : "");
                row.createCell(1).setCellValue(d.getDiscountType() != null ? d.getDiscountType() : "");
                row.createCell(2).setCellValue(d.getDiscountValue());
                row.createCell(3).setCellValue(d.getDescription() != null ? d.getDescription() : "");
                row.createCell(4).setCellValue(d.getValidFrom() != null ? d.getValidFrom().toString() : "");
                row.createCell(5).setCellValue(d.getValidTo() != null ? d.getValidTo().toString() : "");
                row.createCell(6).setCellValue(d.getMinOrderAmount());
                row.createCell(7).setCellValue(d.getMaxUsage() != null ? d.getMaxUsage() : 0);
                row.createCell(8).setCellValue(d.isActive() ? "Active" : "Deactive");
                row.createCell(9).setCellValue(d.getMaxValue() != null ? d.getMaxValue() : 0);

                String errorMsg = "";
                if (d.getDiscountCodeErr() != null) {
                    errorMsg += "- " + d.getDiscountCodeErr() + "\n";
                }
                if (d.getDiscountValueErr() != null) {
                    errorMsg += "- " + d.getDiscountValueErr() + "\n";
                }
                if (d.getMinOrderAmountErr() != null) {
                    errorMsg += "- " + d.getMinOrderAmountErr() + "\n";
                }
                if (d.getMaxUsageErr() != null) {
                    errorMsg += "- " + d.getMaxUsageErr() + "\n";
                }
                if (d.getMaxValueErr() != null) {
                    errorMsg += "- " + d.getMaxValueErr() + "\n";
                }
                if (d.getValidFromErr() != null) {
                    errorMsg += "- " + d.getValidFromErr() + "\n";
                }
                if (d.getValidToErr() != null) {
                    errorMsg += "- " + d.getValidToErr() + "\n";
                }

                Cell errorCell = row.createCell(10);
                errorCell.setCellValue(errorMsg);

                CellStyle wrapStyle = workbook.createCellStyle();
                wrapStyle.setWrapText(true);
                wrapStyle.setVerticalAlignment(VerticalAlignment.TOP);
                errorCell.setCellStyle(wrapStyle);

                row.setHeight((short) -1);
            }
            for (int i = 0; i <= 10; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
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
