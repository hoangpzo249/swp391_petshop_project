/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class PetImagePathDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public boolean addImage(int petId, List<String> imageURLs) {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = db.getConnection();
            String sql = "INSERT INTO PetImagePathTB (petId, imagePath) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);

            for (String url : imageURLs) {
                ps.setInt(1, petId);
                ps.setString(2, url);
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(PetImagePathDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                Logger.getLogger(PetImagePathDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
