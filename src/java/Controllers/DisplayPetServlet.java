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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayPetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        int petId = Integer.parseInt(id);
        PetDAO dao = new PetDAO();

        Pet pet = dao.getPetById(petId);
        List<String> images = dao.getImagePathsByPetId(petId);
        pet.setImages(images);

        List<Pet> similarPets = dao.getSimilarPets(pet.getBreedId(), petId);

        request.setAttribute("similarPets", similarPets);

        request.setAttribute("pet", pet);
        HttpSession session = request.getSession();
        CartDAO _daoCart = new CartDAO();

        if (session.getAttribute("userAccount") != null) {
            int accountId = ((Account) session.getAttribute("userAccount")).getAccId();
            List<Cart> carts = _daoCart.getCart(accountId);
            List<Cart> filtered = new ArrayList<>();

            for (Cart c : carts) {
                if (c.getPetId() != null) {
                    Pet pet1 = dao.getPetById(c.getPetId());
                    if (pet1 != null && pet.getPetAvailability() == 1 && pet1.getPetStatus() == 1) {
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
                        Pet pet1 = dao.getPetById(c.getPetId());
                        if (pet1 != null && pet1.getPetAvailability() == 1 && pet1.getPetStatus() == 1) {
                            guestCount++;
                        }
                    }
                }
            }
            session.setAttribute("cartcount", guestCount);

        }

        request.getRequestDispatcher("main_productpet.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
