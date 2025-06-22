/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.*;
import Models.Discount;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiscountDAO extends DBContext {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public Discount getActiveDiscountByCode(String code) {
        DBContext db = new DBContext();
        Discount d = null;
        String sql = "SELECT * FROM DiscountTB WHERE discountCode = ?";

        try {

            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, code.trim());
            rs = ps.executeQuery();

            if (rs.next()) {
                d = new Discount();
                d.setDiscountId(rs.getInt("discountId"));
                d.setDiscountCode(rs.getString("discountCode"));
                d.setDiscountType(rs.getString("discountType"));
                d.setDiscountValue(rs.getDouble("discountValue"));
                d.setDescription(rs.getString("description"));
                d.setValidFrom(rs.getDate("validFrom"));
                d.setValidTo(rs.getDate("validTo"));
                d.setMinOrderAmount(rs.getDouble("minOrderAmount"));
                d.setMaxUsage(rs.getInt("maxUsage"));
                d.setUsageCount(rs.getInt("usageCount"));
                d.setActive(rs.getBoolean("isActive"));
                d.setMaxValue(rs.getDouble("maxValue"));
            }
        } catch (Exception e) {

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

        return d;
    }

    public void increaseUsageCount(String discountCode) {
        String sql = "UPDATE DiscountTB SET usageCount = usageCount + 1 WHERE discountCode = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, discountCode.trim().toUpperCase());
            ps.executeUpdate();
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
}
