/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author HuyHoang
 */
public class AccountDAO extends DBContext {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    DBContext db = new DBContext();

    public void registerAcc(Account account) {
        try {
            con = db.getConnection();
            String sql = "INSERT INTO AccountTB(accUsername, accEmail, accPassword, accFname, accLname, accDob, accAddress, accPhoneNumber, accRole, accDescription, accCreateDate, accImage, accStatus) \n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), Null, 'Active');";
            ps = con.prepareStatement(sql);

            ps.setString(1, account.getAccUsername());
            ps.setString(2, account.getAccEmail());
            ps.setString(3, account.getAccPassword());
            ps.setString(4, account.getAccFname());
            ps.setString(5, account.getAccLname());
            ps.setString(6, "2000-01-01");
            ps.setString(7, "Chưa cập nhật");
            ps.setString(8, "Chưa cập nhật");
            ps.setString(9, "Customer");
            ps.setString(10, "New account");

//            int rowsAffected = ps.executeUpdate();
            ps.executeUpdate();
//            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return false;
    }

    public boolean isEmailExist(String email) {
        try {
            con = db.getConnection();
            String sql = "SELECT 1 FROM AccountTB WHERE accEmail = ?;";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isUsernameExist(String username) {
        try {
            con = db.getConnection();
            String sql = "SELECT 1 FROM AccountTB WHERE accUsername = ?;";
            ps = con.prepareStatement(sql);

            ps.setString(1, username);
            rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
        }
        return false;
    }

    public Account isLoginAcc(String email, String pass) {
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM AccountTB WHERE accEmail = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
//            ps.setString(2, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                String hashPass = rs.getString("accPassword");
                if (BCrypt.checkpw(pass, hashPass)) {

                    Account acc = new Account();
                    acc.setAccId(rs.getInt("accId"));
                    acc.setAccUsername(rs.getString("accUsername"));
                    acc.setAccPassword(rs.getString("accPassword"));
                    acc.setAccEmail(rs.getString("accEmail"));
                    acc.setAccFname(rs.getString("accFname"));
                    acc.setAccLname(rs.getString("accLname"));
                    acc.setAccDob(rs.getDate("accDob"));
                    acc.setAccAddress(rs.getString("accAddress"));
                    acc.setAccPhoneNumber(rs.getString("accPhoneNumber"));
                    acc.setAccRole(rs.getString("accRole"));
                    acc.setAccDescription(rs.getString("accDescription"));
                    acc.setAccCreateDate(rs.getString("accCreateDate"));
                    acc.setAccImage(rs.getString("accImage"));
                    acc.setAccStatus(rs.getString("accStatus"));

                    return acc;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean updatePass(String email, String pass) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accPassword = ? WHERE accEmail = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, pass);
            ps.setString(2, email);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmail(String email, int accId) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accEmail = ? WHERE accId = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setInt(2, accId);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account ggByEmail(String email) {
        try {
            String sql = "Select * from AccountTB where accEmail =?";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                
                acc.setAccId(rs.getInt("accId"));
                acc.setAccUsername(rs.getString("accUsername"));
                acc.setAccEmail(rs.getString("accEmail"));
                acc.setAccFname(rs.getString("accFname"));
                acc.setAccLname(rs.getString("accLname"));
                acc.setAccDob(rs.getDate("accDob"));
                acc.setAccAddress(rs.getString("accAddress"));
                acc.setAccPhoneNumber("accPhoneNumber");
                acc.setAccRole(rs.getString("accRole"));
                acc.setAccDescription(rs.getString("accDescription"));
                acc.setAccCreateDate(rs.getString("accCreateDate"));
                acc.setAccImage(rs.getString("accImage"));
                acc.setAccStatus(rs.getString("accStatus"));
                
                return acc;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void registerAccByGG(Account account) {
        try {
            con = db.getConnection();
            String sql = "INSERT INTO AccountTB(accUsername, accEmail, accPassword, accFname, accLname, accDob, accAddress, accPhoneNumber, accRole, accDescription, accCreateDate, accImage, accStatus) \n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), Null, 'Active');";
            ps = con.prepareStatement(sql);

            ps.setString(1, account.getAccUsername());
            ps.setString(2, account.getAccEmail());
            ps.setString(3, genPass());
            ps.setString(4, account.getAccFname());
            ps.setString(5, account.getAccLname());
            ps.setString(6, "2000-01-01");
            ps.setString(7, "Chưa cập nhật");
            ps.setString(8, "Chưa cập nhật");
            ps.setString(9, "Customer");
            ps.setString(10, "New account");

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String genPass() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()_+";
        String all = upper + lower + digits + special;
        Random random = new Random();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            pass.append(all.charAt(random.nextInt(all.length())));
        }

        String hashPass = BCrypt.hashpw(pass.toString(), BCrypt.gensalt());
        System.out.println(hashPass);
        return hashPass;
    }

//    public void updateProfile(String accUsername, String accFname, String accLname, String accPhoneNumber, String accAddress, String accDescription, int accId) {
//        try {
//            con = db.getConnection();
//            String sql = "UPDATE AccountTB SET accUsername =?, accFname = ?, accLname= ?,accPhoneNumber = ?,accAddress = ?,accDescription  = ? where accId = ?";
//
//            ps = con.prepareStatement(sql);
//
//            ps.setString(1, accUsername);
//            ps.setString(2, accFname);
//            ps.setString(3, accLname);
//            ps.setDate(4, new java.sql.Date(accDob.getTime()));
//            ps.setString(4, accPhoneNumber);
//            ps.setString(5, accAddress);
//            ps.setString(6, accDescription);
//            ps.setInt(7, accId);
//
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void updateProfile(Account acc) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accUsername =?, accFname = ?, accLname= ?, accDob =?, accPhoneNumber = ?,accAddress = ?,accDescription  = ? where accId = ?";

            ps = con.prepareStatement(sql);

            ps.setString(1, acc.getAccUsername());
            ps.setString(2, acc.getAccFname());
            ps.setString(3, acc.getAccLname());
            ps.setDate(4, new java.sql.Date(acc.getAccDob().getTime()));
            ps.setString(5, acc.getAccPhoneNumber());
            ps.setString(6, acc.getAccAddress());
            ps.setString(7, acc.getAccDescription());
            ps.setInt(8, acc.getAccId());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
