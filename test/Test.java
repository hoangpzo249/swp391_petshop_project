
import DAO.AccountDAO;
import DAO.OrderDAO;
import Models.Account;
import Models.Order;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Lenovo
 */
public class Test {

    private static AccountDAO _dao=new AccountDAO();
    private static OrderDAO _orddao=new OrderDAO();

//    public static void main(String[] args) {
//
//        // Create Admin account
//        Account admin = new Account(
//                "adminUser",
//                "admin@example.com",
//                "secureAdminPass123",
//                "Admin",
//                "User",
//                LocalDate.of(1990, 1, 1),
//                "Admin Address",
//                "0123456789",
//                "Admin",
//                "Superuser account",
//                null, // no image for now
//                "Active"
//        );
//
//        // Create Customer account
//        Account customer = new Account(
//                "customer1",
//                "customer1@example.com",
//                "custPass456",
//                "Customer",
//                "One",
//                LocalDate.of(1995, 5, 15),
//                "Customer Address",
//                "0987654321",
//                "Customer",
//                "Regular customer",
//                null,
//                "Active"
//        );
//
//        // Create Saler account
//        Account saler = new Account(
//                "salerUser",
//                "saler@example.com",
//                "salerPass789",
//                "Saler",
//                "User",
//                LocalDate.of(1988, 7, 20),
//                "Saler Address",
//                "0112233445",
//                "Saler",
//                "Sales representative",
//                null,
//                "Active"
//        );
//
//        // Create Manager account
//        Account manager = new Account(
//                "managerUser",
//                "manager@example.com",
//                "managerPass321",
//                "Manager",
//                "User",
//                LocalDate.of(1985, 3, 10),
//                "Manager Address",
//                "0223344556",
//                "Manager",
//                "Store manager",
//                null,
//                "Active"
//        );
//
//        // Create Shipper account
//        Account shipper = new Account(
//                "shipperUser",
//                "shipper@example.com",
//                "shipperPass654",
//                "Shipper",
//                "User",
//                LocalDate.of(1992, 11, 25),
//                "Shipper Address",
//                "0334455667",
//                "Shipper",
//                "Delivery personnel",
//                null,
//                "Active"
//        );
//
//        // Insert accounts into database
//        createAccount(admin);
//        createAccount(customer);
//        createAccount(saler);
//        createAccount(manager);
//        createAccount(shipper);
//    }
    
    public static void main(String[] args) {
        ArrayList<Integer> list=_orddao.getOrderContentById("1");
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    public static void createAccount(Account account) {
        _dao.createAccount(account);
    }
    
    public static void getOrders() {
        List<Order> list=_orddao.getOrders();
        for (Order order : list) {
            System.out.println(order.toString());
        }
    }
}
