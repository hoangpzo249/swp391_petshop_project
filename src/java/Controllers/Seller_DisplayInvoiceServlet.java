/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.InvoiceDAO;
import DAO.OrderDAO;
import Models.Account;
import Models.Invoice;
import Models.Pagination;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class Seller_DisplayInvoiceServlet extends HttpServlet {

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
            out.println("<title>Servlet Seller_DisplayInvoiceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Seller_DisplayInvoiceServlet at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !"Seller".equals(account.getAccRole())) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        final int PAGE_SIZE = 8;
        final int MAX_PAGE_NUMBERS_TO_SHOW = 5;

        String searchKey = request.getParameter("searchKey");
        String paymentMethod = request.getParameter("paymentMethod");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String pageStr = request.getParameter("page");

        int currentPage = 1;
        if (pageStr != null && pageStr.matches("\\d+")) {
            currentPage = Integer.parseInt(pageStr);
        }

        java.sql.Date startDate = null, endDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = new java.sql.Date(sdf.parse(startDateStr).getTime());
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = new java.sql.Date(sdf.parse(endDateStr).getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            session.setAttribute("errMess", "Định dạng ngày không hợp lệ.");
            response.sendRedirect("sellerdisplayinvoice");
            return;
        }

        InvoiceDAO _invoicedao = new InvoiceDAO();

        int totalRecords = _invoicedao.countFilteredInvoices(searchKey, paymentMethod, startDate, endDate);

        Pagination pagination = new Pagination(totalRecords, currentPage, PAGE_SIZE, MAX_PAGE_NUMBERS_TO_SHOW);

        if (currentPage > pagination.getTotalPages() && pagination.getTotalPages() > 0) {
            currentPage = pagination.getTotalPages();
            pagination = new Pagination(totalRecords, currentPage, PAGE_SIZE, MAX_PAGE_NUMBERS_TO_SHOW);
        }

        List<Invoice> invoiceList = _invoicedao.filterInvoices(searchKey, paymentMethod, startDate, endDate, pagination.getCurrentPage(), pagination.getPageSize());

        request.setAttribute("invoiceList", invoiceList);
        request.setAttribute("pagination", pagination);

        request.getRequestDispatcher("seller_invoice_view.jsp").forward(request, response);
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
