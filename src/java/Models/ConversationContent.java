/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.time.LocalDateTime;

/**
 *
 * @author Lenovo
 */
public class ConversationContent {

    private Integer messageId;
    private Integer convoId;
    private Integer senderId;
    private String messageText;
    private LocalDateTime messageTime;

    public ConversationContent() {
    }

    public ConversationContent(Integer messageId, Integer convoId, Integer senderId, String messageText, LocalDateTime messageTime) {
        this.messageId = messageId;
        this.convoId = convoId;
        this.senderId = senderId;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getConvoId() {
        return convoId;
    }

    public void setConvoId(Integer convoId) {
        this.convoId = convoId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return "ConversationContent{" + "messageId=" + messageId + ", convoId=" + convoId + ", senderId=" + senderId + ", messageText=" + messageText + ", messageTime=" + messageTime + '}';
    }
    
    
}
