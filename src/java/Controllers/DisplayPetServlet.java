/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.PetDAO;
import Models.Pet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class DisplayPetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        try {

            int petId = Integer.parseInt(id);
            PetDAO dao = new PetDAO();

            Pet pet = dao.getPetById(petId);

            List<Pet> similarPets = dao.getSimilarPets(pet.getBreedId(), petId);

            request.setAttribute("similarPets", similarPets);

            request.setAttribute("pet", pet);

            request.getRequestDispatcher("main_productpet.jsp").forward(request, response);

        } catch (Exception e) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}