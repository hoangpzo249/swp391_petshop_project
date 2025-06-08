/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList; // để dùng arraylist
import model.Account;   //để nhét data vào model
import java.sql.Statement; //tạo statement
import java.sql.PreparedStatement; //tạo statement
import java.sql.ResultSet; // lấy resultset từ statement

import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;  // Để xử lý lỗi database

/**
 *
 * @author Admin
 */
public class AccountDAO extends DBContext {

    public ArrayList<Account> testGetAllAccount() {
        ArrayList<Account> accountList = new ArrayList<>(); //        tạo một arrayList để chứa data trả về
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 1. Tạo một Statement:
            // Statement là đối tượng giúp chúng ta gửi các câu lệnh SQL đến database
            statement = connection.createStatement();

            String sql = "select * from accountTB"; // tạo câu lệnh sql để thực thi

            // 3. Thực thi câu lệnh SQL và nhận kết quả:
            // executeQuery() dùng cho các lệnh SELECT. Nó trả về một ResultSet.
            // ResultSet giống như một bảng ảo chứa dữ liệu từ database.
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Tạo một đối tượng Account mới cho mỗi hàng dữ liệu
                Account account = new Account();

                // Lấy dữ liệu từ từng cột của hàng hiện tại và gán vào đối tượng Account
                // rs.getXXX("Ten_Cot_Trong_DB")
                // Đảm bảo "Ten_Cot_Trong_DB" phải giống hệt tên cột trong SQL Server
                account.setAccId(resultSet.getInt("accId"));
                account.setAccUsername(resultSet.getString("accUsername"));
                account.setAccEmail(resultSet.getString("accEmail"));
                account.setAccPassword(resultSet.getString("accPassword"));
                account.setAccFname(resultSet.getString("accFname"));
                account.setAccLname(resultSet.getString("accLname"));

                // Lấy ngày sinh: dùng getDate() cho kiểu DATE của SQL Server
                account.setAccDob(resultSet.getDate("accDob"));

                account.setAccAddress(resultSet.getString("accAddress"));
                account.setAccPhoneNumber(resultSet.getString("accPhoneNumber"));
                account.setAccRole(resultSet.getString("accRole"));
                account.setAccDescription(resultSet.getString("accDescription"));

                // Lấy ngày tạo: dùng getTimestamp() cho kiểu DATETIME của SQL Server
                account.setAccCreateDate(resultSet.getTimestamp("accCreateDate"));

                // Lấy dữ liệu ảnh: dùng getBytes() cho kiểu varbinary(MAX)
                account.setAccImage(resultSet.getBytes("accImage"));
                account.setAccStatus(resultSet.getString("accStatus"));

                // Thêm đối tượng Account vừa tạo vào danh sách
                accountList.add(account);
            }
        } catch (SQLException e) { // Bắt lỗi SQL (ví dụ: lỗi kết nối, lỗi cú pháp SQL)
            System.err.println("Đã xảy ra lỗi khi truy vấn dữ liệu Account:");
            System.err.println("Thông báo lỗi: " + e.getMessage());
            e.printStackTrace(); // In ra chi tiết lỗi để giúp debug
        } finally { // Khối finally LUÔN ĐƯỢC THỰC THI dù có lỗi hay không
            // 5. Đóng các tài nguyên JDBC: RẤT QUAN TRỌNG!
            // Đóng ResultSet trước
            if (resultSet != null) {
                try {
                    resultSet.close();
                    System.out.println("Đã đóng ResultSet.");
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng ResultSet: " + e.getMessage());
                }
            }
            // Đóng Statement sau
            if (statement != null) {
                try {
                    statement.close();
                    System.out.println("Đã đóng Statement.");
                } catch (SQLException e) {
                    System.err.println("Lỗi khi đóng Statement: " + e.getMessage());
                }
            }
        }
        return accountList;
    }

    public Account getAccountByUserName(String userName) {
        Account account = new Account();
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from accountTB where accUsername = ? ";
            preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, userName);
            resultSet = preStatement.executeQuery();
//            resultSet = null;
            while (resultSet.next()) {
                account.setAccId(resultSet.getInt("accId"));
                account.setAccUsername(resultSet.getString("accUsername"));
                account.setAccEmail(resultSet.getString("accEmail"));
                account.setAccPassword(resultSet.getString("accPassword"));
                account.setAccFname(resultSet.getString("accFname"));
                account.setAccLname(resultSet.getString("accLname"));

                // Lấy ngày sinh: dùng getDate() cho kiểu DATE của SQL Server
                account.setAccDob(resultSet.getDate("accDob"));

                account.setAccAddress(resultSet.getString("accAddress"));
                account.setAccPhoneNumber(resultSet.getString("accPhoneNumber"));
                account.setAccRole(resultSet.getString("accRole"));
                account.setAccDescription(resultSet.getString("accDescription"));

                // Lấy ngày tạo: dùng getTimestamp() cho kiểu DATETIME của SQL Server
                account.setAccCreateDate(resultSet.getTimestamp("accCreateDate"));

                // Lấy dữ liệu ảnh: dùng getBytes() cho kiểu varbinary(MAX)
                account.setAccImage(resultSet.getBytes("accImage"));
                account.setAccStatus(resultSet.getString("accStatus"));
                return account;
            }
        } catch (Exception e) {
            System.out.println("Có lỗi rồi douma");
        } 
        return account;
    }

//    testMain
    public static void main(String[] args) {
        AccountDAO test = new AccountDAO();
        System.out.println("Test lấy all Account");
        ArrayList<Account> allAcountList = test.testGetAllAccount();
        for (Account account : allAcountList) {
            System.out.println(account);
        }
        
        System.out.println("\nTest lấy 1 account: admin user rồi in ra");
        Account account = test.getAccountByUserName("adminUser");
        System.out.println(account);
    }

}
