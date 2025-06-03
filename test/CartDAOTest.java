import DAO.CartDAO;
import Models.Cart;

import java.util.List;

public class CartDAOTest {

    public static void main(String[] args) {
        CartDAO dao = new CartDAO();

        
        int accId = 1;
        int petId = 2;
        int quantity = 1;
        double unitPrice = 500000.0;

       
        System.out.println("=== TEST: addToPetCart ===");
        dao.addToPetCart(accId, petId);
        System.out.println("=> addToPetCart done.");

       
        System.out.println("=== TEST: petInCart ===");
        boolean exists = dao.petInCart(accId, petId);
        System.out.println("=> petInCart: " + exists);

       
       
        
        System.out.println("=== TEST: getTotalCartItems ===");
        int totalItems = dao.getTotalCartItems(accId);
        System.out.println("=> Total items in cart: " + totalItems);

       
        System.out.println("=== TEST: getCart ===");
        List<Cart> cartList = dao.getCart(accId);
        for (Cart c : cartList) {
            System.out.println(c);
        }

        
        System.out.println("=== TEST: deleteFromPetCart ===");
        dao.deleteFromPetCart(accId, petId);
        System.out.println("=> deleteFromPetCart done.");
    }
}
