/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import DAO.OrderDAO;
import Models.Breed;
import Models.Order;
import Models.Revenue;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Lenovo
 */
public class ManagerDisplayRevenueStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerDisplayRevenueStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerDisplayRevenueStatisticServlet at " + request.getContextPath() + "</h1>");
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
        OrderDAO _orderdao = new OrderDAO();
        BreedDAO _breeddao = new BreedDAO();
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        long diffInDays = 0;

        HttpSession session = request.getSession();
        String referer = request.getHeader("referer");
        if (referer == null) {
            referer = "displayrevenuestatistic";
        }

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
            session.setAttribute("errMess", "Đã có lỗi xảy ra khi xử lý dử liệu ngày.");
            response.sendRedirect(referer);
        }
        if (startDate != null && endDate != null) {
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } else {
            diffInDays = 30;
        }

        int totalOrders = _orderdao.totalOrdersFulfilled(startDate, endDate);
        BigDecimal totalRevenue = _orderdao.getTotalRevenue(startDate, endDate);
        BigDecimal averageOrderValue = _orderdao.getAverageOrderValue(startDate, endDate);
        List<Breed> topBreedsByRevenue = _breeddao.getTopPricedBreeds(startDate, endDate, 5);
        List<Order> topHighValueOrders = _orderdao.getTopPricedOrders(startDate, endDate, 10);
        List<Revenue> revenueByDay = new ArrayList<>();
        String chartTimeUnit;
        if (diffInDays > 180) {
            revenueByDay = _orderdao.getRevenueByMonth(startDate, endDate);
            chartTimeUnit = "month";
        } else if (diffInDays > 60) {
            revenueByDay = _orderdao.getRevenueByWeek(startDate, endDate);
            chartTimeUnit = "week";

        } else {
            revenueByDay = _orderdao.getRevenueByDay(startDate, endDate);
            chartTimeUnit = "day";

        }

        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("averageOrderValue", averageOrderValue);
        request.setAttribute("topBreedsByRevenue", topBreedsByRevenue);
        request.setAttribute("topHighValueOrders", topHighValueOrders);
        request.setAttribute("revenueByDay", revenueByDay);
        request.setAttribute("chartTimeUnit", chartTimeUnit);

        request.getRequestDispatcher("manager_revenue_statistic.jsp")
                .forward(request, response);
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
