/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.PetImage;
import java.sql.Timestamp;
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

    public boolean changeImageById(int imageId, byte[] imageData) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE PetImageTB SET imageData = ? WHERE imageId = ?";
            ps = conn.prepareStatement(sql);
            ps.setBytes(1, imageData);
            ps.setInt(2, imageId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public boolean deleteImageById(int petId, int imageId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "DELETE FROM PetImageTB WHERE imageId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, imageId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public List<PetImage> getPetImagesById(int petId) {
        List<PetImage> imageList = new ArrayList<>();
        DBContext db = new DBContext();
        String sql = "SELECT * FROM PetImageTB WHERE petId = ?";

        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);
            rs = ps.executeQuery();

            while (rs.next()) {
                PetImage image = new PetImage();

                image.setImageId(rs.getInt("imageId"));
                image.setPetId(rs.getInt("petId"));
                image.setImageData(rs.getBytes("imageData"));

                Timestamp timestamp = rs.getTimestamp("uploadedAt");
                if (timestamp != null) {
                    image.setUploadedAt(timestamp.toLocalDateTime());
                }

                imageList.add(image);
            }
        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, "Error retrieving pet images for petId: " + petId, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, "Error closing database resources", e);
            }
        }
        return imageList;
    }

    public boolean addImage(int petId, List<byte[]> imageDatas) {
        DBContext db = new DBContext();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = db.getConnection();
            String sql = "INSERT INTO PetImageTB (petId, imageData) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);

            for (byte[] data : imageDatas) {
                ps.setInt(1, petId);
                ps.setBytes(2, data);
                ps.addBatch();
            }

            ps.executeBatch();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public int countImagesById(int petId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) AS imageCount FROM PetImageTB WHERE petId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, petId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                Logger.getLogger(PetImageDAO.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return 0;
    }
}