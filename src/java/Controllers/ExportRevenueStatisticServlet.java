/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import Models.Account;
import Models.Revenue;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
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
public class ExportRevenueStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet ExportRevenueStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExportRevenueStatisticServlet at " + request.getContextPath() + "</h1>");
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
        if (account == null || !account.getAccRole().equals("Manager")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }
        OrderDAO orderDAO = new OrderDAO();
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        long diffInDays = 0;

        Date startDate = null;
        Date endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = Date.valueOf(startDateStr);
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = Date.valueOf(endDateStr);
            }
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ngày không hợp lệ. Vui lòng dùng YYYY-MM-DD.");
            return;
        }

        if (startDate != null && endDate == null) {
            endDate = new Date(new java.util.Date().getTime());
        }

        if (startDate != null && endDate != null) {
            if (startDate.after(endDate)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày bắt đầu không thể ở sau ngày kết thúc.");
                return;
            }
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } else {
            diffInDays = 181;
        }

        List<Revenue> revenueData;
        String timeUnitLabel;
        String timeUnitFormat;

        if (diffInDays > 180) {
            revenueData = orderDAO.getRevenueByMonth(startDate, endDate);
            timeUnitLabel = "Tháng";
            timeUnitFormat = "MM/yyyy";
        } else if (diffInDays > 60) {
            revenueData = orderDAO.getRevenueByWeek(startDate, endDate);
            timeUnitLabel = "Tuần (Ngày bắt đầu)";
            timeUnitFormat = "dd/MM/yyyy";
        } else {
            revenueData = orderDAO.getRevenueByDay(startDate, endDate);
            timeUnitLabel = "Ngày";
            timeUnitFormat = "dd/MM/yyyy";
        }

        if (revenueData == null) {
            revenueData = new ArrayList<>();
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {

            XSSFSheet sheet = workbook.createSheet("BaoCaoDoanhThu");

            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle infoStyle = createInfoStyle(workbook);
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle currencyStyle = createCurrencyStyle(workbook);

            createReportHeader(sheet, titleStyle, infoStyle, startDate, endDate);
            createTableHeader(sheet, headerStyle, timeUnitLabel);
            fillReportData(sheet, dataStyle, currencyStyle, revenueData, timeUnitFormat);

            sheet.setColumnWidth(0, 7000);
            sheet.setColumnWidth(1, 8000);

            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String fileName = "BaoCaoDoanhThu_" + currentDate + ".xlsx";

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            workbook.write(out);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi tạo file Excel báo cáo doanh thu", e);
        }
    }

    private void createReportHeader(XSSFSheet sheet, CellStyle titleStyle, CellStyle infoStyle, Date startDate, Date endDate) {
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO DOANH THU THEO THỜI GIAN");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

        Row shopRow = sheet.createRow(1);
        shopRow.setHeightInPoints(25);
        Cell shopCell = shopRow.createCell(0);
        shopCell.setCellValue("Cửa hàng: " + SHOP_NAME);
        shopCell.setCellStyle(infoStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

        Row dateRow = sheet.createRow(2);
        dateRow.setHeightInPoints(25);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Thời gian thống kê: " + formatDateRange(startDate, endDate));
        dateCell.setCellStyle(infoStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
    }

    private void createTableHeader(XSSFSheet sheet, CellStyle headerStyle, String timeUnitLabel) {
        Row headerRow = sheet.createRow(4);
        headerRow.setHeightInPoints(40);

        String[] columns = {timeUnitLabel, "Tổng Doanh Thu"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void fillReportData(XSSFSheet sheet, CellStyle dataStyle, CellStyle currencyStyle, List<Revenue> revenueList, String dateFormat) {
        int rowNum = 5;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        for (Revenue revenue : revenueList) {
            Row row = sheet.createRow(rowNum++);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(sdf.format(revenue.getDate()));
            dateCell.setCellStyle(dataStyle);

            Cell revenueCell = row.createCell(1);
            if (revenue.getTotalRevenue() != null) {
                revenueCell.setCellValue(revenue.getTotalRevenue().doubleValue());
            }
            revenueCell.setCellStyle(currencyStyle);
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
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.DARK_TEAL.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    private CellStyle createInfoStyle(XSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
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
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createCurrencyStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("#,##0\" ₫\""));
        style.setVerticalAlignment(VerticalAlignment.CENTER);
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
