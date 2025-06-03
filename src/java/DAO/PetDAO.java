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

    public List<Pet> getSimilarPets(int breedId, int excludedPetId) {
        List<Pet> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM PetTB WHERE petAvailability=1 AND breedId=? AND petId!=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, breedId);
            ps.setInt(2, excludedPetId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setImages(getImagesByPetId(pet.getPetId()));
                list.add(pet);
            }
        } catch (Exception ex) {
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
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
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
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
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
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
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
        return images;
    }

    public List<String> getAllOrigins() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petOrigin FROM PetTB WHERE petOrigin IS NOT NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("petOrigin"));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<String> getAllGenders() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petGender FROM PetTB WHERE petGender IS NOT NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("petGender"));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<String> getAllColors() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petColor FROM PetTB WHERE petColor IS NOT NULL";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("petColor"));
            }
        } catch (Exception e) {
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
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        }
        return list;
    }

    public List<String> getAllVaccinationStatus() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petVaccination FROM PetTB";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int status = rs.getInt("petVaccination");
                list.add(status == 1 ? "Đã tiêm" : "Chưa tiêm");
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Pet> filterPets(String breed, String species, String search, int num1, int num2, String sort,
            String gender, String color, String origin, String ageRange, String vaccinationStatus) {

        List<Pet> listPet = new ArrayList<>();
        List<Pet> tempList = new ArrayList<>();

        if (breed == null || breed.isEmpty()) {
            breed = "%";
        }
        if (species == null || species.isEmpty()) {
            species = "%";
        }
        if (search == null || search.isEmpty()) {
            search = "%";
        } else {
            search = "%" + search + "%";
        }

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT p.*, b.breedName FROM PetTB p INNER JOIN BreedTB b ON p.breedId = b.breedId "
                    + "WHERE p.petAvailability = 1 AND (p.petName LIKE ? OR b.breedName LIKE ?)";

            if (!"%".equals(breed)) {
                sql += " AND b.breedId = ?";
            }
            if (!"%".equals(species)) {
                sql += " AND b.breedSpecies = ?";
            }
            if (num1 != 0 || num2 != 0) {
                sql += " AND p.petPrice BETWEEN ? AND ?";
            }
            if (gender != null && !gender.isEmpty()) {
                sql += " AND p.petGender = ?";
            }
            if (color != null && !color.isEmpty()) {
                sql += " AND p.petColor = ?";
            }
            if (origin != null && !origin.isEmpty()) {
                sql += " AND p.petOrigin = ?";
            }
            if (vaccinationStatus != null && !vaccinationStatus.isEmpty()) {
                sql += " AND p.petVaccination = ?";
            }

            if (sort != null && !sort.isEmpty()) {
                switch (sort) {
                    case "popular", "new" ->
                        sql += " ORDER BY p.petId DESC";
                    case "az" ->
                        sql += " ORDER BY p.petName ASC";
                    case "za" ->
                        sql += " ORDER BY p.petName DESC";
                    case "price-asc" ->
                        sql += " ORDER BY p.petPrice ASC";
                    case "price-desc" ->
                        sql += " ORDER BY p.petPrice DESC";
                    default ->
                        sql += " ORDER BY p.petName ASC";
                }
            }

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, search);
            ps.setString(i++, search);
            if (!"%".equals(breed)) {
                ps.setInt(i++, Integer.parseInt(breed));
            }
            if (!"%".equals(species)) {
                ps.setString(i++, species);
            }
            if (num1 != 0 || num2 != 0) {
                ps.setInt(i++, Math.min(num1, num2));
                ps.setInt(i++, Math.max(num1, num2));
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(i++, gender);
            }
            if (color != null && !color.isEmpty()) {
                ps.setString(i++, color);
            }
            if (origin != null && !origin.isEmpty()) {
                ps.setString(i++, origin);
            }
            if (vaccinationStatus != null && !vaccinationStatus.isEmpty()) {
                ps.setInt(i++, vaccinationStatus.equals("Đã tiêm") ? 1 : 0);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setBreedName(rs.getString("breedName"));
                if (ageRange != null && !ageRange.isEmpty()) {
                    Date dob = pet.getPetDob();
                    if (dob != null) {
                        int months = getAgeInMonths(dob);
                        boolean match = ("under30".equals(ageRange) && months < 30)
                                || ("30-36".equals(ageRange) && months >= 30 && months <= 36)
                                || ("37-44".equals(ageRange) && months >= 37 && months <= 44)
                                || ("above44".equals(ageRange) && months >= 45);
                        if (!match) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                tempList.add(pet);
            }

            rs.close();
            ps.close();
            conn.close();

            for (Pet pet : tempList) {
                pet.setImages(getImagesByPetId(pet.getPetId()));
                listPet.add(pet);
            }

        } catch (Exception ex) {
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
        return listPet;
    }
}
