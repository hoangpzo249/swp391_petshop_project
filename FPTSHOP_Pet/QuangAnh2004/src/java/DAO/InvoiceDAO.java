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

   public List<Invoice> getInvoiceById(int invoiceId) {
    List<Invoice> list = new ArrayList<>();
    String sql = "SELECT invoiceId, orderId, issueDate, totalAmount, taxAmount, paymentMethod " +
                 "FROM InvoiceTB WHERE invoiceId = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, invoiceId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Invoice inv = new Invoice();
            inv.setInvoiceId(rs.getInt("invoiceId"));
            inv.setOrderId(rs.getInt("orderId"));
            inv.setIssuaDate(rs.getDate("issueDate"));
            inv.setTotalAmount(rs.getDouble("totalAmount"));
            inv.setTaxAmount(rs.getDouble("taxAmount"));
            inv.setPaymentMethod(rs.getString("paymentMethod"));
            list.add(inv);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


    
}