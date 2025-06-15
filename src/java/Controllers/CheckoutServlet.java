/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.CartDAO;
import DAO.PetDAO;
import Models.Account;
import Models.Cart;
import Models.Pet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author AD
 */
public class CheckoutServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckoutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckoutServlet at " + request.getContextPath() + "</h1>");
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
        String[] selectedPets = request.getParameterValues("selectedPets");

        PetDAO petDao = new PetDAO();
        List<Pet> selectedPetList = new ArrayList<>();
        double total = 0;

        for (String id : selectedPets) {
            try {
                int petId = Integer.parseInt(id);
                Pet pet = petDao.getPetById(petId);
                if (pet != null && pet.getPetAvailability() == 1&&pet.getPetStatus()==1) {
                    selectedPetList.add(pet);
                    total += pet.getPetPrice();
                }
            } catch (Exception e) {

            }
        }
        if (selectedPets != null && selectedPets.length > selectedPetList.size()) {
            request.setAttribute("warningMessage", "Một số sản phẩm bạn chọn có thể không còn tồn tại hoặc đã bị xoá khỏi cửa hàng.");
        }
        

        if (selectedPetList.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("cartMessage", "Sản phẩm bạn chọn đã không còn khả dụng. Vui lòng kiểm tra lại giỏ hàng.");
            response.sendRedirect("displaycart");
            return;
        }

        request.setAttribute("selectedPets", selectedPetList);
        request.setAttribute("total", total);
        request.setAttribute("finalTotal", total);
        

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("userAccount");
         CartDAO cartDao = new CartDAO();
        List<Cart> petCart = new ArrayList<>();
       

        if (account != null) {
             petCart = cartDao.getCart(account.getAccId());
            request.setAttribute("name", account.getAccFname() + " " + account.getAccLname());
            request.setAttribute("phone", account.getAccPhoneNumber());
            request.setAttribute("address", account.getAccAddress());
            request.setAttribute("email", account.getAccEmail());
        }
        else {
            List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
            if (guestCart != null) {
                petCart = guestCart;
            }
        }
        request.setAttribute("cartcount", petCart.size());
        
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
