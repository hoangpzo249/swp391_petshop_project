/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.OrderDAO;
import Models.Account;
import Models.Order;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class SellerDisplayOrderServlet extends HttpServlet {

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
            out.println("<title>Servlet DisplayOrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayOrderServlet at " + request.getContextPath() + "</h1>");
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
        String status = request.getParameter("status");
        String sort = request.getParameter("sort");
        String pageStr = request.getParameter("page");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        int currentPage = (pageStr != null && pageStr.matches("\\d+")) ? Integer.parseInt(pageStr) : 1;
        sort = (sort == null || sort.trim().isEmpty()) ? "DESC" : sort;

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
            response.sendRedirect("displayorder");
            return;
        }

        OrderDAO dao = new OrderDAO();
        int totalRecords = dao.countFilteredOrders(searchKey, status, startDate, endDate);
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

        int startPage = 1, endPage = totalPages;
        if (totalPages > MAX_PAGE_NUMBERS_TO_SHOW) {
            int before = MAX_PAGE_NUMBERS_TO_SHOW / 2;
            int after = MAX_PAGE_NUMBERS_TO_SHOW - before - 1;

            if (currentPage <= before) {
                endPage = MAX_PAGE_NUMBERS_TO_SHOW;
            } else if (currentPage + after >= totalPages) {
                startPage = totalPages - MAX_PAGE_NUMBERS_TO_SHOW + 1;
            } else {
                startPage = currentPage - before;
                endPage = currentPage + after;
            }
        }

        List<Order> orderList = dao.filterOrderForSeller(searchKey, status, startDate, endDate, sort, currentPage, PAGE_SIZE);

        request.setAttribute("orderList", orderList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("sort", sort);

        request.getRequestDispatcher("seller_order_view.jsp").forward(request, response);
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
//        request.setCharacterEncoding("UTF-8");
//
//        String searchKey = request.getParameter("searchKey");
//        String status = request.getParameter("status");
//
//        String startDateStr = request.getParameter("startDate");
//        String endDateStr = request.getParameter("endDate");
//
//        Date startDate = null;
//        Date endDate = null;
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            if (startDateStr != null && !startDateStr.isEmpty()) {
//                java.util.Date utilStartDate = sdf.parse(startDateStr);
//                startDate = new Date(utilStartDate.getTime());
//            }
//
//            if (endDateStr != null && !endDateStr.isEmpty()) {
//                java.util.Date utilEndDate = sdf.parse(endDateStr);
//                endDate = new Date(utilEndDate.getTime());
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            HttpSession session = request.getSession(false);
//            String referer = request.getHeader("referer");
//            session.setAttribute("errMess", "Định dạng ngày không hợp lệ.");
//            if (referer != null) {
//                response.sendRedirect(referer);
//            } else {
//                response.sendRedirect("displayorder");
//            }
//            return;
//        }
//
//        OrderDAO _dao = new OrderDAO();
//        List<Order> list = _dao.filterOrders(searchKey, status, startDate, endDate);
//
//        request.setAttribute("orderList", list);
//        request.getRequestDispatcher("seller_order_view.jsp")
//                .forward(request, response);
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
