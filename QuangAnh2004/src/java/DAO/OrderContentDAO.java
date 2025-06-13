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
import model.OrderContent;
/**
 *
 * @author QuangAnh
 */
public class OrderContentDAO extends DBContext{
    public List<OrderContent> getByOrderId(int orderId) {
        List<OrderContent> list = new ArrayList<>();
        String query = "SELECT orderContentId, orderId, petId FROM OrderContentTB WHERE orderId = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderContent oc = new OrderContent(
                    rs.getInt("orderContentId"),
                    rs.getInt("orderId"),
                    rs.getInt("petId")
                );
                list.add(oc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
