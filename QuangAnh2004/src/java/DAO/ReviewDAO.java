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
public class ReviewDAO extends DBContext{
    public List<Review> getAllReviews() {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM ShopReviewTB";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
}