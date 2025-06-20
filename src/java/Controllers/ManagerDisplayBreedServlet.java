/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import Models.Breed;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class ManagerDisplayBreedServlet extends HttpServlet {

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
            out.println("<title>Servlet ManagerDisplayBreedServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerDisplayBreedServlet at " + request.getContextPath() + "</h1>");
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
        BreedDAO _dao = new BreedDAO();
        final int PAGE_SIZE = 8;
        final int MAX_PAGE_NUMBERS_TO_SHOW = 5;

        String searchKey = request.getParameter("searchKey");
        String species = request.getParameter("species");
        String status = request.getParameter("status");
        String pageStr = request.getParameter("page");

        int currentPage = 1;
        int startPage;
        int endPage;
        if (pageStr != null && pageStr.matches("\\d+")) {
            currentPage = Integer.parseInt(pageStr);
        }

        int totalRecords = _dao.countFilteredBreeds(searchKey, species, status);

        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

        if (totalPages <= MAX_PAGE_NUMBERS_TO_SHOW) {
            startPage = 1;
            endPage = totalPages;
        } else {
            int maxPagesBeforeCurrent = (int) Math.floor(MAX_PAGE_NUMBERS_TO_SHOW / 2.0);
            int maxPagesAfterCurrent = (int) Math.ceil(MAX_PAGE_NUMBERS_TO_SHOW / 2.0) - 1;

            if (currentPage <= maxPagesBeforeCurrent) {
                startPage = 1;
                endPage = MAX_PAGE_NUMBERS_TO_SHOW;
            } else if (currentPage + maxPagesAfterCurrent >= totalPages) {
                startPage = totalPages - MAX_PAGE_NUMBERS_TO_SHOW + 1;
                endPage = totalPages;
            } else {
                startPage = currentPage - maxPagesBeforeCurrent;
                endPage = currentPage + maxPagesAfterCurrent;
            }
        }

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        List<Breed> breedList = _dao.filterBreedsForManager(searchKey, species, status, currentPage, PAGE_SIZE);

        List<String> speciesList = _dao.getAllSpecies();

        request.setAttribute("breedList", breedList);
        request.setAttribute("speciesList", speciesList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);

        request.getRequestDispatcher("manager_breed_view.jsp")
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
