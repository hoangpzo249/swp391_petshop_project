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
import model.OrderContent;

/**
 *
 * @author QuangAnh
 */
public class OrderContentDAO extends DBContext{
    public List<OrderContent> getOrderContentsByCustomerId(int customerId){
    List<OrderContent> list = new ArrayList<>();
    String sql = "SELECT oc.orderContentId, oc.orderId, oc.petId, p.name, p.image, o.status " +
                 "FROM OrderContentTB oc " +
                 "JOIN OrderTB o ON oc.orderId = o.orderId " +
                 "JOIN PetTB p ON oc.petId = p.petId " +
                 "WHERE o.customerId = ?";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            OrderContent oc = new OrderContent();
            oc.setOrderContentId(rs.getInt("orderContentId"));
            oc.setOrderId(rs.getInt("orderId"));
            oc.setPetId(rs.getInt("petId"));
            list.add(oc);
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    return list;
}

}
