/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Breed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 *
 * @author Lenovo
 */
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
                double purchases = rs.getDouble("breedStatus");
                byte[] imageBytes = rs.getBytes("breedImage");

                listBreed.add(new Breed(id, name, species, purchases, imageBytes));
            }
        } catch (Exception ex) {
            
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
                double purchases = rs.getDouble("breedStatus");
                byte[] imageBytes = rs.getBytes("breedImage");

                listBreed.add(new Breed(id, name, species, purchases, imageBytes));
            }
        } catch (Exception ex) {
          
        }
        return listBreed;
    }

   

}
