/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.BreedDAO;
import DAO.CartDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Breed;
import Models.Cart;
import Models.Pet;
import Utils.EmailSender;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HuyHoang
 */
public class Homepage_Servlet extends HttpServlet {

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
            out.println("<title>Servlet Homepage_Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Homepage_Servlet at " + request.getContextPath() + "</h1>");
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

        BreedDAO breedDao = new BreedDAO();
        List<Breed> listBreed = breedDao.get6BreedHot();
        request.setAttribute("listBreed", listBreed);

        PetDAO petDao = new PetDAO();
        List<Pet> listPet = petDao.get6PetNew();
        request.setAttribute("listPet", listPet);

        HttpSession session = request.getSession();
        CartDAO _daoCart = new CartDAO();

        if (session.getAttribute("userAccount") != null) {
            int accountId = ((Account) session.getAttribute("userAccount")).getAccId();
            List<Cart> carts = _daoCart.getCart(accountId);
            List<Cart> filtered = new ArrayList<>();

            for (Cart c : carts) {
                if (c.getPetId() != null) {
                    Pet pet = petDao.getPetById(c.getPetId());
                    if (pet != null && pet.getPetAvailability() == 1 && pet.getPetStatus() == 1) {
                        filtered.add(c);
                    }
                }
            }

            session.setAttribute("cartcount", filtered.size());
        } else {
            List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
            int guestCount = 0;
            if (guestCart != null) {

                for (Cart c : guestCart) {
                    if (c.getPetId() != null) {
                        Pet pet = petDao.getPetById(c.getPetId());
                        if (pet != null && pet.getPetAvailability() == 1 && pet.getPetStatus() == 1) {
                            guestCount++;
                        }
                    }
                }
            }
            session.setAttribute("cartcount", guestCount);

        }

        request.getRequestDispatcher("home_page.jsp").forward(request, response);
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
        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            request.getRequestDispatcher("home_page.jsp").forward(request, response);
        }

        try {
            EmailSender.registerInfo(email);
            request.setAttribute("loginSuccess", "Đăng kí nhận thông tin thành công!");
        } catch (Exception e) {
        }
        doGet(request, response);
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
