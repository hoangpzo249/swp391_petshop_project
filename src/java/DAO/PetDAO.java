/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Breed;
import Models.Pet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

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
                rs.getInt("createdBy"),
                rs.getInt("petStatus")
        );
    }

    public int addPet(Pet pet) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO PetTB ("
                    + "petName, petDob, petOrigin, petGender, petAvailability, "
                    + "petColor, petVaccination, petDescription, petPrice, "
                    + "breedId, createdBy, petStatus) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, pet.getPetName());
            ps.setDate(2, pet.getPetDob() != null ? new java.sql.Date(pet.getPetDob().getTime()) : null);
            ps.setString(3, pet.getPetOrigin());
            ps.setString(4, pet.getPetGender());
            ps.setInt(5, pet.getPetAvailability());
            ps.setString(6, pet.getPetColor());
            ps.setInt(7, pet.getPetVaccination());
            ps.setString(8, pet.getPetDescription());
            ps.setDouble(9, pet.getPetPrice());
            ps.setInt(10, pet.getBreedId());
            ps.setInt(11, pet.getCreatedBy());
            ps.setInt(12, pet.getPetStatus());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new Exception("Creating pet failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new Exception("Creating pet failed, no ID obtained.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public boolean updatePetById(int id, Pet pet) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE PetTB\n"
                    + "SET \n"
                    + "    petName = ?,\n"
                    + "    petDob = ?,\n"
                    + "    petOrigin = ?,\n"
                    + "    petGender = ?,\n"
                    + "    petAvailability = ?,\n"
                    + "    petColor = ?,\n"
                    + "    petVaccination = ?,\n"
                    + "    petDescription = ?,\n"
                    + "    petPrice = ?,\n"
                    + "    breedId = ?,\n"
                    + "    createdBy = ?,\n"
                    + "    petStatus = ?\n"
                    + "WHERE petId = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pet.getPetName());
            ps.setDate(2, pet.getPetDob() != null ? new java.sql.Date(pet.getPetDob().getTime()) : null);
            ps.setString(3, pet.getPetOrigin());
            ps.setString(4, pet.getPetGender());
            ps.setInt(5, pet.getPetAvailability());
            ps.setString(6, pet.getPetColor());
            ps.setInt(7, pet.getPetVaccination());
            ps.setString(8, pet.getPetDescription());
            ps.setDouble(9, pet.getPetPrice());
            ps.setInt(10, pet.getBreedId());
            ps.setInt(11, pet.getCreatedBy());
            ps.setInt(12, pet.getPetStatus());
            ps.setInt(13, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updatePetStatusById(int id, int status) {
        String sql = "UPDATE PetTB SET petStatus = ? WHERE petId = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updatePetStatusById(List<Integer> petIds, int status) {
        if (petIds == null || petIds.isEmpty()) {
            return true;
        }

        String sql = "UPDATE PetTB SET petStatus = ? WHERE petId = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Integer id : petIds) {
                ps.setInt(1, status);
                ps.setInt(2, id);
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, "Error during batch pet status update.", ex);
            return false;
        }
    }

    public List<String> getImagePathsByPetId(int petId) {
        List<String> imagePaths = new ArrayList<>();

        String sql = "SELECT imagePath FROM PetImagePathTB WHERE petId = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, petId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    imagePaths.add(rs.getString("imagePath"));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PetImagePathDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return imagePaths;
    }

    public List<Pet> filterPetsForSeller(String searchKey,
            String availability,
            String species,
            String breedId,
            String gender,
            String vaccination,
            String petStatus) {
        List<Pet> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.*, b.breedName "
                + "FROM PetTB p JOIN BreedTB b ON p.breedId = b.breedId "
                + "WHERE 1=1 "
        );

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            if (searchKey.matches(".*[a-zA-Z].*")) {
                sql.append("AND p.petName LIKE ? ");
                params.add("%" + searchKey + "%");
            } else {
                try {
                    Integer.parseInt(searchKey);
                    sql.append("AND p.petId = ? ");
                    params.add(searchKey);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        if (availability != null && !availability.isEmpty()) {
            sql.append("AND p.petAvailability = ? ");
            params.add(availability);
        }
        if (species != null && !species.isEmpty()) {
            sql.append("AND b.breedSpecies = ? ");
            params.add(species);
        }
        if (breedId != null && !breedId.isEmpty()) {
            sql.append("AND p.breedId = ? ");
            params.add(breedId);
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append("AND p.petGender = ? ");
            params.add(gender);
        }
        if (vaccination != null && !vaccination.isEmpty()) {
            sql.append("AND p.petVaccination = ? ");
            params.add(vaccination);
        }
        if (petStatus != null && !petStatus.isEmpty()) {
            sql.append("AND p.petStatus = ? ");
            params.add(petStatus);
        }
        sql.append("ORDER BY p.petId ASC");

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pet pet = PetInfo(rs);
                    Breed breed = new Breed();
                    breed.setBreedId(rs.getInt("breedId"));
                    breed.setBreedName(rs.getString("breedName"));
                    pet.setBreed(breed);
                    pet.setImages(getImagePathsByPetId(pet.getPetId()));
                    list.add(pet);
                }
            }
        } catch (Exception ex) {

        }
        return list;
    }

    public boolean updatePetAvailabilityById(List<Integer> petIds, int status) {
        if (petIds == null || petIds.isEmpty()) {
            return true;
        }
        String sql = "UPDATE PetTB SET petAvailability = ? WHERE petId = ?";
        try (Connection conn = new DBContext().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, status);
                for (Integer id : petIds) {
                    ps.setInt(2, id);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
            return true;
        } catch (Exception ex) {

            return false;
        }
    }

    public List<Pet> getPetForOrderDetail(int orderId) {
        List<Pet> list = new ArrayList<>();
        String sql
                = "SELECT p.*, b.breedName, oc.priceAtOrder "
                + "FROM PetTB p "
                + "JOIN OrderContentTB oc ON p.petId = oc.petId "
                + "JOIN BreedTB b ON p.breedId = b.breedId "
                + "WHERE oc.orderId = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pet pet = PetInfo(rs);
                    Breed breed = new Breed();
                    breed.setBreedId(rs.getInt("breedId"));
                    breed.setBreedName(rs.getString("breedName"));
                    pet.setBreed(breed);
                    pet.setPriceAtOrder(rs.getDouble("priceAtOrder"));
                    list.add(pet);
                }
            }
        } catch (Exception ex) {

        }
        return list;
    }

    public List<Pet> getSimilarPets(int breedId, int excludedPetId) {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT * FROM PetTB WHERE petAvailability=1 AND petStatus=1 AND breedId=? AND petId!=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, breedId);
            ps.setInt(2, excludedPetId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setImages(getImagePathsByPetId(pet.getPetId()));
                list.add(pet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        String sql = "SELECT p.*, b.breedName FROM PetTB p JOIN BreedTB b ON p.breedId = b.breedId WHERE p.petId = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pet = PetInfo(rs);
                pet.setImages(getImagePathsByPetId(pet.getPetId()));
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                pet.setBreed(breed);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public List<String> getAllOrigins() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petOrigin FROM PetTB WHERE petOrigin IS NOT NULL";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("petOrigin"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public List<String> getAllGenders() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petGender FROM PetTB WHERE petGender IS NOT NULL";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("petGender"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public List<String> getAllColors() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petColor FROM PetTB WHERE petColor IS NOT NULL";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("petColor"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public List<String> getAllVaccinationStatus() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petVaccination FROM PetTB";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int status = rs.getInt("petVaccination");
                list.add(status == 1 ? "Đã tiêm" : "Chưa tiêm");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public List<Pet> filterPets(String breed, String species, String search, int num1, int num2, String sort,
            String gender, String color, String origin, String dobFrom, String dobTo, String vaccinationStatus) {

        List<Pet> listPet = new ArrayList<>();
        if (search == null || search.isEmpty()) {
            search = "%";
        } else {
            search = "%" + search + "%";
        }

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT p.*, b.breedName FROM PetTB p JOIN BreedTB b ON p.breedId = b.breedId "
                    + "WHERE p.petAvailability = 1 AND (p.petName LIKE ? OR b.breedName LIKE ?) AND p.petStatus=1";

            if (breed != null && !breed.isEmpty()) {
                sql += " AND b.breedId = ?";
            }
            if (species != null && !species.isEmpty()) {
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
            if (dobFrom != null && !dobFrom.isEmpty()) {
                sql += " AND p.petDob >= ?";
            }
            if (dobTo != null && !dobTo.isEmpty()) {
                sql += " AND p.petDob <= ?";
            }

            if (vaccinationStatus != null && !vaccinationStatus.isEmpty()) {
                sql += " AND p.petVaccination = ?";
            }

            if (sort != null && !sort.isEmpty()) {
                switch (sort) {

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
            if (breed != null && !breed.isEmpty()) {
                ps.setInt(i++, Integer.parseInt(breed));
            }
            if (species != null && !species.isEmpty()) {
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
            if (dobFrom != null && !dobFrom.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(dobFrom));
            }
            if (dobTo != null && !dobTo.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(dobTo));
            }

            if (vaccinationStatus != null && !vaccinationStatus.isEmpty()) {
                ps.setInt(i++, vaccinationStatus.equals("Đã tiêm") ? 1 : 0);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setBreedName(rs.getString("breedName"));

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

    public int getPendingOrderIdForPet(int petId) {
        String sql
                = "SELECT o.orderId FROM OrderContentTB oc "
                + "JOIN OrderTB o ON oc.orderId = o.orderId "
                + "WHERE oc.petId = ? AND o.orderStatus = 'Pending'";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, petId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("orderId");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE,
                    "Error checking pending order", ex);
        }
        return 0;
    }

    public List<Pet> get6PetNew() {
        List<Pet> listPet = new ArrayList<>();
        String sql = "SELECT TOP 6 *\n"
                + "FROM PetTB\n"
                + "WHERE petStatus = 1\n"
                + "ORDER BY petId DESC;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setPetId(rs.getInt("petId"));
                pet.setPetName(rs.getString("petName"));
                pet.setPetDob(rs.getDate("petDob"));
                pet.setPetGender(rs.getString("petGender"));
                pet.setPetOrigin(rs.getString("petOrigin"));
                pet.setPetColor(rs.getString("petColor"));
                pet.setPetDescription(rs.getString("petDescription"));
                pet.setPetPrice(rs.getDouble("petPrice"));
                pet.setBreedId(rs.getInt("breedId"));
                pet.setImages(getImagePathsByPetId(pet.getPetId()));

                listPet.add(pet);
            }
        } catch (Exception e) {
        }
        return listPet;
    }
}
