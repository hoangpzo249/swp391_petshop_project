/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public int getTotalCartItems(int accountId) {
        DBContext db = new DBContext();
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
        return 0;
    }

    public boolean petInCart(int accId, int petId) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT 1 FROM CartTB c JOIN CartContentTB cc ON c.cartId = cc.cartId "
                    + "WHERE c.accId = ? AND cc.petId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);
            ps.setInt(2, petId);
            rs = ps.executeQuery();
            return rs.next();
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
        return false;
    }

    public void addToPetCart(int accId, int petId) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();

            String getCartIdSql = "SELECT cartId FROM CartTB WHERE accId = ?";
            ps = conn.prepareStatement(getCartIdSql);
            ps.setInt(1, accId);
            rs = ps.executeQuery();

            int cartId = -1;
            if (rs.next()) {
                cartId = rs.getInt("cartId");
            } else {

                rs.close();
                ps.close();
                String getNextIdSql = "SELECT ISNULL(MAX(cartId), 0) + 1 AS nextId FROM CartTB";
                ps = conn.prepareStatement(getNextIdSql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    cartId = rs.getInt("nextId");
                }
                rs.close();
                ps.close();

                String createCartSql = "INSERT INTO CartTB (cartId, accId) VALUES (?, ?)";
                ps = conn.prepareStatement(createCartSql);
                ps.setInt(1, cartId);
                ps.setInt(2, accId);
                ps.executeUpdate();
                ps.close();
            }

            String insertSql = "INSERT INTO CartContentTB (cartId, petId, addedAt) VALUES (?, ?, GETDATE())";
            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, cartId);
            ps.setInt(2, petId);
            ps.executeUpdate();

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
    }

    public List<Cart> getCart(int accId) {
        List<Cart> list = new ArrayList<>();
        DBContext db = new DBContext();
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
                Cart cart = new Cart(accId, rs.getInt("petId"), null, 1, 0);
                list.add(cart);
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
        return list;
    }
}
