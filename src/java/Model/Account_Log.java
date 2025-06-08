/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author HuyHoang
 */
public class Account_Log {
    private int logId;
    private int accId;
    private int deactivatedBy;
    private String deactivationReason;
    private String deactivationDate;
    private String reactivationDate;

    public Account_Log() {
    }

    public Account_Log(int logId, int accId, int deactivatedBy, String deactivationReason, String deactivationDate, String reactivationDate) {
        this.logId = logId;
        this.accId = accId;
        this.deactivatedBy = deactivatedBy;
        this.deactivationReason = deactivationReason;
        this.deactivationDate = deactivationDate;
        this.reactivationDate = reactivationDate;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(int deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
    }

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(String deactivationReason) {
        this.deactivationReason = deactivationReason;
    }

    public String getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(String deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public String getReactivationDate() {
        return reactivationDate;
    }

    public void setReactivationDate(String reactivationDate) {
        this.reactivationDate = reactivationDate;
    }

    @Override
    public String toString() {
        return "Account_Log{" + "logId=" + logId + ", accId=" + accId + ", deactivatedBy=" + deactivatedBy + ", deactivationReason=" + deactivationReason + ", deactivationDate=" + deactivationDate + ", reactivationDate=" + reactivationDate + '}';
    }
    
    
}
