/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
import java.util.Date;   // Để biểu diễn ngày và giờ chung

public class Account {

    private int accId;
    private String accUsername;
    private String accEmail;
    private String accPassword;
    private String accFname;
    private String accLname;
    private Date accDob;         // date (SQL Server) -> java.util.Date
    private String accAddress;
    private String accPhoneNumber;
    private String accRole;
    private String accDescription;
    private Date accCreateDate;  // datetime (SQL Server) -> java.util.Date
    private byte[] accImage;
    private String accStatus;

    // --- 1. Constructor mặc định (Không tham số) ---
    // Cần thiết nếu bạn muốn tạo đối tượng Account mà không gán giá trị ban đầu cho các trường.
    public Account() {
        // Có thể khởi tạo các trường mặc định ở đây nếu cần
    }

    // --- 2. Constructor đầy đủ tham số ---
    // Hữu ích khi bạn muốn tạo một đối tượng Account với tất cả các thông tin ngay lập tức.
    public Account(int accId, String accUsername, String accEmail, String accPassword,
                   String accFname, String accLname, Date accDob, String accAddress,
                   String accPhoneNumber, String accRole, String accDescription,
                   Date accCreateDate, byte[] accImage, String accStatus) {
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

    // --- 3. Getters (Phương thức để lấy giá trị của thuộc tính) ---
    public int getAccId() {
        return accId;
    }

    public String getAccUsername() {
        return accUsername;
    }

    public String getAccEmail() {
        return accEmail;
    }

    public String getAccPassword() {
        return accPassword;
    }

    public String getAccFname() {
        return accFname;
    }

    public String getAccLname() {
        return accLname;
    }

    public Date getAccDob() {
        return accDob;
    }

    public String getAccAddress() {
        return accAddress;
    }

    public String getAccPhoneNumber() {
        return accPhoneNumber;
    }

    public String getAccRole() {
        return accRole;
    }

    public String getAccDescription() {
        return accDescription;
    }

    public Date getAccCreateDate() {
        return accCreateDate;
    }

    public byte[] getAccImage() {
        return accImage;
    }

    public String getAccStatus() {
        return accStatus;
    }

    // --- 4. Setters (Phương thức để gán giá trị cho thuộc tính) ---
    public void setAccId(int accId) {
        this.accId = accId;
    }

    public void setAccUsername(String accUsername) {
        this.accUsername = accUsername;
    }

    public void setAccEmail(String accEmail) {
        this.accEmail = accEmail;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword;
    }

    public void setAccFname(String accFname) {
        this.accFname = accFname;
    }

    public void setAccLname(String accLname) {
        this.accLname = accLname;
    }

    public void setAccDob(Date accDob) {
        this.accDob = accDob;
    }

    public void setAccAddress(String accAddress) {
        this.accAddress = accAddress;
    }

    public void setAccPhoneNumber(String accPhoneNumber) {
        this.accPhoneNumber = accPhoneNumber;
    }

    public void setAccRole(String accRole) {
        this.accRole = accRole;
    }

    public void setAccDescription(String accDescription) {
        this.accDescription = accDescription;
    }

    public void setAccCreateDate(Date accCreateDate) {
        this.accCreateDate = accCreateDate;
    }

    public void setAccImage(byte[] accImage) {
        this.accImage = accImage;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    // --- 5. Override toString() để dễ dàng debug và hiển thị thông tin đối tượng ---
    @Override
    public String toString() {
        return "Account{" +
               "accId=" + accId +
               ", accUsername='" + accUsername + '\'' +
               ", accEmail='" + accEmail + '\'' +
               ", accPassword='" + accPassword + '\'' +
               ", accFname='" + accFname + '\'' +
               ", accLname='" + accLname + '\'' +
               ", accDob=" + accDob +
               ", accAddress='" + accAddress + '\'' +
               ", accPhoneNumber='" + accPhoneNumber + '\'' +
               ", accRole='" + accRole + '\'' +
               ", accDescription='" + accDescription + '\'' +
               ", accCreateDate=" + accCreateDate +
               ", accImage=" + (accImage != null ? "byte[" + accImage.length + "]" : "null") + // Xử lý null cho mảng byte
               ", accStatus='" + accStatus + '\'' +
               '}';
    }

    // --- 6. Phương thức nghiệp vụ (Business Logic) ---
    // Ví dụ: kiểm tra trạng thái hoạt động của tài khoản
    public boolean isActive() {
        return "Active".equalsIgnoreCase(this.accStatus);
    }
}