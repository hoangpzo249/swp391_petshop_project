
import DAO.OrderDAO;
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
public class TestOrder {

    private static OrderDAO _dao = new OrderDAO();

    public static void main(String[] args) {
        List<Order> list=getOrder();
        for (Order order : list) {
            System.out.println(order.toString());
        }
    }

    public static List<Order> getOrder() {
        List<Order> list = new ArrayList<>();
        list = _dao.getOrders();
        return list;
    }
}
