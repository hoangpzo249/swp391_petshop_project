/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author QuangAnh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Review;

/**
 *
 * @author QuangAnh
 */
public class ReviewDAO extends DBContext {

    public List<Review> getReviewsByAccId(int accId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT reviewId, accId, rating, reviewText, reviewDate FROM ShopReviewTB WHERE accId = ? ORDER BY reviewDate DESC";

        try (Connection conn = new DBContext().getConnection(); 
                PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setInt(1, accId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review r = new Review();
                r.setReviewId(rs.getInt("reviewId"));
                r.setAccId(rs.getInt("accId"));
                r.setRating(rs.getInt("rating"));
                r.setReviewText(rs.getString("reviewText"));
                r.setReviewDate(rs.getDate("reviewDate"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertReview(Review review) {
    String sql = "INSERT INTO ShopReviewTB (accId, rating, reviewText, reviewDate) VALUES (?, ?, ?, ?)";
    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, review.getAccId());
        ps.setInt(2, review.getRating());
        ps.setString(3, review.getReviewText());
        ps.setDate(4, new java.sql.Date(review.getReviewDate().getTime()));

        int rows = ps.executeUpdate();
        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

    public static void main(String[] args) {
        ReviewDAO view = new ReviewDAO();
    List<Review> list = view.getReviewsByAccId(1); // accId đúng với dữ liệu bạn đã nhập
    for (Review r : list) {
        System.out.println(r.getReviewId() + " | " + r.getRating() + " | " + r.getReviewText());
    }
    }
    
    
}
