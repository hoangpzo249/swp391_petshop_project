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
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO extends DBContext {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public DiscountDAO() {
        try {
            conn = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public boolean addDiscount(Discount d) {
        String sql = "INSERT INTO DiscountTB (discountCode, discountType, discountValue, description, validFrom, validTo, "
                + "minOrderAmount, maxUsage, usageCount, isActive, maxValue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, d.getDiscountCode());
            ps.setString(2, d.getDiscountType());
            ps.setDouble(3, d.getDiscountValue());
            if (d.getDescription() != null) {
                ps.setString(4, d.getDescription());
            } else {
                ps.setNull(4, Types.NVARCHAR);
            }
            ps.setDate(5, d.getValidFrom());
            ps.setDate(6, d.getValidTo());
            ps.setDouble(7, d.getMinOrderAmount());
            if (d.getMaxUsage() != null) {
                ps.setInt(8, d.getMaxUsage());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.setInt(9, d.getUsageCount());
            ps.setBoolean(10, d.isActive());
            if (d.getMaxValue() != null) {
                ps.setDouble(11, d.getMaxValue());
            } else {
                ps.setNull(11, Types.DOUBLE);
            }
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return false;
    }

    public List<Discount> getAllDiscounts() {
        List<Discount> list = new ArrayList<>();
        String sql = "SELECT * FROM DiscountTB ORDER BY discountId DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Discount d = new Discount();
                d.setDiscountId(rs.getInt("discountId"));
                d.setDiscountCode(rs.getString("discountCode"));
                d.setDiscountType(rs.getString("discountType"));
                d.setDiscountValue(rs.getDouble("discountValue"));
                d.setDescription(rs.getString("description"));
                d.setValidFrom(rs.getDate("validFrom"));
                d.setValidTo(rs.getDate("validTo"));
                d.setMinOrderAmount(rs.getDouble("minOrderAmount"));
                Object maxUsage = rs.getObject("maxUsage");
                d.setMaxUsage(maxUsage != null ? rs.getInt("maxUsage") : null);
                d.setUsageCount(rs.getInt("usageCount"));
                d.setActive(rs.getBoolean("isActive"));
                Object maxValue = rs.getObject("maxValue");
                d.setMaxValue(maxValue != null ? rs.getDouble("maxValue") : null);
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return list;
    }

    public Discount getDiscountById(int id) {
        String sql = "SELECT * FROM DiscountTB WHERE discountId = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Discount d = new Discount();
                d.setDiscountId(rs.getInt("discountId"));
                d.setDiscountCode(rs.getString("discountCode"));
                d.setDiscountType(rs.getString("discountType"));
                d.setDiscountValue(rs.getDouble("discountValue"));
                d.setDescription(rs.getString("description"));
                d.setValidFrom(rs.getDate("validFrom"));
                d.setValidTo(rs.getDate("validTo"));
                d.setMinOrderAmount(rs.getDouble("minOrderAmount"));
                Object maxUsage = rs.getObject("maxUsage");
                d.setMaxUsage(maxUsage != null ? rs.getInt("maxUsage") : null);
                d.setUsageCount(rs.getInt("usageCount"));
                d.setActive(rs.getBoolean("isActive"));
                Object maxValue = rs.getObject("maxValue");
                d.setMaxValue(maxValue != null ? rs.getDouble("maxValue") : null);
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return null;
    }

    public boolean updateDiscount(Discount d) {
        String sql = "UPDATE DiscountTB SET discountCode=?, discountType=?, discountValue=?, description=?, validFrom=?, validTo=?, "
                + "minOrderAmount=?, maxUsage=?, usageCount=?, isActive=?, maxValue=? WHERE discountId=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, d.getDiscountCode());
            ps.setString(2, d.getDiscountType());
            ps.setDouble(3, d.getDiscountValue());
            ps.setString(4, d.getDescription());
            ps.setDate(5, d.getValidFrom());
            ps.setDate(6, d.getValidTo());
            ps.setDouble(7, d.getMinOrderAmount());
            if (d.getMaxUsage() != null) {
                ps.setInt(8, d.getMaxUsage());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.setInt(9, d.getUsageCount());
            ps.setBoolean(10, d.isActive());
            if (d.getMaxValue() != null) {
                ps.setDouble(11, d.getMaxValue());
            } else {
                ps.setNull(11, Types.DOUBLE);
            }
            ps.setInt(12, d.getDiscountId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return false;
    }

    public boolean updateStatus(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String selectSql = "SELECT isActive FROM DiscountTB WHERE discountId = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                boolean currentStatus = rs.getBoolean("isActive");
                String updateSql = "UPDATE DiscountTB SET isActive = ? WHERE discountId = ?";
                ps.close();
                ps = conn.prepareStatement(updateSql);
                ps.setBoolean(1, !currentStatus);
                ps.setInt(2, id);
                return ps.executeUpdate() == 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return false;
    }

    public List<Discount> getFilteredDiscounts(String searchKey, String type, String status, String fromDate, String toDate, String sortBy) {
        List<Discount> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM DiscountTB WHERE 1=1 ";

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql += "AND (discountCode LIKE ? OR description LIKE ?) ";
        }
        if (type != null && !type.isEmpty()) {
            sql += "AND discountType = ? ";
        }
        if (status != null && !status.isEmpty()) {
            sql += "AND isActive = ? ";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql += "AND validTo >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql += "AND validTo <= ? ";
        }

        if ("validTo_asc".equals(sortBy)) {
            sql += "ORDER BY validTo ASC ";
        } else if ("validTo_desc".equals(sortBy)) {
            sql += "ORDER BY validTo DESC ";
        } else if ("usageCount_asc".equals(sortBy)) {
            sql += "ORDER BY usageCount ASC ";
        } else if ("usageCount_desc".equals(sortBy)) {
            sql += "ORDER BY usageCount DESC ";
        }

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            int i = 1;

            if (searchKey != null && !searchKey.trim().isEmpty()) {
                ps.setString(i++, "%" + searchKey.trim() + "%");
                ps.setString(i++, "%" + searchKey.trim() + "%");
            }
            if (type != null && !type.isEmpty()) {
                ps.setString(i++, type);
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(i++, "1".equals(status));
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(toDate));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Discount d = new Discount();
                d.setDiscountId(rs.getInt("discountId"));
                d.setDiscountCode(rs.getString("discountCode"));
                d.setDiscountType(rs.getString("discountType"));
                d.setDiscountValue(rs.getDouble("discountValue"));
                d.setDescription(rs.getString("description"));
                d.setValidFrom(rs.getDate("validFrom"));
                d.setValidTo(rs.getDate("validTo"));
                d.setMinOrderAmount(rs.getDouble("minOrderAmount"));
                d.setMaxUsage(rs.getObject("maxUsage") != null ? rs.getInt("maxUsage") : null);
                d.setUsageCount(rs.getInt("usageCount"));
                d.setActive(rs.getBoolean("isActive"));
                d.setMaxValue(rs.getObject("maxValue") != null ? rs.getDouble("maxValue") : null);
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return list;
    }

    public List<Discount> getFilteredDiscountsPaging(String searchKey, String type, String status, String fromDate, String toDate, String sortBy, int page, int pageSize) {
        List<Discount> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM DiscountTB WHERE 1=1 ";
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql += "AND (discountCode LIKE ? OR description LIKE ?) ";
        }
        if (type != null && !type.isEmpty()) {
            sql += "AND discountType = ? ";
        }
        if (status != null && !status.isEmpty()) {
            sql += "AND isActive = ? ";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql += "AND validTo >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql += "AND validTo <= ? ";
        }

        if (sortBy == null || sortBy.isEmpty()) {
            sql += "ORDER BY discountId DESC ";
        } else {
            switch (sortBy) {
                case "validTo_asc":
                    sql += "ORDER BY validTo ASC ";
                    break;
                case "validTo_desc":
                    sql += "ORDER BY validTo DESC ";
                    break;
                case "usageCount_asc":
                    sql += "ORDER BY usageCount ASC ";
                    break;
                case "usageCount_desc":
                    sql += "ORDER BY usageCount DESC ";
                    break;
                default:
                    sql += "ORDER BY discountId DESC ";
            }
        }

        sql += "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            int i = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                ps.setString(i++, "%" + searchKey.trim() + "%");
                ps.setString(i++, "%" + searchKey.trim() + "%");
            }
            if (type != null && !type.isEmpty()) {
                ps.setString(i++, type);
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(i++, "1".equals(status));
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(toDate));
            }

            ps.setInt(i++, (page - 1) * pageSize);
            ps.setInt(i, pageSize);

            rs = ps.executeQuery();
            while (rs.next()) {
                Discount d = new Discount();
                d.setDiscountId(rs.getInt("discountId"));
                d.setDiscountCode(rs.getString("discountCode"));
                d.setDiscountType(rs.getString("discountType"));
                d.setDiscountValue(rs.getDouble("discountValue"));
                d.setDescription(rs.getString("description"));
                d.setValidFrom(rs.getDate("validFrom"));
                d.setValidTo(rs.getDate("validTo"));
                d.setMinOrderAmount(rs.getDouble("minOrderAmount"));
                d.setMaxUsage(rs.getObject("maxUsage") != null ? rs.getInt("maxUsage") : null);
                d.setUsageCount(rs.getInt("usageCount"));
                d.setActive(rs.getBoolean("isActive"));
                d.setMaxValue(rs.getObject("maxValue") != null ? rs.getDouble("maxValue") : null);
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        return list;
    }

    public int countFilteredDiscounts(String searchKey, String type, String status, String fromDate, String toDate) {
        String sql = "SELECT COUNT(*) FROM DiscountTB WHERE 1=1 ";
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            sql += "AND (discountCode LIKE ? OR description LIKE ?) ";
        }
        if (type != null && !type.isEmpty()) {
            sql += "AND discountType = ? ";
        }
        if (status != null && !status.isEmpty()) {
            sql += "AND isActive = ? ";
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql += "AND validTo >= ? ";
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql += "AND validTo <= ? ";
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                ps.setString(i++, "%" + searchKey.trim() + "%");
                ps.setString(i++, "%" + searchKey.trim() + "%");
            }
            if (type != null && !type.isEmpty()) {
                ps.setString(i++, type);
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(i++, "1".equals(status));
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(fromDate));
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setDate(i++, java.sql.Date.valueOf(toDate));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean resetUsageCountById(int discountId) {
        String sql = "UPDATE DiscountTB SET usageCount = 0 WHERE discountId = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, discountId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

}
