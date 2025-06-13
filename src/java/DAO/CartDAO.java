/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    private Connection conn;

    public int getTotalCartItems(int accountId) {
        DBContext db = new DBContext();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) AS totalItems "
                       + "FROM CartContentTB cc "
                       + "JOIN CartTB c ON cc.cartId = c.cartId "
                       + "WHERE c.accId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalItems");
            }
        } catch (Exception ex) {
            // handle or log as needed
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return 0;
    }

    public boolean petInCart(int accId, int petId) {
        DBContext db = new DBContext();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT COUNT(*) AS total "
                       + "FROM CartTB c "
                       + "JOIN CartContentTB cc ON c.cartId = cc.cartId "
                       + "WHERE c.accId = ? AND cc.petId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            ps.setInt(2, petId);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt("total") > 0) {
                return true;
            }
        } catch (Exception ex) {
            // handle or log as needed
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return false;
    }

    public void addToPetCart(int accId, int petId) {
        DBContext db = new DBContext();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();

            int cartId = -1;
            String getCartSql = "SELECT cartId FROM CartTB WHERE accId = ?";
            ps = conn.prepareStatement(getCartSql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();
            if (rs.next()) {
                cartId = rs.getInt("cartId");
            }
            rs.close();
            ps.close();

            if (cartId == -1) {
                String insertSql = "INSERT INTO CartTB (accId) VALUES (?)";
                ps = conn.prepareStatement(insertSql);
                ps.setInt(1, accId);
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement(getCartSql);
                ps.setInt(1, accId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    cartId = rs.getInt("cartId");
                }
                rs.close();
                ps.close();
            }

            String insertContentSql = "INSERT INTO CartContentTB (cartId, petId, addedAt) VALUES (?, ?, GETDATE())";
            ps = conn.prepareStatement(insertContentSql);
            ps.setInt(1, cartId);
            ps.setInt(2, petId);
            ps.executeUpdate();

        } catch (Exception ex) {
            // handle or log as needed
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public void deleteFromPetCart(int accId, int petId) {
        DBContext db = new DBContext();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();
            String getCartIdSQL = "SELECT cartId FROM CartTB WHERE accId = ?";
            ps = conn.prepareStatement(getCartIdSQL);
            ps.setInt(1, accId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int cartId = rs.getInt("cartId");
                ps.close();
                String deleteSQL = "DELETE FROM CartContentTB WHERE cartId = ? AND petId = ?";
                ps = conn.prepareStatement(deleteSQL);
                ps.setInt(1, cartId);
                ps.setInt(2, petId);
                ps.executeUpdate();
            }

        } catch (Exception ex) {
            // handle or log as needed
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    public List<Cart> getCart(int accId) {
        List<Cart> list = new ArrayList<>();
        DBContext db = new DBContext();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT cc.petId, cc.addedAt "
                       + "FROM CartTB c "
                       + "JOIN CartContentTB cc ON c.cartId = cc.cartId "
                       + "WHERE c.accId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();
            while (rs.next()) {
                PetDAO p = new PetDAO();
                int petId = rs.getInt("petId");
                Cart cart = new Cart(accId, petId, 1, p.getPetById(petId).getPetPrice());
                list.add(cart);
            }
        } catch (Exception ex) {
            // handle or log as needed
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }
}
