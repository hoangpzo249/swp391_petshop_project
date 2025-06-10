/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import com.sun.jdi.connect.spi.Connection;
import dao.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;

/**
 *
 * @author QuangAnh
 */
public class OrderDAO extends DBContext{
    public List<Order> getOrderAccId(int accId){
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderTB WHERE accID = ?";
        try (PreparedStatement stm = c.prepareStatement(sql)){
            stm.setInt(1, accId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setAccId(rs.getInt("accId"));
                o.setOrderDate(rs.getDate("orderDate"));
                o.setOrderStatus(rs.getString("orderStatus"));
                o.setCustomerName(rs.getString("customerName"));
                o.setCustomerEmail(rs.getString("customerEmail"));
                o.setCustomerPhone(rs.getString("customerPhone"));
                o.setCustomerAddress(rs.getString("customerAddress"));
                o.setShipperId(rs.getInt("shipperId"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setPaymentStatus(rs.getString("paymentStatus"));
                list.add(o);
                        
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean requestCancellationForPaidOrder(int orderId) {
        String sql = "UPDATE OrderTB SET cancellationRequested = 1 WHERE orderId = ? AND paymentStatus = 'Paid'";
        try (PreparedStatement stm = c.prepareStatement(sql)) {
            stm.setInt(1, orderId);
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean cancelPendingOrderByCustomer(int orderId, int accId) {
        String checkSql = "SELECT orderStatus FROM OrderTB WHERE orderId = ? AND accId = ?";
        try (PreparedStatement stm = c.prepareStatement(checkSql)){
            stm.setInt(1, orderId);
            stm.setInt(2, accId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()){
                String status = rs.getString("orderStatus");
                if("Pending".equalsIgnoreCase(status)){
                    String updateSql = "UPDATE OrderTB SET orderStatus = 'Cancelled' WHERE orderId = ?";
                    try (PreparedStatement upStm = c.prepareStatement(updateSql)){
                        upStm.setInt(1, orderId);
                        upStm.executeUpdate();
                        return true;
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args){
        OrderDAO  o = new OrderDAO();
        var list = o.getOrderAccId(0);
        for (Order order : list){
            System.out.println(order.toString());
        }
    }
    

}
