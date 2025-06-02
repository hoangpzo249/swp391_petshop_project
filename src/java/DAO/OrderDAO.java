/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class OrderDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public List<Order> getOrders() {
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM OrderTB";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setAccId(rs.getInt("accId"));
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setOrderStatus(rs.getString("orderStatus"));
                order.setCustomerName(rs.getString("customerName"));
                order.setCustomerEmail(rs.getString("customerEmail"));
                order.setCustomerPhone(rs.getString("customerPhone"));
                order.setCustomerAddress(rs.getString("customerAddress"));
                order.setShipperId(rs.getObject("shipperId") != null ? rs.getInt("shipperId") : null);
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));

                list.add(order);
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Order getOrderById(String id) {
        DBContext db = new DBContext();
        Order order = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM OrderTB WHERE orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setAccId(rs.getInt("accId"));
                order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                order.setOrderStatus(rs.getString("orderStatus"));
                order.setCustomerName(rs.getString("customerName"));
                order.setCustomerEmail(rs.getString("customerEmail"));
                order.setCustomerPhone(rs.getString("customerPhone"));
                order.setCustomerAddress(rs.getString("customerAddress"));
                order.setShipperId(rs.getInt("shipperId"));
                order.setPaymentMethod(rs.getString("paymentMethod"));
                order.setPaymentStatus(rs.getString("paymentStatus"));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return order;
    }
    
    public ArrayList<Integer> getOrderContentById(String id) {
        DBContext db=new DBContext();
        ArrayList<Integer> list=new ArrayList<>();
        try {
            conn=db.getConnection();
            String sql="SELECT * FROM OrderContentTB WHERE orderId=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1, id);
            rs=ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("petId"));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
