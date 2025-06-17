
import DAO.OrderDAO;
import Models.Order;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class TestOrder {
    private static OrderDAO _dao=new OrderDAO();
    public static void main(String[] args) {
        System.out.println(getOrder("1").toString());
    }
    
    private static Order getOrder(String id) {
        return _dao.getOrderById(id);
    }
}
