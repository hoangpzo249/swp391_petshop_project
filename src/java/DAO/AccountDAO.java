/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Lenovo
 */
public class AccountDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public void createAccount(Account account) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO AccountTB (\n"
                    + "    accUsername,\n"
                    + "    accEmail,\n"
                    + "    accPassword,\n"
                    + "    accFname,\n"
                    + "    accLname,\n"
                    + "    accDob,\n"
                    + "    accAddress,\n"
                    + "    accPhoneNumber,\n"
                    + "    accRole,\n"
                    + "    accDescription,\n"
                    + "    accImage,\n"
                    + "    accStatus\n"
                    + ") VALUES (\n"
                    + "    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?\n"
                    + ");";
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getAccUsername());
            ps.setString(2, account.getAccEmail());
            ps.setString(3, BCrypt.hashpw(account.getAccPassword(), BCrypt.gensalt()));
            ps.setString(4, account.getAccFname());
            ps.setString(5, account.getAccLname());
            ps.setDate(6, java.sql.Date.valueOf(account.getAccDob()));
            ps.setString(7, account.getAccAddress());
            ps.setString(8, account.getAccPhoneNumber());
            ps.setString(9, account.getAccRole());
            ps.setString(10, account.getAccDescription());
            ps.setBytes(11, account.getAccImage());
            ps.setString(12, account.getAccStatus());
            
            ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
