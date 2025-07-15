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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListShopPetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        CartDAO _daoCart = new CartDAO();
        PetDAO petDAO = new PetDAO();
        BreedDAO breedDAO = new BreedDAO();
        if (session.getAttribute("userAccount") != null) {
            int accountId = ((Account) session.getAttribute("userAccount")).getAccId();
            List<Cart> carts = _daoCart.getCart(accountId);
            List<Cart> filtered = new ArrayList<>();

            for (Cart c : carts) {
                if (c.getPetId() != null) {
                    Pet pet = petDAO.getPetById(c.getPetId());
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
                        Pet pet = petDAO.getPetById(c.getPetId());
                        if (pet != null && pet.getPetAvailability() == 1 && pet.getPetStatus() == 1) {
                            guestCount++;
                        }
                    }
                }
            }
            session.setAttribute("cartcount", guestCount);

        }

        List<String> listSpecies = breedDAO.getAllSpecies();
        List<Breed> listBreed = null;
        String species = request.getParameter("species");

        String breed = request.getParameter("breed");

        if ((species == null || species.isEmpty()) && breed != null && !breed.isEmpty()) {
            species = breedDAO.getSpeciesByBreed(breed);
        }
        if (species != null && !species.isEmpty()) {
            listBreed = breedDAO.getBreedsBySpecies(species);
        } else {
            listBreed = breedDAO.getAllBreeds();
        }
        String search = request.getParameter("search");
        search = (search != null) ? search.trim() : "";
        String sort = request.getParameter("sortpet");
        String priceRange = request.getParameter("priceRange");
        String gender = request.getParameter("gender");
        String color = request.getParameter("color");
        String origin = request.getParameter("origin");
        String dobFrom = request.getParameter("dobFrom");
        String dobTo = request.getParameter("dobTo");

        String vaccination = request.getParameter("vaccination");

        int giapet1 = 0, giapet2 = 0;
        if (priceRange != null && !priceRange.isEmpty()) {
            String[] parts = priceRange.split("-");
            if (parts.length == 2) {
                try {
                    giapet1 = Integer.parseInt(parts[0]);
                    giapet2 = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    giapet1 = 0;
                    giapet2 = 0;
                }
            }
        }
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 12;
        
        List<Pet> petList = petDAO.filterPetsPaging(
                breed, species, search, giapet1, giapet2,
                sort, gender, color, origin, dobFrom, dobTo, vaccination,
                page, pageSize
        );

        int totalItems = petDAO.countFilteredPets(
                breed, species, search, giapet1, giapet2,
                gender, color, origin, dobFrom, dobTo, vaccination
        );
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        int displayPageCount = 3;
        int startPage = Math.max(1, page - 1);
        int endPage = Math.min(totalPages, page + 1);

        if (page == 1) {
            startPage = 1;
            endPage = Math.min(totalPages, displayPageCount);
        } else if (page >= totalPages - 1) {
            endPage = totalPages;
            startPage = Math.max(1, totalPages - displayPageCount + 1);
        }

        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);

        request.setAttribute("listPet", petList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("species", species);
        request.setAttribute("breed", breed);

        request.setAttribute("listSpecies", listSpecies);
        request.setAttribute("listBreedBySpecies", listBreed);

        request.setAttribute("listOrigin", petDAO.getAllOrigins());
        request.setAttribute("dobFrom", dobFrom);
        request.setAttribute("dobTo", dobTo);

        request.setAttribute("listColor", petDAO.getAllColors());
        request.setAttribute("listGender", petDAO.getAllGenders());

        request.setAttribute("listVaccine", petDAO.getAllVaccinationStatus());
        request.setAttribute("priceRange", priceRange);

        request.getRequestDispatcher("main_typepet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Pet Shop List Servlet";
    }
}
