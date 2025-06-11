/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Lenovo
 */
import Models.Breed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BreedDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public List<Breed> displayDogBreeds() {
        DBContext db = new DBContext();
        List<Breed> listBreed = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT breedId, breedName, breedSpecies, breedStatus, breedImage "
                    + "FROM BreedTB "
                    + "WHERE breedSpecies = N'Dog' "
                    + "ORDER BY breedName ASC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("breedId");
                String name = rs.getString("breedName");
                String species = rs.getString("breedSpecies");
                boolean status = rs.getBoolean("breedStatus");
                byte[] imageBytes = rs.getBytes("breedImage");

                listBreed.add(new Breed(id, name, species, status, imageBytes));
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
        return listBreed;
    }

    public List<Breed> displayCatBreeds() {
        DBContext db = new DBContext();
        List<Breed> listBreed = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT breedId, breedName, breedSpecies, breedStatus, breedImage "
                    + "FROM BreedTB "
                    + "WHERE breedSpecies = N'Cat' "
                    + "ORDER BY breedName ASC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("breedId");
                String name = rs.getString("breedName");
                String species = rs.getString("breedSpecies");
                boolean status = rs.getBoolean("breedStatus");
                byte[] imageBytes = rs.getBytes("breedImage");

                listBreed.add(new Breed(id, name, species, status, imageBytes));
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
        return listBreed;
    }

    public List<Breed> getAllBreeds() {
        List<Breed> list = new ArrayList<>();
        DBContext db = new DBContext();
        String sql = "SELECT breedId, breedName FROM BreedTB WHERE breedStatus = 1 ORDER BY breedName ASC";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                list.add(breed);
            }
        } catch (Exception ex) {
        } finally {
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
        }
        return list;
    }

    public List<String> getAllSpecies() {
        List<String> list = new ArrayList<>();
        DBContext db = new DBContext();
        String sql = "SELECT DISTINCT breedSpecies FROM BreedTB WHERE breedStatus = 1 ORDER BY breedSpecies ASC";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("breedSpecies"));
            }
        } catch (Exception ex) {
        } finally {
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
        }
        return list;
    }
}
