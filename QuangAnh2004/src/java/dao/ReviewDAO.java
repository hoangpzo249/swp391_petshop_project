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
import model.Review;
/**
 *
 * @author QuangAnh
 */
public class ReviewDAO extends DBContext{
    public void insertReview(Review review){
        String sql = "INSERT INTO ShopReviewTB (accId, rating, reviewText, reviewDate) VALUES (?, ?, ?, GETDATE())";
        try (PreparedStatement stm = c.prepareStatement(sql)){
            
        stm.setInt(1, review.getAccId());
        stm.setInt(2, review.getRating());
        stm.setString(3, review.getReviewText());
        stm.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
    }
    }
    public List<Review> getAllReviews() throws SQLException {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM ShopReviewTB ORDER BY reviewDate DESC";
        try (PreparedStatement stm = c.prepareStatement(sql)){
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Review r = new Review();
            r.setReviewId(rs.getInt("reviewId"));
            r.setAccId(rs.getInt("accId"));
            r.setRating(rs.getInt("rating"));
            r.setReviewText(rs.getString("reviewText"));
            r.setReviewDate(rs.getTimestamp("reviewDate"));
            list.add(r);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    }



