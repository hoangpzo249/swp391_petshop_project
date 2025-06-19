/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author AD
 */


import Models.BlogPost;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class BlogPostDAO {

   
    public List<BlogPost> getAllBlogs() {
        List<BlogPost> list = new ArrayList<>();
        String query = "SELECT * FROM BlogPostTB ORDER BY createdDate DESC";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BlogPost blog = new BlogPost(
                    rs.getInt("blogId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getInt("authorId"),
                    rs.getTimestamp("createdDate"),
                    rs.getString("status"),
                    rs.getString("featuredImage")
                );
                list.add(blog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public void addBlog(BlogPost blog) {
        String query = "INSERT INTO BlogPostTB (title, content, authorId, createdDate, status, featuredImage) " +
                       "VALUES (?, ?, ?, GETDATE(), ?, ?)";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setInt(3, blog.getAuthorId());
            ps.setString(4, blog.getStatus());
            ps.setString(5, blog.getFeaturedImage());
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi thêm blog: " + e.getMessage());
        e.printStackTrace(); // <-- Quan trọng để hiện lỗi!
        }
    }

   
    public void updateBlog(BlogPost blog) {
        String query = "UPDATE BlogPostTB SET title = ?, content = ?, status = ?, featuredImage = ? WHERE blogId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setString(3, blog.getStatus());
            ps.setString(4, blog.getFeaturedImage());
            ps.setInt(5, blog.getBlogId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public BlogPost getBlogById(int blogId) {
        String query = "SELECT * FROM BlogPostTB WHERE blogId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BlogPost(
                    rs.getInt("blogId"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getInt("authorId"),
                    rs.getTimestamp("createdDate"),
                    rs.getString("status"),
                    rs.getString("featuredImage")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public void updateStatus(int blogId, String status) {
        String query = "UPDATE BlogPostTB SET status = ? WHERE blogId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, blogId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    public void deleteBlog(int blogId) {
        String query = "DELETE FROM BlogPostTB WHERE blogId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, blogId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

