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

public class DisplayCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayCart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayCart at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CartDAO cartDao = new CartDAO();
        PetDAO petDao = new PetDAO();
        HttpSession session = request.getSession();

        List<Cart> petCart = new ArrayList<>();
        List<Cart> updatedPetCart = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();
        double totalPrice = 0;

        Account account = (Account) session.getAttribute("userAccount");

        if (account != null) {
            petCart = cartDao.getCart(account.getAccId());
        } else {
            List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");

            if (guestCart != null) {
                petCart = guestCart;
            }
        }

        for (Cart c : petCart) {
            if (c.getPetId() != null) {
                Pet pet = petDao.getPetById(c.getPetId());
                if (pet != null && pet.getPetAvailability() == 1) {
                    pets.add(pet);
                    updatedPetCart.add(c);

                } else {
                    if (account != null) {
                        cartDao.deleteFromPetCart(account.getAccId(), c.getPetId());
                    }

                }
            }
        }

        if (account != null) {
            session.setAttribute("cartcount", cartDao.getTotalCartItems(account.getAccId()));
        } else {
            session.setAttribute("cartcount", updatedPetCart.size());

            session.setAttribute("guestCart", updatedPetCart);

        }
        if (petCart.size() > updatedPetCart.size()) {
            session.setAttribute("cartMessage", "Một số sản phẩm bạn đã chọn hiện không còn khả dụng nên không hiển thị trong giỏ hàng.");
        }

        request.setAttribute("pets", pets);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
