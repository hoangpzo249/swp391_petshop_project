/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author HuyHoang
 */
public class AccountDAO extends DBContext {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    DBContext db = new DBContext();

    public boolean registerAcc(Account account) {
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

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
        }
        return false;
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
}
