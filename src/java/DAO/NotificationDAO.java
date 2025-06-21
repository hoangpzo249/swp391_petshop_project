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
import Model.Notification;
/**
 *
 * @author QuangAnh
 */
public class NotificationDAO extends DBContext{
    public List<Notification> getAllNotifications(int accId) {
    List<Notification> list = new ArrayList<>();
    String sql = "SELECT * FROM NotificationTB WHERE accId = ? ORDER BY createdAt DESC";

    try (Connection conn = new DBContext().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Notification n = new Notification();
            n.setNotificationId(rs.getInt("notificationId"));
            n.setAccId(rs.getInt("accId"));
            n.setSenderId(rs.getInt("senderId"));
            n.setTitle(rs.getString("title"));
            n.setMessage(rs.getString("message"));
            n.setNotifType(rs.getString("notifType"));
            n.setIsRead(rs.getBoolean("isRead"));
            n.setCreatedAt(rs.getTimestamp("createdAt"));
            list.add(n);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    public List<Notification> getNotificationsByAccId(int accId, String type) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM NotificationTB WHERE accId = ? "
                   + (type != null && !type.isEmpty() ? "AND notifType = ?" : "") 
                   + " ORDER BY createdAt DESC";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accId);
            if (type != null && !type.isEmpty()) ps.setString(2, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notifId"));
                n.setAccId(rs.getInt("accId"));
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                n.setNotifType(rs.getString("notifType"));
                n.setIsRead(rs.getBoolean("isRead"));
                n.setCreatedAt(rs.getTimestamp("createdAt"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int countUnreadNotifications(int accId) {
    String sql = "SELECT COUNT(*) FROM NotificationTB WHERE accId = ? AND isRead = 0";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, accId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
    public boolean insertNotification(Notification notif) {
    String sql = "INSERT INTO NotificationTB (accId, senderId, title, message, notifType, isRead, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, notif.getAccId());
        ps.setInt(2, notif.getSenderId());
        ps.setString(3, notif.getTitle());
        ps.setString(4, notif.getMessage());
        ps.setString(5, notif.getNotifType());
        ps.setBoolean(6, notif.isIsRead());
        ps.setTimestamp(7, new java.sql.Timestamp(notif.getCreatedAt().getTime()));
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
}