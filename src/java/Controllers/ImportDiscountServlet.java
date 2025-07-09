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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
@MultipartConfig
public class ImportDiscountServlet extends HttpServlet {

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
            out.println("<title>Servlet ImportDiscountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ImportDiscountServlet at " + request.getContextPath() + "</h1>");
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
        Part filePart = request.getPart("excelFile");
        InputStream inputStream = filePart.getInputStream();

        List<Discount> failedDiscounts = new ArrayList<>();
        DiscountDAO dao = new DiscountDAO();
        int successCount = 0, failCount = 0;

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 0;
            Row firstRow = sheet.getRow(0);
            boolean isTemplate = false;

            if (firstRow != null) {
                Cell cell = firstRow.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String val = cell.getStringCellValue().toLowerCase();
                    if (val.contains("template")) {
                        isTemplate = true;
                    }
                }
            }
            int startRow = isTemplate ? 6 : 1;

            for (Row row : sheet) {
                if (rowNum < startRow) {
                    rowNum++;
                    continue;
                }
                try {
                    Discount d = new Discount();
                    boolean hasError = false;

                    Cell codeCell = row.getCell(0);
                    String code = "";

                    if (codeCell != null) {
                        if (codeCell.getCellType() == CellType.STRING) {
                            code = codeCell.getStringCellValue().trim();
                        } else if (codeCell.getCellType() == CellType.NUMERIC) {
                            code = String.valueOf((long) codeCell.getNumericCellValue()).trim();
                        } else {
                            code = codeCell.toString().trim();
                        }
                    }
                    d.setDiscountCode(code);
                    if (code.isEmpty()) {
                        d.setDiscountCodeErr("Mã giảm giá không được để trống.");
                        hasError = true;
                    } else if (dao.getActiveDiscountByCode(code) != null) {
                        d.setDiscountCodeErr("Mã giảm giá đã tồn tại.");
                        hasError = true;
                    }

                    String type = row.getCell(1).getStringCellValue().trim();
                    d.setDiscountType(type);

                    double value = row.getCell(2).getNumericCellValue();
                    d.setDiscountValue(value);
                    if (value <= 0) {
                        d.setDiscountValueErr("Giá trị giảm phải >0.");
                        hasError = true;
                    } else if ("Percent".equals(type) && value > 100) {
                        d.setDiscountValueErr("Phần trăm giảm không vượt quá 100%.");
                        hasError = true;
                    }

                    if (row.getCell(3) != null && row.getCell(3).getCellType() != CellType.BLANK) {
                        d.setDescription(row.getCell(3).getStringCellValue().trim());
                    }

                    Date fromDate, toDate;
                    try {
                        Cell fromCell = row.getCell(4);
                        if (fromCell.getCellType() == CellType.NUMERIC) {
                            fromDate = new Date(fromCell.getDateCellValue().getTime());
                        } else {
                            fromDate = Date.valueOf(fromCell.getStringCellValue().trim());
                        }
                        d.setValidFrom(fromDate);
                        d.setValidFromErr(null);
                    } catch (Exception e) {
                        d.setValidFromErr("Ngày bắt đầu không hợp lệ.");
                        hasError = true;
                        fromDate = null;
                    }

                    try {
                        Cell toCell = row.getCell(5);
                        if (toCell.getCellType() == CellType.NUMERIC) {
                            toDate = new Date(toCell.getDateCellValue().getTime());
                        } else {
                            toDate = Date.valueOf(toCell.getStringCellValue().trim());
                        }
                        d.setValidTo(toDate);
                        if (fromDate != null && !toDate.after(fromDate)) {
                            d.setValidToErr("Ngày kết thúc phải sau ngày bắt đầu.");
                            hasError = true;
                        } else if (toDate.before(new Date(System.currentTimeMillis()))) {
                            d.setValidToErr("Ngày kết thúc không được trong quá khứ.");
                            hasError = true;
                        } else {
                            d.setValidToErr(null);
                        }
                    } catch (Exception e) {
                        d.setValidToErr("Ngày kết thúc không hợp lệ.");
                        hasError = true;
                    }

                    double minOrder = row.getCell(6).getNumericCellValue();
                    d.setMinOrderAmount(minOrder);
                    if (minOrder < 0) {
                        d.setMinOrderAmountErr("Đơn hàng tối thiểu >=0.");
                        hasError = true;
                    }

                    if (row.getCell(7) != null && row.getCell(7).getCellType() != CellType.BLANK) {
                        int maxUsage = (int) row.getCell(7).getNumericCellValue();
                        d.setMaxUsage(maxUsage);
                        if (maxUsage <= 0) {
                            d.setMaxUsageErr("Số lần dùng phải >0.");
                            hasError = true;
                        }
                    }

                    if (row.getCell(8) != null && row.getCell(8).getCellType() != CellType.BLANK) {
                        Cell activeCell = row.getCell(8);
                        boolean activeValue;
                        if (activeCell.getCellType() == CellType.BOOLEAN) {
                            activeValue = activeCell.getBooleanCellValue();
                        } else if (activeCell.getCellType() == CellType.NUMERIC) {
                            activeValue = activeCell.getNumericCellValue() != 0;
                        } else {
                            String activeStr = activeCell.getStringCellValue().trim();
                            activeValue = activeStr.equalsIgnoreCase("true") || activeStr.equalsIgnoreCase("1");
                        }
                        d.setActive(activeValue);
                    } else {
                        d.setActive(false);
                    }

                    if ("Percent".equals(type)) {
                        if (row.getCell(9) != null && row.getCell(9).getCellType() != CellType.BLANK) {
                            double maxValue = row.getCell(9).getNumericCellValue();
                            d.setMaxValue(maxValue);
                            if (maxValue <= 0) {
                                d.setMaxValueErr("Giảm tối đa phải >0.");
                                hasError = true;
                            }
                        }
                    }

                    d.setUsageCount(0);
                    if (hasError) {
                        failCount++;
                        failedDiscounts.add(d);
                    } else {
                        dao.addDiscount(d);
                        successCount++;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    failCount++;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errMess", "Lỗi đọc file: " + e.getMessage());
            request.getRequestDispatcher("import_discount.jsp").forward(request, response);
            return;
        }
        if (!failedDiscounts.isEmpty()) {
            request.getSession().setAttribute("failedDiscounts", failedDiscounts);
            request.getSession().setAttribute("successMess", "Import thành công " + successCount + " mã, thất bại " + failCount + " mã.");
            response.sendRedirect("retryadddiscount");
            return;
        }

        request.setAttribute("successMess", "Import thành công " + successCount + " mã, thất bại " + failCount + " mã.");
        request.getRequestDispatcher("import_discount.jsp").forward(request, response);
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
