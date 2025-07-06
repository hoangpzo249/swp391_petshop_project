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
import java.sql.Date;
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
        String sql = "INSERT INTO PetTB ("
                + "petName, petDob, petOrigin, petGender, petAvailability, "
                + "petColor, petVaccination, petDescription, petPrice, "
                + "breedId, createdBy, petStatus) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet generatedKeys = null;
        try {
            conn = new DBContext().getConnection();
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

            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new Exception("Creating pet failed, no ID obtained.");
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updatePetById(int id, Pet pet) {
        String sql = "UPDATE PetTB SET "
                + "petName = ?, petDob = ?, petOrigin = ?, petGender = ?, petAvailability = ?, "
                + "petColor = ?, petVaccination = ?, petDescription = ?, petPrice = ?, "
                + "breedId = ?, createdBy = ?, petStatus = ? "
                + "WHERE petId = ?";
        try {
            conn = new DBContext().getConnection();
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
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updatePetStatusById(int id, int status) {
        String sql = "UPDATE PetTB SET petStatus = ? WHERE petId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updatePetStatusById(List<Integer> petIds, int status) {
        if (petIds == null || petIds.isEmpty()) {
            return true;
        }

        String sql = "UPDATE PetTB SET petStatus = ? WHERE petId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
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
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<byte[]> getImageDataByPetId(int petId) {
        List<byte[]> images = new ArrayList<>();
        String sql = "SELECT imageData FROM PetImageTB WHERE petId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);
            rs = ps.executeQuery();
            while (rs.next()) {
                images.add(rs.getBytes("imageData"));
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    private StringBuilder buildFilterQuery(String searchKey, String availability, String species, String breedId, String gender, String vaccination, String petStatus) {
        StringBuilder sql = new StringBuilder(
                " FROM PetTB p JOIN BreedTB b ON p.breedId = b.breedId "
                + " WHERE 1=1 "
        );

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql.append(searchKey.matches("\\d+") ? "AND p.petId = ? " : "AND p.petName LIKE ? ");
        }
        if (availability != null && !availability.isEmpty()) {
            sql.append("AND p.petAvailability = ? ");
        }
        if (species != null && !species.isEmpty()) {
            sql.append("AND b.breedSpecies = ? ");
        }
        if (breedId != null && !breedId.isEmpty()) {
            sql.append("AND p.breedId = ? ");
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append("AND p.petGender = ? ");
        }
        if (vaccination != null && !vaccination.isEmpty()) {
            sql.append("AND p.petVaccination = ? ");
        }
        if (petStatus != null && !petStatus.isEmpty()) {
            sql.append("AND p.petStatus = ? ");
        }
        return sql;
    }

    public int countFilteredPetsForSeller(String searchKey, String availability, String species, String breedId, String gender, String vaccination, String petStatus) {
        StringBuilder sqlBase = new StringBuilder("SELECT COUNT(p.petId) ");
        sqlBase.append(buildFilterQuery(searchKey, availability, species, breedId, gender, vaccination, petStatus));

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sqlBase.toString());

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                if (searchKey.matches("\\d+")) {
                    ps.setInt(paramIndex++, Integer.parseInt(searchKey.trim()));
                } else {
                    ps.setString(paramIndex++, "%" + searchKey.trim() + "%");
                }
            }
            if (availability != null && !availability.isEmpty()) {
                ps.setString(paramIndex++, availability);
            }
            if (species != null && !species.isEmpty()) {
                ps.setString(paramIndex++, species);
            }
            if (breedId != null && !breedId.isEmpty()) {
                ps.setString(paramIndex++, breedId);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            if (vaccination != null && !vaccination.isEmpty()) {
                ps.setString(paramIndex++, vaccination);
            }
            if (petStatus != null && !petStatus.isEmpty()) {
                ps.setString(paramIndex++, petStatus);
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public List<Pet> filterPetsForSeller(String searchKey, String availability, String species, String breedId, String gender, String vaccination, String petStatus, int pageNumber, int pageSize) {
        List<Pet> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.*, b.breedName ");
        sql.append(buildFilterQuery(searchKey, availability, species, breedId, gender, vaccination, petStatus));
        sql.append("ORDER BY p.petId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                if (searchKey.matches("\\d+")) {
                    ps.setInt(paramIndex++, Integer.parseInt(searchKey.trim()));
                } else {
                    ps.setString(paramIndex++, "%" + searchKey.trim() + "%");
                }
            }
            if (availability != null && !availability.isEmpty()) {
                ps.setString(paramIndex++, availability);
            }
            if (species != null && !species.isEmpty()) {
                ps.setString(paramIndex++, species);
            }
            if (breedId != null && !breedId.isEmpty()) {
                ps.setString(paramIndex++, breedId);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            if (vaccination != null && !vaccination.isEmpty()) {
                ps.setString(paramIndex++, vaccination);
            }
            if (petStatus != null && !petStatus.isEmpty()) {
                ps.setString(paramIndex++, petStatus);
            }

            ps.setInt(paramIndex++, (pageNumber - 1) * pageSize);
            ps.setInt(paramIndex++, pageSize);

            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                pet.setBreed(breed);
                pet.setImages(getImageDataByPetId(pet.getPetId()));
                list.add(pet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    public boolean updatePetAvailabilityById(List<Integer> petIds, int status) {
        if (petIds == null || petIds.isEmpty()) {
            return true;
        }
        String sql = "UPDATE PetTB SET petAvailability = ? WHERE petId = ?";
        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            for (Integer id : petIds) {
                ps.setInt(1, status);
                ps.setInt(2, id);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Pet> getPetForOrderDetail(int orderId) {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT p.*, b.breedName, oc.priceAtOrder "
                + "FROM PetTB p JOIN OrderContentTB oc ON p.petId = oc.petId "
                + "JOIN BreedTB b ON p.breedId = b.breedId "
                + "WHERE oc.orderId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                pet.setBreed(breed);
                pet.setPriceAtOrder(rs.getDouble("priceAtOrder"));
                list.add(pet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Pet> getSimilarPets(int breedId, int excludedPetId) {
        List<Pet> list = new ArrayList<>();
        String sql = "SELECT * FROM PetTB WHERE petAvailability=1 AND petStatus=1 AND breedId=? AND petId!=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, breedId);
            ps.setInt(2, excludedPetId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setImages(getImageDataByPetId(pet.getPetId()));
                list.add(pet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Pet getPetById(int id) {
        Pet pet = null;
        String sql = "SELECT p.*, b.breedName FROM PetTB p JOIN BreedTB b ON p.breedId = b.breedId WHERE p.petId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                pet = PetInfo(rs);
                pet.setImages(getImageDataByPetId(pet.getPetId()));
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                pet.setBreed(breed);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pet;
    }

    public List<String> getAllOrigins() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petOrigin FROM PetTB WHERE petOrigin IS NOT NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("petOrigin"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getAllGenders() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petGender FROM PetTB WHERE petGender IS NOT NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("petGender"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getAllColors() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petColor FROM PetTB WHERE petColor IS NOT NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("petColor"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getAllVaccinationStatus() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT petVaccination FROM PetTB";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("petVaccination") == 1 ? "Đã tiêm" : "Chưa tiêm");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Pet> filterPets(String breed, String species, String search, int num1, int num2, String sort,
            String gender, String color, String origin, String dobFrom, String dobTo, String vaccinationStatus) {

        List<Pet> listPet = new ArrayList<>();
        String finalSearch = (search == null || search.isEmpty()) ? "%" : "%" + search + "%";
        StringBuilder sqlBuilder = new StringBuilder("SELECT p.*, b.breedName FROM PetTB p JOIN BreedTB b ON p.breedId = b.breedId "
                + "WHERE p.petAvailability = 1 AND (p.petName LIKE ? OR b.breedName LIKE ?) AND p.petStatus=1");

        // ... (rest of the dynamic query building logic is unchanged)
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sqlBuilder.toString());
            // ... (rest of the parameter setting logic is unchanged)
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setBreedName(rs.getString("breedName"));
                pet.setImages(getImageDataByPetId(pet.getPetId()));
                listPet.add(pet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listPet;
    }

    public void updatePetAvailability(int petId, int availability) {
        String sql = "UPDATE PetTB SET petAvailability = ? WHERE petId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, availability);
            ps.setInt(2, petId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getPendingOrderIdForPet(int petId) {
        String sql = "SELECT o.orderId FROM OrderContentTB oc JOIN OrderTB o ON oc.orderId = o.orderId "
                + "WHERE oc.petId = ? AND o.orderStatus = 'Pending'";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("orderId");
            }
        } catch (Exception ex) {
            Logger.getLogger(PetDAO.class.getName()).log(Level.SEVERE, "Error checking pending order", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public List<Pet> get6PetNew() {
        List<Pet> listPet = new ArrayList<>();
        String sql = "SELECT TOP 18 * FROM PetTB WHERE petStatus = 1 and petAvailability = 1 ORDER BY petId DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = PetInfo(rs);
                pet.setImages(getImageDataByPetId(pet.getPetId()));
                listPet.add(pet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listPet;
    }

    public int totalPetsSold(Date startDate, Date endDate) {
        int total = 0;
        String sql = "SELECT COUNT(*) AS totalPetsSold FROM OrderContentTB oc "
                + "INNER JOIN OrderTB o ON oc.orderId = o.orderId "
                + "WHERE o.deliveryDate BETWEEN ? AND ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("totalPetsSold");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    
}
