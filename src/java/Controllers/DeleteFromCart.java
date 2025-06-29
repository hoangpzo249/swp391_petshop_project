/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAO.CartDAO;
import Models.Account;
import Models.Cart;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeleteFromCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        CartDAO cartDAO = new CartDAO();
        String idParam = request.getParameter("id");

        int petId = Integer.parseInt(idParam);
        Account account = (Account) session.getAttribute("userAccount");

        if (account != null) {
            int accountId = account.getAccId();
            cartDAO.deleteFromPetCart(accountId, petId);
        } else {
            List<Cart> guestCart = (List<Cart>) session.getAttribute("guestCart");
            if (guestCart != null) {
                for (int i = 0; i < guestCart.size(); i++) {
                    Cart cart = guestCart.get(i);
                    if (cart.getPetId() == petId) {
                        guestCart.remove(i);
                        break;
                    }
                }
                session.setAttribute("guestCart", guestCart);
            }
        }
        session.setAttribute("cartMessage", "Xóa thành công khỏi giỏ hàng");
        response.sendRedirect("displaycart");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Xóa thú cưng khỏi giỏ hàng";
    }
}
