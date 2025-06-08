/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account_Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author HuyHoang
 */
public class Account_LogDAO extends DBContext {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    DBContext db = new DBContext();

    public void banAcc(Account_Log log) {
        try {
            con = db.getConnection();
            String sql = "INSERT INTO AccountDeactivationLogTB(accId,deactivatedBy,deactivationReason,deactivationDate,reactivationDate) \n" +
"VALUES (?,?,?, GETDATE(), ?)";
            ps = con.prepareStatement(sql);

            ps.setInt(1, log.getAccId());
            ps.setInt(2, log.getDeactivatedBy());
            ps.setString(3, log.getDeactivationReason());
            ps.setString(4, "Chưa Cập nhật");

//            int rowsAffected = ps.executeUpdate();
            ps.executeUpdate();
//            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return false;
    }
}
