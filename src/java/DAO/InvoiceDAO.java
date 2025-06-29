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

    private StringBuilder buildInvoiceFilterWhereClause(String searchKey, String status, Date startDate, Date endDate) {
        StringBuilder whereClause = new StringBuilder();

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            String trimmedKey = searchKey.trim();
            if (trimmedKey.matches("\\d+")) {
                whereClause.append("(i.invoiceId = ? OR o.orderId = ?) ");
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
            whereClause.append("CAST(i.issueDate AS DATE) >= ? ");
        }

        if (endDate != null) {
            if (whereClause.length() > 0) {
                whereClause.append("AND ");
            }
            whereClause.append("CAST(i.issueDate AS DATE) <= ? ");
        }

        if (whereClause.length() > 0) {
            return new StringBuilder("WHERE ").append(whereClause);
        }

        return new StringBuilder();
    }

    public int countFilteredInvoices(String searchKey, String status, Date startDate, Date endDate) {
        String baseSql = "SELECT COUNT(i.invoiceId) FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId ";
        StringBuilder whereClause = buildInvoiceFilterWhereClause(searchKey, status, startDate, endDate);
        String finalSql = baseSql + whereClause.toString();

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(finalSql)) {

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String trimmedKey = searchKey.trim();
                if (trimmedKey.matches("\\d+")) {
                    int id = Integer.parseInt(trimmedKey);
                    ps.setInt(paramIndex++, id);
                    ps.setInt(paramIndex++, id);
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

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, "Error counting filtered invoices", ex);
        }
        return 0;
    }

    public List<Invoice> filterInvoices(String searchKey, String status, Date startDate, Date endDate, String sort, int pageNumber, int pageSize) {
        List<Invoice> list = new ArrayList<>();
        String sortDirection = "ASC".equalsIgnoreCase(sort) ? "ASC" : "DESC";

        String baseSql = "SELECT i.*, o.* FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId ";
        StringBuilder whereClause = buildInvoiceFilterWhereClause(searchKey, status, startDate, endDate);

        String finalSql = baseSql + whereClause.toString()
                + "ORDER BY i.invoiceId " + sortDirection + " "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(finalSql)) {

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                String trimmedKey = searchKey.trim();
                if (trimmedKey.matches("\\d+")) {
                    int id = Integer.parseInt(trimmedKey);
                    ps.setInt(paramIndex++, id);
                    ps.setInt(paramIndex++, id);
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

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(invoiceInfo(rs));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(InvoiceDAO.class.getName()).log(Level.SEVERE, "Error filtering invoices", ex);
        }
        return list;
    }
}
