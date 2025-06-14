/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

/**
 *
 * @author QuangAnh
 */
public class InvoiceDAO extends DBContext{

   public List<Invoice> getInvoicesByAccountId(int accId) {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT i.invoiceId, i.orderId, i.issueDate, i.totalAmount, i.taxAmount, i.paymentMethod " +
                     "FROM InvoiceTB i JOIN OrderTB o ON i.orderId = o.orderId " +
                     "WHERE o.accId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
        return list;
    }

    
}
