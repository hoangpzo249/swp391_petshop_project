/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author QuangAnh
 */
import Model.Notification;
import DAO.NotificationDAO;
import java.util.Date;

public class NotificationUtils {
    public static void sendNotification(int accId, int senderId, String title, String message, String type) {
        Notification n = new Notification();
        n.setAccId(accId);
        n.setSenderId(senderId);
        n.setTitle(title);
        n.setMessage(message);
        n.setNotifType(type);
        n.setIsRead(true);
        n.setCreatedAt(new Date());
        new NotificationDAO().insertNotification(n);
    }
}
