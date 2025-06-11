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
import java.util.Date;
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

    public boolean updateOrderStatusById(int id, String status, String reason) {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = db.getConnection();
            String sql;

            if (reason != null && !reason.trim().isEmpty()) {
                sql = "UPDATE OrderTB "
                        + "SET orderStatus = ?, rejectionReason = ? "
                        + "WHERE orderId = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, status);
                ps.setString(2, reason);
                ps.setInt(3, id);
            } else {
                sql = "UPDATE OrderTB "
                        + "SET orderStatus = ?, rejectionReason = NULL "
                        + "WHERE orderId = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, status);
                ps.setInt(2, id);
            }

            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
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
     public List<Order> filterOrders(String searchKey, String status, Date startDate, Date endDate) {
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        
        List<Object> params = new ArrayList<>();

        String baseSql = "SELECT o.*, ISNULL(oc.totalPrice, 0) AS totalPrice FROM OrderTB o "
                       + "LEFT JOIN (SELECT orderId, SUM(priceAtOrder) as totalPrice FROM OrderContentTB GROUP BY orderId) oc "
                       + "ON o.orderId = oc.orderId ";

        StringBuilder whereClause = new StringBuilder();

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            if (searchKey.matches(".*[a-zA-Z].*")) {
                whereClause.append("customerName LIKE ? ");
                params.add("%" + searchKey + "%");
            } else {
                try {
                    Integer.parseInt(searchKey);
                    whereClause.append("o.orderId = ? ");
                    params.add(searchKey);
                } catch (NumberFormatException e) {
                }
            }
        }

        if (status != null && !status.trim().isEmpty()) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("orderStatus = ? ");
            params.add(status);
        }

        if (startDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(orderDate AS DATE) >= ? ");
            params.add(startDate);
        }

        if (endDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(orderDate AS DATE) <= ? ");
            params.add(endDate);
        }

        String finalSql = baseSql;
        if (whereClause.length() > 0) {
            finalSql += "WHERE " + whereClause.toString();
        }
        finalSql += "ORDER BY o.orderId ASC";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(finalSql);

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(orderInfo(rs));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return list;
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

    public ArrayList<Integer> getOrderContentById(int id) {
        DBContext db = new DBContext();
        ArrayList<Integer> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM OrderContentTB WHERE orderId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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
