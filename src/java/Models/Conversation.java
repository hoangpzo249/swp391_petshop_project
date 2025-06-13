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
public class Conversation {

    private Integer convoId;
    private Integer accId;
    private LocalDateTime convoStartDate;
    private LocalDateTime convoEndDate;
    private String convoStatus;

    public Conversation() {
    }

    public Conversation(Integer convoId, Integer accId, LocalDateTime convoStartDate, LocalDateTime convoEndDate, String convoStatus) {
        this.convoId = convoId;
        this.accId = accId;
        this.convoStartDate = convoStartDate;
        this.convoEndDate = convoEndDate;
        this.convoStatus = convoStatus;
    }

    public Integer getConvoId() {
        return convoId;
    }

    public void setConvoId(Integer convoId) {
        this.convoId = convoId;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public LocalDateTime getConvoStartDate() {
        return convoStartDate;
    }

    public void setConvoStartDate(LocalDateTime convoStartDate) {
        this.convoStartDate = convoStartDate;
    }

    public LocalDateTime getConvoEndDate() {
        return convoEndDate;
    }

    public void setConvoEndDate(LocalDateTime convoEndDate) {
        this.convoEndDate = convoEndDate;
    }

    public String getConvoStatus() {
        return convoStatus;
    }

    public void setConvoStatus(String convoStatus) {
        this.convoStatus = convoStatus;
    }

    @Override
    public String toString() {
        return "Conversation{" + "convoId=" + convoId + ", accId=" + accId + ", convoStartDate=" + convoStartDate + ", convoEndDate=" + convoEndDate + ", convoStatus=" + convoStatus + '}';
    }

}
