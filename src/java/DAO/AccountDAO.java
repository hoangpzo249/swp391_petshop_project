/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            ps.setString(8, account.getAccPhoneNumber());
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

    public Account isLoginAcc(String email, String username, String pass) {
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM AccountTB WHERE accEmail = ? or accUsername = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, username);

            rs = ps.executeQuery();
            if (rs.next()) {
                String hashPass = rs.getString("accPassword");
                if (BCrypt.checkpw(pass, hashPass)) {

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
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean updatePass(Account acc) {
        try {
            String sql = "UPDATE AccountTB SET accPassword = ? WHERE accEmail = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, acc.getAccPassword());
            ps.setString(2, acc.getAccEmail());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {

        }
        return false;
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

            ps.setString(1, account.getAccEmail());
            ps.setString(2, account.getAccEmail());
            ps.setString(3, genPass());
            ps.setString(4, account.getAccFname());
            ps.setString(5, account.getAccLname());
            ps.setString(6, "2000-01-01");
            ps.setString(7, "Chưa cập nhật");
            ps.setString(8, account.getAccPhoneNumber());
            ps.setString(9, "Customer");
            ps.setString(10, "New account");

            ps.executeUpdate();
//            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return false;
    }

    public String genPass() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "`~!@#$%^&*()_+";
        String all = upper + lower + digits + special;
        Random random = new Random();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            pass.append(all.charAt(random.nextInt(all.length())));

        }
        System.out.println(pass);
        return pass.toString();
    }
}
