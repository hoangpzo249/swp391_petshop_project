/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import DAO.OrderDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Breed;
import Models.Pagination;
import Models.Pet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class SellerDisplaySalesStatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet SellerDisplaySalesStatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SellerDisplaySalesStatisticServlet at " + request.getContextPath() + "</h1>");
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
        final int PAGE_SIZE = 10;
        final int MAX_PAGE_NUMBERS_TO_SHOW = 5;
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        PetDAO _petdao = new PetDAO();
        BreedDAO _breeddao = new BreedDAO();
        OrderDAO _orderdao = new OrderDAO();
        String referer = request.getHeader("referer");
        referer = referer == null ? "homepage" : referer;
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String pageStr = request.getParameter("page");

        int currentPage = 1;
        if (pageStr != null && pageStr.matches("\\d+")) {
            currentPage = Integer.parseInt(pageStr);
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

        int totalPet = _petdao.totalPetsSold(startDate, endDate);
        int totalOrder = _orderdao.totalOrdersFulfilled(startDate, endDate);
        Breed topBreed = _breeddao.mostPopularBreed(startDate, endDate);
        String breedName = topBreed == null ? "Không có" : topBreed.getBreedName();
        List<Breed> salesByBreed = _breeddao.getBreedsSortedBySales(startDate, endDate);

        int totalRecords = _petdao.countSoldPets(startDate, endDate);
        Pagination pagination = new Pagination(totalRecords, currentPage, PAGE_SIZE, MAX_PAGE_NUMBERS_TO_SHOW);

        if (currentPage > pagination.getTotalPages() && pagination.getTotalPages() > 0) {
            currentPage = pagination.getTotalPages();
            pagination = new Pagination(totalRecords, currentPage, PAGE_SIZE, MAX_PAGE_NUMBERS_TO_SHOW);
        }

        List<Pet> petList = _petdao.getSoldPets(startDate, endDate, pagination.getCurrentPage(), pagination.getPageSize());
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("totalPetsSold", totalPet);
        request.setAttribute("ordersFulfilled", totalOrder);
        request.setAttribute("mostPopularBreed", breedName);
        request.setAttribute("salesByBreed", salesByBreed);
        request.setAttribute("recentSales", petList);
        request.setAttribute("pagination", pagination);

        request.getRequestDispatcher("seller_sales_statistic.jsp")
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
