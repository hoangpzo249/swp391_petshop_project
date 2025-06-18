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
import Model.Pet;
/**
 *
 * @author QuangAnh
 */
public class PetDAO extends DBContext {

    public Pet getPetById(int petId) {
        String sql = "SELECT * FROM PetTB WHERE petId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Pet pet = new Pet();
                pet.setPetId(rs.getInt("petId"));
                pet.setPetName(rs.getString("petName"));
                pet.setPetColor(rs.getString("petColor"));
                pet.setPetPrice(rs.getDouble("petPrice"));
                pet.setPetGender(rs.getString("petGender"));
                // Thêm các trường khác nếu có
                return pet;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}