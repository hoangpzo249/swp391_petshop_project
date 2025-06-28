/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Base64;
import java.util.Date;

/**
 *
 * @author HuyHoang
 */
public class Account {

    private int accId;
    private String accUsername;
    private String accEmail;
    private String accPassword;
    private String accFname;
    private String accLname;

    private Date accDob;

    private String accAddress;
    private String accPhoneNumber;
    private String accRole;
    private String accDescription;

    private String accCreateDate;
    private byte[] accImage;
    private String accStatus;

    public Account() {
    }

    public Account(int accId, String accUsername, String accEmail, String accPassword, String accFname, String accLname, Date accDob, String accAddress, String accPhoneNumber, String accRole, String accDescription, String accCreateDate, byte[] accImage, String accStatus) {
        this.accId = accId;
        this.accUsername = accUsername;
        this.accEmail = accEmail;
        this.accPassword = accPassword;
        this.accFname = accFname;
        this.accLname = accLname;
        this.accDob = accDob;
        this.accAddress = accAddress;
        this.accPhoneNumber = accPhoneNumber;
        this.accRole = accRole;
        this.accDescription = accDescription;
        this.accCreateDate = accCreateDate;
        this.accImage = accImage;
        this.accStatus = accStatus;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getAccUsername() {
        return accUsername;
    }

    public void setAccUsername(String accUsername) {
        this.accUsername = accUsername;
    }

    public String getAccEmail() {
        return accEmail;
    }

    public void setAccEmail(String accEmail) {
        this.accEmail = accEmail;
    }

    public String getAccPassword() {
        return accPassword;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }

    public String getAccFname() {
        return accFname;
    }

    public void setAccFname(String accFname) {
        this.accFname = accFname;
    }

    public String getAccLname() {
        return accLname;
    }

    public void setAccLname(String accLname) {
        this.accLname = accLname;
    }

    public Date getAccDob() {
        return accDob;
    }

    public void setAccDob(Date accDob) {
        this.accDob = accDob;
    }

    public String getAccAddress() {
        return accAddress;
    }

    public void setAccAddress(String accAddress) {
        this.accAddress = accAddress;
    }

    public String getAccPhoneNumber() {
        return accPhoneNumber;
    }

    public void setAccPhoneNumber(String accPhoneNumber) {
        this.accPhoneNumber = accPhoneNumber;
    }

    public String getAccRole() {
        return accRole;
    }

    public void setAccRole(String accRole) {
        this.accRole = accRole;
    }

    public String getAccDescription() {
        return accDescription;
    }

    public void setAccDescription(String accDescription) {
        this.accDescription = accDescription;
    }

    public String getAccCreateDate() {
        return accCreateDate;
    }

    public void setAccCreateDate(String accCreateDate) {
        this.accCreateDate = accCreateDate;
    }

    public byte[] getAccImage() {
        return accImage;
    }

    public void setAccImage(byte[] accImage) {
        this.accImage = accImage;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    @Override
    public String toString() {
        return "Account{" + "accId=" + accId + ", accUsername=" + accUsername + ", accEmail=" + accEmail + ", accPassword=" + accPassword + ", accFname=" + accFname + ", accLname=" + accLname + ", accDob=" + accDob + ", accAddress=" + accAddress + ", accPhoneNumber=" + accPhoneNumber + ", accRole=" + accRole + ", accDescription=" + accDescription + ", accCreateDate=" + accCreateDate + ", accImage=" + accImage + ", accStatus=" + accStatus + '}';
    }

    public String displayAccImage() {
        if (accImage != null) {
            String base64Image = Base64.getEncoder().encodeToString(accImage);
            return "data:image/jpeg;base64," + base64Image;
        }
        return "images/support button/account.png";
    }
}
