/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author QuangAnh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Order;

public class OrderDAO extends DBContext {

    public List<Order> getOrdersByAccount(int accId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderTB WHERE accId = ? ORDER BY orderDate DESC";

        try {
            DBContext db = new DBContext();
            try (
                    Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, accId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
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
                    o.setRejectionReason(rs.getString("rejectionReason"));
                    list.add(o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean cancelOrderByOrderId(int orderId) {
        String sql = "UPDATE OrderTB SET orderStatus = 'Cancelled' WHERE orderId = ? AND orderStatus = 'Pending'";
        try {
            DBContext db = new DBContext();
            try (
                    Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean requestCancelOrder(int orderId) {
        String sql = "UPDATE OrderTB SET orderStatus = 'requestCancel' WHERE orderId = ? AND paymentStatus = 'Paid' AND orderStatus = 'Confirmed'";
        try {
            DBContext db = new DBContext();
            try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static void main(String[] args) {
//        OrderDAO o = new OrderDAO();
//        var list = o.getAllOrders();
//        for (Order order : list) {
//            System.out.println(order.toString());
//        }
//    }
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM OrderTB";

        try (
                Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
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
                o.setRejectionReason(rs.getString("rejectionReason"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM OrderTB WHERE orderId = ? AND orderStatus = 'Rejected'";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM OrderTB WHERE orderId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
                o.setRejectionReason(rs.getString("rejectionReason"));
                return o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
