/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dao.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Notification;


/**
 *
 * @author QuangAnh
 */
public class NotificationDAO extends DBContext{
    public List<Notification> getNotificationsByAccount(int accId, String notifTypeFilter){
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM NotificationTB WHERE accId = ?";
        if (notifTypeFilter != null && !notifTypeFilter.isEmpty()){
            sql += " AND notifType = ?";
        }
        sql += " ORDER BY createdAt DESC";
        
        try(PreparedStatement stm = c.prepareStatement(sql)){
            stm.setInt(1, accId);
            if (notifTypeFilter != null && !notifTypeFilter.isEmpty()){
            stm.setString(2, notifTypeFilter);
        }
        ResultSet rs = stm.executeQuery();
        while (rs.next()){
            Notification n = new Notification();
            n.setNotificationId(rs.getInt("notificationId"));
            n.setAccId(rs.getInt("accId"));
            n.setSenderId(rs.getInt("senderId"));
            n.setTitle(rs.getString("title"));
            n.setMessage(rs.getString("message"));
            n.setNotifType(rs.getString("notifType"));
            n.setIsRead(rs.getBoolean("isRead"));
            n.setCreatedAt(rs.getDate("createdAt"));
            list.add(n);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
