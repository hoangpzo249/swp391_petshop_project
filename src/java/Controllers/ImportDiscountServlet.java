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

        List<Discount> importedList = new ArrayList<>();
        DiscountDAO dao = new DiscountDAO();
        int successCount = 0, failCount = 0;

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = 0;
            for (Row row : sheet) {
                if (rowNum == 0) {
                    rowNum++;
                    continue;
                }
                try {
                    Discount d = new Discount();

                    d.setDiscountCode(row.getCell(0).getStringCellValue().trim());
                    d.setDiscountType(row.getCell(1).getStringCellValue().trim());
                    d.setDiscountValue(row.getCell(2).getNumericCellValue());

                    if (row.getCell(3) != null && row.getCell(3).getCellType() != CellType.BLANK) {
                        d.setDescription(row.getCell(3).getStringCellValue().trim());
                    } else {
                        d.setDescription(null);
                    }

                    Date fromDate;
                    Cell fromCell = row.getCell(4);
                    if (fromCell.getCellType() == CellType.NUMERIC) {
                        java.util.Date utilDate = fromCell.getDateCellValue();
                        fromDate = new Date(utilDate.getTime());
                    } else {
                        fromDate = Date.valueOf(fromCell.getStringCellValue().trim());
                    }
                    d.setValidFrom(fromDate);

                    Date toDate;
                    Cell toCell = row.getCell(5);
                    if (toCell.getCellType() == CellType.NUMERIC) {
                        java.util.Date utilDate = toCell.getDateCellValue();
                        toDate = new Date(utilDate.getTime());
                    } else {
                        toDate = Date.valueOf(toCell.getStringCellValue().trim());
                    }
                    d.setValidTo(toDate);

                    d.setMinOrderAmount(row.getCell(6).getNumericCellValue());

                    if (row.getCell(7) != null && row.getCell(7).getCellType() != CellType.BLANK) {
                        d.setMaxUsage((int) row.getCell(7).getNumericCellValue());
                    } else {
                        d.setMaxUsage(null);
                    }

                    d.setUsageCount(0);

                    if (row.getCell(8) != null && row.getCell(8).getCellType() != CellType.BLANK) {
                        Cell activeCell = row.getCell(8);
                        boolean activeValue;
                        if (activeCell.getCellType() == CellType.BOOLEAN) {
                            activeValue = activeCell.getBooleanCellValue();
                        } else {
                            String activeStr = activeCell.getStringCellValue().trim();
                            activeValue = activeStr.equalsIgnoreCase("true") || activeStr.equalsIgnoreCase("1");
                        }
                        d.setActive(activeValue);
                    } else {
                        d.setActive(false);
                    }

                    if (row.getCell(9) != null && row.getCell(9).getCellType() != CellType.BLANK) {
                        d.setMaxValue(row.getCell(9).getNumericCellValue());
                    } else {
                        d.setMaxValue(null);
                    }

                    boolean added = dao.addDiscount(d);
                    if (added) {
                        successCount++;
                    } else {
                        failCount++;
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
