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
import Model.Invoice;

/**
 *
 * @author QuangAnh
 */
public class InvoiceDAO extends DBContext{

    public Invoice getInvoiceByOrderId(int orderId) {
        String sql = "SELECT * FROM InvoiceTB WHERE orderId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(2, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("invoiceId"));
                invoice.setOrderId(rs.getInt("orderId"));
                invoice.setIssueDate(rs.getDate("issueDate"));
                invoice.setTotalAmount(rs.getDouble("totalAmount"));
                invoice.setPaymentMethod(rs.getString("paymentMethod"));
                return invoice;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    
}