/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Invoice;
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
public class InvoiceDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private Invoice invoiceInfo(ResultSet rs) throws Exception {
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
        order.setRejectionReason(rs.getString("rejectionReason"));
        order.setDiscountId((Integer) rs.getObject("discountId"));

        java.math.BigDecimal discountAmountBd = (java.math.BigDecimal) rs.getObject("discountAmountAtApply");
        if (discountAmountBd != null) {
            order.setDiscountAmountAtApply(discountAmountBd.doubleValue());
        } else {
            order.setDiscountAmountAtApply(null);
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoiceId"));
        invoice.setIssueDate(rs.getTimestamp("issueDate"));
        invoice.setTotalAmount(rs.getBigDecimal("totalAmount"));
        invoice.setPaymentMethod(rs.getString("paymentMethod"));

        invoice.setOrder(order);

        return invoice;
    }

    private StringBuilder buildInvoiceFilterQuery(String orderId, String paymentMethod, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder(
                " FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId WHERE 1=1 "
        );

        if (orderId != null && orderId.matches("\\d+")) {
            sql.append("AND o.orderId = ? ");
        }
        if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
            sql.append("AND i.paymentMethod = ? ");
        }
        if (startDate != null) {
            sql.append("AND CAST(i.issueDate AS DATE) >= ? ");
        }
        if (endDate != null) {
            sql.append("AND CAST(i.issueDate AS DATE) <= ? ");
        }
        return sql;
    }

    public int countFilteredInvoices(String orderId, String paymentMethod, Date startDate, Date endDate) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sqlBase = new StringBuilder("SELECT COUNT(i.invoiceId) ");
        sqlBase.append(buildInvoiceFilterQuery(orderId, paymentMethod, startDate, endDate));

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sqlBase.toString());

            int paramIndex = 1;
            if (orderId != null && orderId.matches("\\d+")) {
                ps.setInt(paramIndex++, Integer.parseInt(orderId));
            }
            if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
                ps.setString(paramIndex++, paymentMethod);
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
            ex.printStackTrace();
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
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public List<Invoice> filterInvoices(String orderId, String paymentMethod, Date startDate, Date endDate, int pageNumber, int pageSize) {
        List<Invoice> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder("SELECT i.*, o.* ");
        sql.append(buildInvoiceFilterQuery(orderId, paymentMethod, startDate, endDate));
        sql.append("ORDER BY i.issueDate DESC ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (orderId != null && orderId.matches("\\d+")) {
                ps.setInt(paramIndex++, Integer.parseInt(orderId));
            }
            if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
                ps.setString(paramIndex++, paymentMethod);
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
                list.add(invoiceInfo(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
                ex.printStackTrace();
            }
        }
        return list;
    }
    
    public boolean invoiceIdExists(int invoiceId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT 1 FROM InvoiceTB WHERE invoiceId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
