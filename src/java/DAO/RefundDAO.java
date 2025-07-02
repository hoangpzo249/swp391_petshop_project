/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import Models.Refund;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;

public class RefundDAO {

    private Connection conn;

    public boolean insertRefund(Refund refund) {
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO RefundTB (orderId, bankAccountNumber, bankName, accountHolderName, "
                    + "refundAmount, proofImage, refundStatus, refundRequestDate,rejectReason) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE(),?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, refund.getOrderId());
            ps.setString(2, refund.getBankAccountNumber());
            ps.setString(3, refund.getBankName());
            ps.setString(4, refund.getAccountHolderName());
            ps.setDouble(5, refund.getRefundAmount());
            ps.setBytes(6, refund.getProofImage());
            ps.setString(7, refund.getRefundStatus());
            ps.setString(8, refund.getRejectReason());
            int rows = ps.executeUpdate();
            return rows > 0;

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

    public List<Refund> getAllRefunds() {
        List<Refund> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM RefundTB";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Refund r = new Refund();
                r.setRefundId(rs.getInt("refundId"));
                r.setOrderId(rs.getInt("orderId"));
                r.setBankAccountNumber(rs.getString("bankAccountNumber"));
                r.setBankName(rs.getString("bankName"));
                r.setAccountHolderName(rs.getString("accountHolderName"));
                r.setRefundAmount(rs.getDouble("refundAmount"));
                r.setProofImage(rs.getBytes("proofImage"));
                r.setRefundStatus(rs.getString("refundStatus"));
                r.setRefundRequestDate(rs.getTimestamp("refundRequestDate"));
                r.setRefundProcessedDate(rs.getTimestamp("refundProcessedDate"));
                r.setRejectReason(rs.getString("rejectReason"));
                r.setProofRefundedImage(rs.getBytes("proofRefundedImage"));

                list.add(r);
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

    public Refund getRefundById(int id) {
        Refund r = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM RefundTB WHERE refundId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                r = new Refund();
                r.setRefundId(rs.getInt("refundId"));
                r.setOrderId(rs.getInt("orderId"));
                r.setBankAccountNumber(rs.getString("bankAccountNumber"));
                r.setBankName(rs.getString("bankName"));
                r.setAccountHolderName(rs.getString("accountHolderName"));
                r.setRefundAmount(rs.getDouble("refundAmount"));
                r.setProofImage(rs.getBytes("proofImage"));
                r.setRefundStatus(rs.getString("refundStatus"));
                r.setRefundRequestDate(rs.getTimestamp("refundRequestDate"));
                r.setRefundProcessedDate(rs.getTimestamp("refundProcessedDate"));
                r.setRejectReason(rs.getString("rejectReason"));
                r.setProofRefundedImage(rs.getBytes("proofRefundedImage"));

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
        return r;
    }

    public boolean updateRefundSeller(Refund refund) {
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE RefundTB SET "
                    + "orderId = ?, "
                    + "bankAccountNumber = ?, "
                    + "bankName = ?, "
                    + "accountHolderName = ?, "
                    + "refundAmount = ?, "
                    + "proofImage = ? "
                    + "WHERE refundId = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, refund.getOrderId());
            ps.setString(2, refund.getBankAccountNumber());
            ps.setString(3, refund.getBankName());
            ps.setString(4, refund.getAccountHolderName());
            ps.setDouble(5, refund.getRefundAmount());
            ps.setBytes(6, refund.getProofImage());
            ps.setInt(7, refund.getRefundId());
            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;

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

    public boolean updateRefundManager(Refund refund) {
        PreparedStatement ps = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE RefundTB SET "
                    + "refundStatus = ?, "
                    + "refundProcessedDate = ?, "
                    + "proofRefundedImage = ?, "
                    + "rejectReason = ? "
                    + "WHERE refundId = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, refund.getRefundStatus());
            ps.setTimestamp(2, refund.getRefundProcessedDate());
            ps.setBytes(3, refund.getProofRefundedImage());
            ps.setString(4, refund.getRejectReason());
            ps.setInt(5, refund.getRefundId());
            int updatedRows = ps.executeUpdate();
            return updatedRows > 0;

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

    public List<Refund> getFilteredRefunds(String statusFilter, String sortByDate) {
        List<Refund> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM RefundTB";

            if (statusFilter != null && !statusFilter.isEmpty()) {
                sql += " WHERE refundStatus = ?";
            }

            if ("asc".equalsIgnoreCase(sortByDate)) {
                sql += " ORDER BY refundRequestDate ASC";
            } else {
                sql += " ORDER BY refundRequestDate DESC";
            }

            ps = conn.prepareStatement(sql);

            if (statusFilter != null && !statusFilter.isEmpty()) {
                ps.setString(1, statusFilter);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Refund r = new Refund();
                r.setRefundId(rs.getInt("refundId"));
                r.setOrderId(rs.getInt("orderId"));
                r.setAccountHolderName(rs.getString("accountHolderName"));
                r.setBankName(rs.getString("bankName"));
                r.setBankAccountNumber(rs.getString("bankAccountNumber"));
                r.setRefundAmount(rs.getDouble("refundAmount"));
                r.setRefundStatus(rs.getString("refundStatus"));
                r.setRefundRequestDate(rs.getTimestamp("refundRequestDate"));
                r.setRefundProcessedDate(rs.getTimestamp("refundProcessedDate"));
                r.setProofImage(rs.getBytes("proofImage"));
                r.setRejectReason(rs.getString("rejectReason"));
                r.setProofRefundedImage(rs.getBytes("proofRefundedImage"));
                list.add(r);
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

    public Refund getRefundByOrderId(int orderId) {
        Refund r = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM RefundTB WHERE orderId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                r = new Refund();
                r.setRefundId(rs.getInt("refundId"));
                r.setOrderId(rs.getInt("orderId"));
                r.setBankAccountNumber(rs.getString("bankAccountNumber"));
                r.setBankName(rs.getString("bankName"));
                r.setAccountHolderName(rs.getString("accountHolderName"));
                r.setRefundAmount(rs.getDouble("refundAmount"));
                r.setProofImage(rs.getBytes("proofImage"));
                r.setRefundStatus(rs.getString("refundStatus"));
                r.setRefundRequestDate(rs.getTimestamp("refundRequestDate"));
                r.setRefundProcessedDate(rs.getTimestamp("refundProcessedDate"));
                r.setRejectReason(rs.getString("rejectReason"));
                r.setProofRefundedImage(rs.getBytes("proofRefundedImage"));
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
        return r;
    }

}
