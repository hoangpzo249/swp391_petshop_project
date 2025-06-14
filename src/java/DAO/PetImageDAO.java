/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Pet;
import Models.PetImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class PetImageDAO {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private PetImage PetImageInfo(ResultSet rs) throws Exception {
        return new PetImage(
                rs.getInt(1),
                rs.getInt(2),
                rs.getBytes(3),
                rs.getTimestamp(4).toLocalDateTime()
        );
    }

    public boolean addPetImage(int idd, List<byte[]> petImage) {
        DBContext db = new DBContext();
        String sql = "INSERT INTO PetImageTB (petId, imageData) VALUES (?, ?)";

        try (Connection conn = db.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (byte[] image : petImage) {
                ps.setInt(1, idd);
                ps.setBytes(2, image);
                ps.addBatch();
            }

            int[] results = ps.executeBatch();

            for (int result : results) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    return false;
                }
            }

            return true;

        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<PetImage> getPetImagesById(int id) {
        DBContext db = new DBContext();
        List<PetImage> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM PetImageTB\n"
                    + "WHERE petId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(PetImageInfo(rs));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
