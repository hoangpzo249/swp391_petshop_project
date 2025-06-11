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

    private Order orderInfo(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getInt("orderId"));
        order.setAccId(rs.getInt("accId"));
        order.setOrderDate(rs.getTimestamp("orderDate"));
        order.setOrderStatus(rs.getString("orderStatus"));
        order.setCustomerName(rs.getString("customerName"));
        order.setCustomerEmail(rs.getString("customerEmail"));
        order.setCustomerPhone(rs.getString("customerPhone"));
        order.setCustomerAddress(rs.getString("customerAddress"));
        order.setShipperId((Integer) rs.getObject("shipperId"));
        order.setPaymentMethod(rs.getString("paymentMethod"));
        order.setPaymentStatus(rs.getString("paymentStatus"));
        order.setTotalPrice(rs.getDouble("totalPrice"));
        order.setRejectionReason(rs.getString("rejectionReason"));
        return order;
    }

    public boolean updateOrderStatusById(int id, String status) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE OrderTB\n"
                    + "SET orderStatus = ?\n"
                    + "WHERE orderId = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Order> getOrders() {
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT \n"
                    + "    o.*,\n"
                    + "    SUM(oc.priceAtOrder) AS totalPrice\n"
                    + "FROM \n"
                    + "    OrderTB o\n"
                    + "LEFT JOIN \n"
                    + "    OrderContentTB oc ON o.orderId = oc.orderId\n"
                    + "GROUP BY \n"
                    + "    o.orderId, o.accId, o.orderDate, o.orderStatus, \n"
                    + "    o.customerName, o.customerEmail, o.customerPhone, \n"
                    + "    o.customerAddress, o.shipperId, o.paymentMethod, o.paymentStatus, \n"
                    + "    o.rejectionReason;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(orderInfo(rs));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Order getOrderById(String id) {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection();
            String sql = "SELECT \n"
                    + "    o.*, \n"
                    + "    SUM(oc.priceAtOrder) AS totalPrice \n"
                    + "FROM \n"
                    + "    OrderTB o \n"
                    + "LEFT JOIN \n"
                    + "    OrderContentTB oc ON o.orderId = oc.orderId \n"
                    + "WHERE \n"
                    + "    o.orderId = ? \n"
                    + "GROUP BY \n"
                    + "    o.orderId, o.accId, o.orderDate, o.orderStatus, \n"
                    + "    o.customerName, o.customerEmail, o.customerPhone, \n"
                    + "    o.customerAddress, o.shipperId, o.paymentMethod, o.paymentStatus,  o.rejectionReason;";

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return orderInfo(rs);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ArrayList<Integer> getOrderContentById(String id) {
        DBContext db = new DBContext();
        ArrayList<Integer> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM OrderContentTB WHERE orderId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("petId"));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
