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
    public List<Notification> getNotificationsByAccId(int accId, String type) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM NotificationTB WHERE accId = ?" +
                     (type != null && !type.isEmpty() ? " AND notifType = ?" : "") +
                     " ORDER BY createdAt DESC";
        try (Connection con = new DBContext().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accId);
            if (type != null && !type.isEmpty()) {
                ps.setString(2, type);
            }

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
}