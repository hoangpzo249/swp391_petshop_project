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
import java.sql.Statement;
import java.sql.Types;

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
        order.setDeliveryDate(rs.getTimestamp("deliveryDate"));
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
                        + "SET orderStatus = ?, "
                        + "deliveryDate = CASE WHEN ? = 'Delivered' THEN GETDATE() ELSE NULL END, "
                        + "rejectionReason = ? "
                        + "WHERE orderId = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, status);
                ps.setString(2, status);
                ps.setString(3, reason);
                ps.setInt(4, id);
            } else {
                sql = "UPDATE OrderTB "
                        + "SET orderStatus = ?, "
                        + "deliveryDate = CASE WHEN ? = 'Delivered' THEN GETDATE() ELSE NULL END, "
                        + "rejectionReason = NULL "
                        + "WHERE orderId = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, status);
                ps.setString(2, status);
                ps.setInt(3, id);
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
                    + "    o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, \n"
                    + "    o.customerName, o.customerEmail, o.customerPhone, \n"
                    + "    o.customerAddress, o.shipperId, o.paymentMethod, o.paymentStatus, \n"
                    + "    o.rejectionReason, o.discountId;";
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

    private StringBuilder buildFilterWhereClause(String searchKey, String status, Date startDate, Date endDate, List<Object> params) {
        StringBuilder whereClause = new StringBuilder();

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            if (searchKey.matches("\\d+")) {
                whereClause.append("o.orderId = ? ");
                params.add(Integer.parseInt(searchKey.trim()));
            } else {
                whereClause.append("o.customerName LIKE ? ");
                params.add("%" + searchKey.trim() + "%");
            }
        }

        if (status != null && !status.trim().isEmpty()) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("o.orderStatus = ? ");
            params.add(status);
        }

        if (startDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(o.orderDate AS DATE) >= ? ");
            params.add(startDate);
        }

        if (endDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(o.orderDate AS DATE) <= ? ");
            params.add(endDate);
        }

        if (whereClause.length() > 0) {
            return new StringBuilder("WHERE ").append(whereClause);
        }

        return new StringBuilder();
    }

    public int countFilteredOrders(String searchKey, String status, Date startDate, Date endDate) {
        DBContext db = new DBContext();
        List<Object> params = new ArrayList<>();
        String baseSql = "SELECT COUNT(o.orderId) FROM OrderTB o ";
        StringBuilder whereClause = buildFilterWhereClause(searchKey, status, startDate, endDate, params);
        String finalSql = baseSql + whereClause.toString();

        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(finalSql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Order> filterOrderForSeller(String searchKey, String status, Date startDate, Date endDate, int pageNumber, int pageSize) {
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String baseSql = "SELECT o.*, ISNULL(oc.totalPrice, 0) AS totalPrice FROM OrderTB o "
                + "LEFT JOIN (SELECT orderId, SUM(priceAtOrder) as totalPrice FROM OrderContentTB GROUP BY orderId) oc "
                + "ON o.orderId = oc.orderId ";

        StringBuilder whereClause = buildFilterWhereClause(searchKey, status, startDate, endDate, params);

        String finalSql = baseSql + whereClause.toString()
                + "ORDER BY o.orderId DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        int offset = (pageNumber - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(finalSql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(orderInfo(rs));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Order getOrderById(int id) {
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
                    + "    o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, \n"
                    + "    o.customerName, o.customerEmail, o.customerPhone, \n"
                    + "    o.customerAddress, o.shipperId, o.paymentMethod, o.paymentStatus, o.rejectionReason, o.discountId;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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

    public String getCustomerEmailByOrderId(int id) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT customerEmail FROM OrderTB\n"
                    + "WHERE orderId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
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
            } catch (Exception e) {
                Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public double getOrderPriceById(int id) {
        DBContext db = new DBContext();
        double totalPrice = 0;
        String sql = "SELECT SUM(priceAtOrder) AS totalPrice FROM OrderContentTB WHERE orderId = ?";

        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalPrice = rs.getDouble("totalPrice");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, "Error fetching order price for ID: " + id, ex);
        }

        return totalPrice;
    }

    public List<Integer> getOrderContentById(int id) {
        DBContext db = new DBContext();
        List<Integer> list = new ArrayList<>();
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

    public int addOrder(Order order) {
        int orderId = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();

            String sql = "INSERT INTO OrderTB (accId, orderDate, orderStatus, customerName, customerEmail, customerPhone, customerAddress, shipperId, paymentMethod, paymentStatus, discountId) "
                    + "VALUES (?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (order.getAccId() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, order.getAccId());
            }

            ps.setString(2, order.getOrderStatus());
            ps.setString(3, order.getCustomerName());
            ps.setString(4, order.getCustomerEmail());
            ps.setString(5, order.getCustomerPhone());
            ps.setString(6, order.getCustomerAddress());
            if (order.getShipperId() != null) {
                ps.setInt(7, order.getShipperId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            ps.setString(8, order.getPaymentMethod());
            ps.setString(9, order.getPaymentStatus());

            if (order.getDiscountId() == null) {
                ps.setNull(10, Types.INTEGER);
            } else {
                ps.setInt(10, order.getDiscountId());
            }

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
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

        return orderId;
    }

    public void addOrderContent(int orderId, int petId) {
        Connection conn = null;
        PreparedStatement ps = null;
        PetDAO petDAO = new PetDAO();

        try {
            conn = new DBContext().getConnection();
            String sql = "INSERT INTO OrderContentTB (orderId, petId,priceAtOrder) VALUES (?, ?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, petId);
            ps.setDouble(3, petDAO.getPetById(petId).getPetPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public List<Order> getOrderCus(int accId) {
        Connection conn = null;
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "o.customerAddress, o.shipperId, o.paymentMethod, o.paymentstatus, SUM(c.priceAtOrder) AS totalPrice, o.rejectionReason,p.petname\n"
                    + "from ordertb o\n"
                    + "join OrderContentTB c on o.orderid=c.orderid\n"
                    + "join pettb p on c.petid=p.petid\n"
                    + "where accId =?\n"
                    + "group by o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "o.customerAddress, o.shipperId, o.paymentMethod,o.paymentstatus, o.rejectionReason,p.petname";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setAccId(rs.getInt("accId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setDeliveryDate(rs.getTimestamp("deliveryDate"));
                o.setOrderStatus(rs.getString("orderStatus"));
                o.setCustomerName(rs.getString("customerName"));
                o.setCustomerEmail(rs.getString("customerEmail"));
                o.setCustomerPhone(rs.getString("customerPhone"));
                o.setCustomerAddress(rs.getString("customerAddress"));
                o.setShipperId(rs.getInt("shipperId"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setPaymentStatus(rs.getString("paymentStatus"));
                o.setRejectionReason(rs.getString("rejectionReason"));
                o.setTotalPrice(rs.getDouble("totalPrice"));
                o.setPetName(rs.getString("petName"));
                list.add(o);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean assignShipper(int orderId, int shipperId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE OrderTB SET shipperId = ?, orderStatus = 'Pending Shipper' WHERE orderId = ?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, shipperId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean unassignShipper(int orderId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE OrderTB SET shipperId = NULL, orderStatus = 'Confirmed' WHERE orderId = ?;";
            ps=conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
