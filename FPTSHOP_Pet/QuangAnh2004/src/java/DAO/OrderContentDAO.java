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
import Model.OrderContent;

/**
 *
 * @author QuangAnh
 */
public class OrderContentDAO extends DBContext {
    public List<OrderContent> getByOrderId(int orderId) {
        List<OrderContent> list = new ArrayList<>();
        String query = "SELECT orderContentId, orderId, petId, priceAtOrder FROM OrderContentTB WHERE orderId = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderContent oc = new OrderContent();

                oc.setOrderContentId(rs.getInt("orderContentId"));
                oc.setOrderId(rs.getInt("orderId"));
                oc.setPetId(rs.getInt("petId"));
                oc.setPriceAtOrder(rs.getDouble("priceAtOrder"));

                list.add(oc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public List<OrderContent> getOrderContentByPetId(int petId) {
    List<OrderContent> list = new ArrayList<>();
    String query = "SELECT orderContentId, orderId, petId, priceAtOrder FROM OrderContentTB WHERE petId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setInt(1, petId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            OrderContent oc = new OrderContent();

            oc.setOrderContentId(rs.getInt("orderContentId"));
            oc.setOrderId(rs.getInt("orderId"));
            oc.setPetId(rs.getInt("petId"));
            oc.setPriceAtOrder(rs.getDouble("priceAtOrder"));

            // Nếu bạn cần gán thêm thông tin Pet, thì dùng đoạn này:
            // PetDAO petDAO = new PetDAO();
            // Pet pet = petDAO.getPetById(petId);
            // oc.setPet(pet);  // nếu có setter tương ứng

            list.add(oc);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    
    public void deleteOrderContent(int orderContentId) {
    String query = "DELETE FROM OrderContentTB WHERE orderContentId = ?";

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setInt(1, orderContentId);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    


}
