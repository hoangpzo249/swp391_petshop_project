/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Pet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class PetDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private Pet PetInfo(ResultSet rs) throws Exception {
        return new Pet(
                rs.getInt("petId"),
                rs.getString("petName"),
                rs.getDate("petDob"),
                rs.getString("petOrigin"),
                rs.getString("petGender"),
                rs.getInt("petAvailability"),
                rs.getString("petColor"),
                rs.getInt("petVaccination"),
                rs.getString("petDescription"),
                rs.getDouble("petPrice"),
                rs.getInt("breedId"),
                rs.getInt("createdBy")
        );
    }

    public Pet getPetById(int id) {

        Pet pet = null;
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM PetTB WHERE petId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pet = PetInfo(rs);
                pet.setImages(getImagesByPetId(pet.getPetId()));
            }
        } catch (Exception ex) {

        }
        return pet;
    }

    public List<byte[]> getImagesByPetId(int petId) {
        List<byte[]> images = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT imageData FROM PetImageTB WHERE petId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);
            rs = ps.executeQuery();
            while (rs.next()) {
                images.add(rs.getBytes("imageData"));
            }
        } catch (Exception e) {

        }
        return images;
    }
}
