/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Invoice;
import Models.InvoiceContent;
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

    private Invoice invoiceInfoForList(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getInt("orderId"));
        order.setCustomerName(rs.getString("customerName"));
        java.math.BigDecimal discountAmountBd = (java.math.BigDecimal) rs.getObject("discountAmountAtApply");
        if (discountAmountBd != null) {
            order.setDiscountAmountAtApply(discountAmountBd.doubleValue());
        } else {
            order.setDiscountAmountAtApply(null);
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoiceId"));
        invoice.setIssueDate(rs.getTimestamp("issueDate"));
        invoice.setPaymentMethod(rs.getString("paymentMethod"));
        invoice.setTotalAmount(rs.getBigDecimal("finalAmount"));
        invoice.setOrder(order);

        return invoice;
    }

    public Invoice getInvoiceDetailById(int invoiceId) {
        Invoice invoice = null;
        String sql = "SELECT i.*, o.* FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId WHERE i.invoiceId = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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

                    invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("invoiceId"));
                    invoice.setIssueDate(rs.getTimestamp("issueDate"));
                    invoice.setTotalAmount(rs.getBigDecimal("totalAmount"));
                    invoice.setPaymentMethod(rs.getString("paymentMethod"));
                    invoice.setOrder(order);

                    invoice.setInvoiceContents(getInvoiceContents(invoiceId));

                    java.math.BigDecimal calculatedTotal = invoice.getInvoiceContents().stream()
                            .map(InvoiceContent::getPriceAtInvoice)
                            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
                    invoice.setTotalAmount(calculatedTotal);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoice;
    }

    private List<InvoiceContent> getInvoiceContents(int invoiceId) {
        List<InvoiceContent> contents = new ArrayList<>();
        String sql = "SELECT * FROM InvoiceContentTB WHERE invoiceId = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InvoiceContent item = new InvoiceContent();
                    item.setInvoiceContentId(rs.getInt("invoiceContentId"));
                    item.setInvoiceId(rs.getInt("invoiceId"));
                    item.setPetId((Integer) rs.getObject("petId"));
                    item.setPetName(rs.getString("petName"));
                    item.setPetBreed(rs.getString("petBreed"));
                    item.setPetGender(rs.getString("petGender"));
                    item.setPetDob(rs.getDate("petDob"));
                    item.setPriceAtInvoice(rs.getBigDecimal("priceAtInvoice"));
                    contents.add(item);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contents;
    }

    private StringBuilder buildInvoiceFilterQuery(String orderId, String paymentMethod, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder(
                " FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId "
                + "LEFT JOIN (SELECT orderId, SUM(priceAtOrder) as calculatedTotal FROM OrderContentTB GROUP BY orderId) oc ON o.orderId = oc.orderId "
                + "WHERE 1=1 "
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
        StringBuilder sqlBase = new StringBuilder("SELECT COUNT(i.invoiceId) FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId WHERE 1=1 ");
        if (orderId != null && orderId.matches("\\d+")) {
            sqlBase.append("AND o.orderId = ? ");
        }
        if (paymentMethod != null && !paymentMethod.trim().isEmpty()) {
            sqlBase.append("AND i.paymentMethod = ? ");
        }
        if (startDate != null) {
            sqlBase.append("AND CAST(i.issueDate AS DATE) >= ? ");
        }
        if (endDate != null) {
            sqlBase.append("AND CAST(i.issueDate AS DATE) <= ? ");
        }

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sqlBase.toString())) {

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

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Invoice> filterInvoices(String orderId, String paymentMethod, Date startDate, Date endDate, int pageNumber, int pageSize) {
        List<Invoice> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT i.invoiceId, i.issueDate, i.paymentMethod, o.orderId, o.customerName, o.discountAmountAtApply, "
                + "(ISNULL(oc.calculatedTotal, 0) - ISNULL(o.discountAmountAtApply, 0)) AS finalAmount "
        );
        sql.append(buildInvoiceFilterQuery(orderId, paymentMethod, startDate, endDate));
        sql.append("ORDER BY i.issueDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

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

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(invoiceInfoForList(rs));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public int getInvoiceIdByOrderId(int id) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT invoiceId FROM InvoiceTB WHERE orderId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("invoiceId");
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
        return 0;
    }
}
