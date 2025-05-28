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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public List<Pet> getSimilarPets(int breedId) {
        List<Pet> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM PetTB WHERE petAvailability=1 AND breedId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, breedId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setImages(getImagesByPetId(pet.getPetId()));
                list.add(pet);
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
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
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return pet;
    }

    public List<Pet> getAllPets() {
        List<Pet> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM PetTB";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setImages(getImagesByPetId(pet.getPetId()));
                list.add(pet);
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return list;
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
            e.printStackTrace();
        } 
        return images;
    }

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

public List<String> getAllOrigins() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petOrigin FROM PetTB WHERE petOrigin IS NOT NULL";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("petOrigin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public List<String> getAllGenders() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petGender FROM PetTB WHERE petGender IS NOT NULL";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("petGender"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public List<String> getAllColors() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petColor FROM PetTB WHERE petColor IS NOT NULL";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("petColor"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static int getAgeInMonths(Date dob) {
    Calendar birth = Calendar.getInstance();
    birth.setTime(dob);

    Calendar now = Calendar.getInstance();

    int years = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    int months = now.get(Calendar.MONTH) - birth.get(Calendar.MONTH);

    return years * 12 + months;
}

   public List<String> getAllAgeRanges() {
    List<String> list = new ArrayList<>();
    String sql = "SELECT petDob FROM PetTB WHERE petDob IS NOT NULL";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
    Date dob = rs.getDate("petDob");
    if (dob != null) {
        int months = getAgeInMonths(dob);

       if (months < 30 && !list.contains("Dưới 2.5 tuổi")) {
    list.add("Dưới 2.5 tuổi");
} else if (months >= 30 && months <= 36 && !list.contains("2.5 – 3 tuổi")) {
    list.add("2.5 – 3 tuổi");
} else if (months >= 37 && months <= 44 && !list.contains("3 – 3.5 tuổi")) {
    list.add("3 – 3.5 tuổi");
} else if (months >= 45 && !list.contains("Trên 3.5 tuổi")) {
    list.add("Trên 3.5 tuổi");
}


    }
}

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public List<String> getAllVaccinationStatus() {
    List<String> list = new ArrayList<>();
    String sql = "SELECT DISTINCT petVaccination FROM PetTB";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int status = rs.getInt("petVaccination");
            list.add(status == 1 ? "Đã tiêm" : "Chưa tiêm");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    


}

 



