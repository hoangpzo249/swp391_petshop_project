/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Order;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
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
        order.setDiscountId((Integer) rs.getObject("discountId"));
        java.math.BigDecimal discountAmountBd = (java.math.BigDecimal) rs.getObject("discountAmountAtApply");

        if (discountAmountBd != null) {
            order.setDiscountAmountAtApply(discountAmountBd.doubleValue());
        } else {
            order.setDiscountAmountAtApply(null);
        }
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
                    + "    o.rejectionReason, o.discountId, o.discountAmountAtApply;";
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

    private StringBuilder buildFilterWhereClause(String searchKey, String status, Date startDate, Date endDate) {
        StringBuilder whereClause = new StringBuilder();

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            String trimmedKey = searchKey.trim();
            if (trimmedKey.matches("\\d+")) {
                whereClause.append("o.orderId = ? ");
            } else if (trimmedKey.contains("@")) {
                whereClause.append("o.customerEmail LIKE ? ");
            } else {
                whereClause.append("o.customerName LIKE ? ");
            }
        }

        if (status != null && !status.trim().isEmpty()) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("o.orderStatus = ? ");
        }

        if (startDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(o.orderDate AS DATE) >= ? ");
        }

        if (endDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(o.orderDate AS DATE) <= ? ");
        }

        if (whereClause.length() > 0) {
            return new StringBuilder("WHERE ").append(whereClause);
        }

        return new StringBuilder();
    }

    public int countFilteredOrders(String searchKey, String status, Date startDate, Date endDate) {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String baseSql = "SELECT COUNT(o.orderId) FROM OrderTB o ";
        StringBuilder whereClause = buildFilterWhereClause(searchKey, status, startDate, endDate);
        String finalSql = baseSql + whereClause.toString();

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(finalSql);

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String trimmedKey = searchKey.trim();
                if (trimmedKey.matches("\\d+")) {
                    ps.setInt(paramIndex++, Integer.parseInt(trimmedKey));
                } else if (trimmedKey.contains("@")) {
                    ps.setString(paramIndex++, "%" + trimmedKey + "%");
                } else {
                    ps.setString(paramIndex++, "%" + trimmedKey + "%");
                }
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (startDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            }
            if (endDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
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
        return 0;
    }

    public List<Order> filterOrderForSeller(String searchKey, String status, Date startDate, Date endDate, String sort, int pageNumber, int pageSize) {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sortDirection = "DESC";
        if ("ASC".equalsIgnoreCase(sort)) {
            sortDirection = "ASC";
        }

        List<Order> list = new ArrayList<>();

        String baseSql = "SELECT o.*, ISNULL(oc.totalPrice, 0) AS totalPrice FROM OrderTB o "
                + "LEFT JOIN (SELECT orderId, SUM(priceAtOrder) as totalPrice FROM OrderContentTB GROUP BY orderId) oc "
                + "ON o.orderId = oc.orderId ";

        StringBuilder whereClause = buildFilterWhereClause(searchKey, status, startDate, endDate);

        String finalSql = baseSql + whereClause.toString()
                + "ORDER BY o.orderId " + sortDirection + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(finalSql);

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String trimmedKey = searchKey.trim();
                if (trimmedKey.matches("\\d+")) {
                    ps.setInt(paramIndex++, Integer.parseInt(trimmedKey));
                } else if (trimmedKey.contains("@")) {
                    ps.setString(paramIndex++, "%" + trimmedKey + "%");
                } else {
                    ps.setString(paramIndex++, "%" + trimmedKey + "%");
                }
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (startDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
            }
            if (endDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            int offset = (pageNumber - 1) * pageSize;
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(orderInfo(rs));
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
                    + "    o.customerAddress, o.shipperId, o.paymentMethod, o.paymentStatus, o.rejectionReason, o.discountId, o.discountAmountAtApply;";

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

            String sql = "INSERT INTO OrderTB (accId, orderDate, orderStatus, customerName, customerEmail, customerPhone, customerAddress, shipperId, paymentMethod, paymentStatus, discountId, discountAmountAtApply) "
                    + "VALUES (?, GETDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            if (order.getDiscountAmountAtApply() == null) {
                ps.setNull(11, Types.DECIMAL);
            } else {
                ps.setDouble(11, order.getDiscountAmountAtApply());
            }

            if (order.getDiscountAmountAtApply() == null) {
                ps.setNull(11, Types.DECIMAL);
            } else {
                ps.setDouble(11, order.getDiscountAmountAtApply());
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

    public List<Order> getOrderCus(int accId, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "                    o.customerAddress, o.shipperId, o.paymentMethod, o.paymentstatus, SUM(c.priceAtOrder) AS totalPrice, o.rejectionReason, o.discountAmountAtApply\n"
                    + "                   from ordertb o\n"
                    + "                   join OrderContentTB c on o.orderid=c.orderid\n"
                    + "                    where accId =? and orderStatus = ?\n"
                    + "                  group by o.orderId, o.accId, o.orderDate,o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "                   o.customerAddress, o.shipperId, o.paymentMethod,o.paymentstatus, o.rejectionReason, o.discountAmountAtApply";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            ps.setString(2, status);
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
                o.setShipperId((Integer) rs.getObject("shipperId"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setPaymentStatus(rs.getString("paymentStatus"));
                o.setRejectionReason(rs.getString("rejectionReason"));
                o.setTotalPrice(rs.getDouble("totalPrice"));
                Object discount = rs.getObject("discountAmountAtApply");
                o.setDiscountAmountAtApply(discount != null ? (Double) discount : 0.0);
                list.add(o);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public List<Order> getOrderCusPet(int accId, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT oTb.orderId, otb.accId, p.petName, p.petPrice , p.petColor,\n"
                    + "(select top 1 imageData from PetImageTB pImg\n"
                    + "where pImg.petId=p.petId )  as imageData\n"
                    + "                   from OrderContentTB oCt\n"
                    + "                   join pettb p on p.petid=oCt.petid\n"
                    + "				   join OrderTB oTb on oTb.orderId = oCt.orderId\n"
                    + "				   join PetImageTB pImg on pImg.petId=p.petId\n"
                    + "                    where accId =? and orderStatus = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            ps.setString(2, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setAccId(rs.getInt("accId"));
                o.setPetName(rs.getString("petName"));

                o.setPetPrice(rs.getDouble("petPrice"));
                o.setPetColor(rs.getString("petColor"));
                o.setImageData(rs.getBytes("imageData"));

                list.add(o);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public List<Order> getOrderShipperId(int accId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "                    o.customerAddress, o.shipperId\n"
                    + "                    from ordertb o\n"
                    + "                  \n"
                    + "                    where shipperId = ?";

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
                list.add(o);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public List<Order> getOrderDetailProductShipperId(int shipperId, int orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT oTb.orderId, otb.accId, p.petName, p.petPrice , p.petColor,\n"
                    + "(select top 1 imageData from PetImageTB pImg\n"
                    + "where pImg.petId=p.petId )  as imageData\n"
                    + "                   from OrderContentTB oCt\n"
                    + "                   join pettb p on p.petid=oCt.petid\n"
                    + "				   join OrderTB oTb on oTb.orderId = oCt.orderId\n"
                    + "				   join PetImageTB pImg on pImg.petId=p.petId\n"
                    + "where shipperid =? and oTb.orderId =?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, shipperId);
            ps.setInt(2, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setAccId(rs.getInt("accId"));
                o.setPetName(rs.getString("petName"));
                o.setPetPrice(rs.getDouble("petPrice"));
                o.setPetColor(rs.getString("petColor"));
                o.setImageData(rs.getBytes("imageData"));
                list.add(o);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public Order getOrderDetailShipperId(int shipperId, int orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        Order o = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "o.customerAddress, o.shipperId, o.paymentMethod, o.paymentstatus, SUM(c.priceAtOrder) AS totalPrice, o.rejectionReason, o.discountAmountAtApply\n"
                    + "from ordertb o\n"
                    + "join OrderContentTB c on o.orderid=c.orderid\n"
                    + "join pettb p on c.petid=p.petid\n"
                    + "where shipperid =? and o.orderId =?\n"
                    + "group by o.orderId, o.accId, o.orderDate,o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "o.customerAddress, o.shipperId, o.paymentMethod,o.paymentstatus, o.rejectionReason, o.discountAmountAtApply";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, shipperId);
            ps.setInt(2, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                o.setAccId(rs.getInt("accId"));
                o.setOrderDate(rs.getTimestamp("orderDate"));
                o.setDeliveryDate(rs.getTimestamp("deliveryDate"));
                o.setOrderStatus(rs.getString("orderStatus"));
                o.setCustomerName(rs.getString("customerName"));
                o.setCustomerEmail(rs.getString("customerEmail"));
                o.setCustomerPhone(rs.getString("customerPhone"));
                o.setCustomerAddress(rs.getString("customerAddress"));
                o.setShipperId((Integer) rs.getObject("shipperId"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setPaymentStatus(rs.getString("paymentStatus"));
                o.setRejectionReason(rs.getString("rejectionReason"));
                o.setTotalPrice(rs.getDouble("totalPrice"));
                Object discount = rs.getObject("discountAmountAtApply");
                o.setDiscountAmountAtApply(discount != null ? (Double) discount : 0.0);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return o;
    }

    public List<Order> getOrderCusSearch(int accId, String status, int orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        List<Order> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT o.orderId, o.accId, o.orderDate, o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "o.customerAddress, o.shipperId, o.paymentMethod, o.paymentstatus, SUM(c.priceAtOrder) AS totalPrice, o.rejectionReason, o.discountAmountAtApply\n"
                    + "from ordertb o\n"
                    + "join OrderContentTB c on o.orderid=c.orderid\n"
                    + "where accId =? and orderStatus = ? and o.orderId = ?\n"
                    + "group by o.orderId, o.accId, o.orderDate,o.deliveryDate, o.orderStatus, o.customerName, o.customerEmail, o.customerPhone,\n"
                    + "o.customerAddress, o.shipperId, o.paymentMethod,o.paymentstatus, o.rejectionReason, o.discountAmountAtApply";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            ps.setString(2, status);
            ps.setInt(3, orderId);
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
                o.setShipperId((Integer) rs.getObject("shipperId"));
                o.setPaymentMethod(rs.getString("paymentMethod"));
                o.setPaymentStatus(rs.getString("paymentStatus"));
                o.setRejectionReason(rs.getString("rejectionReason"));
                o.setTotalPrice(rs.getDouble("totalPrice"));
                o.setDiscountAmountAtApply((Double) rs.getObject("discountAmountAtApply"));
                list.add(o);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public boolean updatePickupOrder(int orderId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "update orderTb set orderstatus ='Shipping' where orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateDeliveredOrder(int orderId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "update orderTb set orderstatus ='Delivered', deliveryDate = GETDATE(), paymentStatus = 'Paid' where orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean assignShipper(int orderId, int shipperId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE OrderTB SET shipperId = ?, orderStatus = 'Pending Shipper' WHERE orderId = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, shipperId);
            ps.setInt(2, orderId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public boolean unassignShipper(int orderId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE OrderTB SET shipperId = NULL, orderStatus = 'Confirmed' WHERE orderId = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int totalOrdersFulfilled(Date startDate, Date endDate) {
        int total = 0;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS totalOrdersFulfilled\n"
                    + "FROM OrderTB o\n"
                    + "WHERE o.deliveryDate IS NOT NULL\n");
            if (startDate != null) {
                sql.append("AND CAST(o.deliveryDate AS DATE) >= ?\n");
            }
            if (endDate != null) {
                sql.append("AND CAST(o.deliveryDate AS DATE) <= ?\n");
            }
            ps = conn.prepareStatement(sql.toString());
            int param = 1;
            if (startDate != null) {
                ps.setDate(param++, startDate);
            }
            if (endDate != null) {
                ps.setDate(param++, endDate);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("totalOrdersFulfilled");
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        }
        return total;
    }

    public Double getDiscountAmountAtApply(int orderId) {
        Double discount = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT discountAmountAtApply FROM OrderTB WHERE orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                discount = rs.getDouble("discountAmountAtApply");
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
        
        return discount;
    }

    public int countPendingShipper(int accId) {
        int count = 0;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) FROM ordertb where shipperId = ? and orderStatus =  'Pending Shipper'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countPendingShipperShipping(int accId) {
        int count = 0;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) FROM ordertb where shipperId = ? and orderStatus =  'Shipping'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countPendingShipperDelivered(int accId) {
        int count = 0;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) FROM ordertb where shipperId = ? and orderStatus =  'Delivered'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

     public boolean checkPenndingShippingShipper(int shipperId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT 1 from ordertb where shipperId = ? and (orderstatus = 'Pending shipper' or orderstatus = 'shipping')";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, shipperId);
            rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
