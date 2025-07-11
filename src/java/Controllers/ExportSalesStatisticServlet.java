/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import Models.Account;
import Models.Breed;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Lenovo
 */
public class ExportSalesStatisticServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String SHOP_NAME = "PetFPT Shop";

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
            out.println("<title>Servlet ExportSalesStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportSalesStatisticServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String referer = request.getHeader("referer");
        if (referer == null) {
            referer = "displayrevenuestatistic";
        }

        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                java.util.Date utilDate = sdf.parse(startDateStr);
                startDate = new Date(utilDate.getTime());
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                java.util.Date utilDate = sdf.parse(endDateStr);
                endDate = new Date(utilDate.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ngày không hợp lệ. Vui lòng sử dụng yyyy-MM-dd.");
            return;
        }

        if (startDate != null && endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

        BreedDAO breedDAO = new BreedDAO();
        List<Breed> breedList = breedDAO.getBreedsSortedBySales(startDate, endDate);

        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {

            XSSFSheet sheet = workbook.createSheet("DoanhSoTheoGiong");

            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle infoStyle = createInfoStyle(workbook);

            createReportHeader(sheet, titleStyle, infoStyle, startDate, endDate);
            createTableHeader(sheet, headerStyle);
            fillReportData(sheet, dataStyle, breedList);

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String fileName = "BaoCaoDoanhSoTheoGiong_" + currentDate + ".xlsx";

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            workbook.write(out);
            request.getRequestDispatcher(referer)
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi tạo file Excel", e);
        }
    }

    private void createReportHeader(XSSFSheet sheet, CellStyle titleStyle, CellStyle infoStyle, Date startDate, Date endDate) {
        titleStyle.setWrapText(true);
        infoStyle.setWrapText(true);

        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(50);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO THỐNG KÊ DOANH SỐ CHI TIẾT THEO TỪNG GIỐNG THÚ CƯNG");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

        Row shopRow = sheet.createRow(1);
        shopRow.setHeightInPoints(30);
        Cell shopCell = shopRow.createCell(0);
        shopCell.setCellValue("Cửa hàng: " + SHOP_NAME);
        shopCell.setCellStyle(infoStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

        Row dateRow = sheet.createRow(2);
        dateRow.setHeightInPoints(30);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Khoảng thời gian thống kê: " + formatDateRange(startDate, endDate));
        dateCell.setCellStyle(infoStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));
    }

    private void createTableHeader(XSSFSheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(4);
        headerRow.setHeightInPoints(40);

        String[] columns = {"STT", "Mã Số Giống", "Tên Giống Thú Cưng", "Tổng Số Lượng Đã Bán"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void fillReportData(XSSFSheet sheet, CellStyle dataStyle, List<Breed> breedList) {
        int rowNum = 5;
        int stt = 1;
        for (Breed breed : breedList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(breed.getBreedId());
            row.createCell(2).setCellValue(breed.getBreedName());
            row.createCell(3).setCellValue(breed.getTotalPurchases());

            for (int i = 0; i < 4; i++) {
                if (row.getCell(i) != null) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }
        }
    }

    private String formatDateRange(Date startDate, Date endDate) {
        SimpleDateFormat displaySdf = new SimpleDateFormat("dd/MM/yyyy");
        if (startDate == null && endDate == null) {
            return "Tất cả thời gian";
        }
        String start = (startDate != null) ? displaySdf.format(startDate) : "đầu tiên";
        String end = (endDate != null) ? displaySdf.format(endDate) : "hôm nay";
        return "Từ ngày " + start + " đến ngày " + end;
    }

    private CellStyle createTitleStyle(XSSFWorkbook workbook) {
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setColor(IndexedColors.DARK_BLUE.getIndex());

        CellStyle style = workbook.createCellStyle();
        style.setFont(titleFont);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createInfoStyle(XSSFWorkbook workbook) {
        Font infoFont = workbook.createFont();
        infoFont.setBold(true);
        infoFont.setFontHeightInPoints((short) 11);

        CellStyle style = workbook.createCellStyle();
        style.setFont(infoFont);
        style.setAlignment(HorizontalAlignment.LEFT);
        return style;
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle style = workbook.createCellStyle();
        style.setFont(headerFont);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createDataStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
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
