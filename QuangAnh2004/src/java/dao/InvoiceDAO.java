/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dao.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Invoice;

/**
 *
 * @author QuangAnh
 */
public class InvoiceDAO extends DBContext{
    public List<Invoice> getInvoicesByCustomer(int accId) {
        List<Invoice> list = new ArrayList<>();
        String sql = """
            SELECT i.invoiceId, i.orderId, i.issueDate, i.totalAmount, i.taxAmount, i.paymentMethod
            FROM InvoiceTB i
            JOIN OrderTB o ON i.orderId = o.orderId
            WHERE o.accId = ?
            ORDER BY i.issueDate DESC
        """;
        
        try (PreparedStatement stm = c.prepareStatement(sql)){
        stm.setInt(1, accId);
        ResultSet rs = stm.executeQuery();
        while (rs.next()){
            Invoice in = new Invoice();
            rs.getInt("invoiceId");
            rs.getInt("orderId");
            rs.getDate("issueDate");
            rs.getDouble("totalAmount");
            rs.getDouble("taxAmount");
            rs.getString("paymentMethod");
            list.add(in);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
        
}
