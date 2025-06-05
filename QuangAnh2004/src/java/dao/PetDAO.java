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
import model.Pet;

/**
 *
 * @author QuangAnh
 */
public class PetDAO extends DBContext{
        public List<Pet> getPetId(int petId){
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT * FROM PetTB WHERE petId = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, petId);
            ResultSet rs = ps.executeQuery(); 

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setPetId(rs.getInt("petId"));
                pet.setPetName(rs.getString("petName"));
                pet.setPetDob(rs.getDate("petDob"));
                pet.setPetOrigin(rs.getString("petOrigin"));
                pet.setPetGender(rs.getString("petGender"));
                pet.setPetAvailability(rs.getBoolean("petAvailability"));
                pet.setPetColor(rs.getString("petColor"));
                pet.setPetVaccination(rs.getBoolean("petVaccination"));
                pet.setPetDescription(rs.getString("petDescription"));
                pet.setPetPrice(rs.getDouble("petPrice"));
                pet.setBreedId(rs.getInt("breedId"));
                pet.setCreatedBy(rs.getString("createdBy"));
                list.add(pet);
            }
        }catch (Exception e){
            
        }

        return list;
    }
}
