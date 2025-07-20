/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
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
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class SellerDisplayAllPetServlet extends HttpServlet {

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
            out.println("<title>Servlet DisplayAllPetServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayAllPetServlet at " + request.getContextPath() + "</h1>");
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
        if (account == null || !account.getAccRole().equals("Seller")) {
            session.setAttribute("errMess", "Bạn không có quyền vào trang này.");
            response.sendRedirect("homepage");
            return;
        }

        final int PAGE_SIZE = 8;
        final int MAX_PAGE_NUMBERS_TO_SHOW = 5;

        String searchKey = request.getParameter("searchKey");
        String availability = request.getParameter("availability");
        String species = request.getParameter("species");
        String breedId = request.getParameter("breedId");
        String gender = request.getParameter("gender");
        String vaccination = request.getParameter("vaccination");
        String petStatus = request.getParameter("petStatus");
        String breedStatus = request.getParameter("breedStatus");
        String pageStr = request.getParameter("page");

        int currentPage = 1;
        if (pageStr != null && pageStr.matches("\\d+")) {
            currentPage = Integer.parseInt(pageStr);
        }

        PetDAO petDAO = new PetDAO();
        int totalRecords = petDAO.countFilteredPetsForSeller(searchKey, availability, species, breedId, gender, vaccination, petStatus, breedStatus);

        Pagination pagination = new Pagination(totalRecords, currentPage, PAGE_SIZE, MAX_PAGE_NUMBERS_TO_SHOW);

        if (currentPage > pagination.getTotalPages() && pagination.getTotalPages() > 0) {
            currentPage = pagination.getTotalPages();
            pagination = new Pagination(totalRecords, currentPage, PAGE_SIZE, MAX_PAGE_NUMBERS_TO_SHOW);
        }

        List<Pet> petList = petDAO.filterPetsForSeller(
                searchKey, availability, species, breedId, gender, vaccination, petStatus, breedStatus,
                pagination.getCurrentPage(),
                pagination.getPageSize()
        );

        BreedDAO breedDAO = new BreedDAO();
        List<String> speciesList = breedDAO.getAllSpecies();
        List<Breed> breedList = breedDAO.getAllBreedsSeller();

        request.setAttribute("petList", petList);
        request.setAttribute("speciesList", speciesList);
        request.setAttribute("breedList", breedList);
        request.setAttribute("pagination", pagination);

        request.getRequestDispatcher("seller_pet_view.jsp").forward(request, response);
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
