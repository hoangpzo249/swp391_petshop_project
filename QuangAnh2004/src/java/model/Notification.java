/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author QuangAnh
 */
public class Notification {
    private int notificationId;
    private int accId;
    private int senderId;
    private String title;
    private String message;
    private String notifType;
    private boolean isRead;
    private Date createdAt;

    public Notification() {
    }

    public Notification(int notificationId, int accId, int senderId, String title, String massage, String notifType, boolean isRead, Date createdAt) {
        this.notificationId = notificationId;
        this.accId = accId;
        this.senderId = senderId;
        this.title = title;
        this.message = message;
        this.notifType = notifType;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotifType() {
        return notifType;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", accId=" + accId + ", senderId=" + senderId + ", title=" + title + ", massage=" + message + ", notifType=" + notifType + ", isRead=" + isRead + ", createdAt=" + createdAt + '}';
    }
    
}
