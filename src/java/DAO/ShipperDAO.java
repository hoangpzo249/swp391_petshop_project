/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.Account;
import Models.Shipper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class ShipperDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection conn;
    AccountDAO _accountdao = new AccountDAO();

    public List<Shipper> getAvailableShippers() {
        DBContext db = new AccountDAO();
        List<Shipper> list = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql
                    = "SELECT\n"
                    + "    a.accId,\n"
                    + "    a.accUsername,\n"
                    + "    a.accFname,\n"
                    + "    a.accLname,\n"
                    + "    s.shipperNote,\n"
                    + "    (\n"
                    + "        SELECT MAX(o_inner.deliveryDate)\n"
                    + "        FROM OrderTB AS o_inner\n"
                    + "        WHERE o_inner.shipperId = a.accId\n"
                    + "    ) AS lastDeliveryTime,\n"
                    + "    COUNT(o.orderId) AS currentShippingOrders\n"
                    + "FROM AccountTB AS a\n"
                    + "INNER JOIN ShipperTB AS s ON a.accId = s.shipperId\n"
                    + "LEFT JOIN OrderTB AS o ON a.accId = o.shipperId AND o.orderStatus = 'Shipping'\n"
                    + "WHERE\n"
                    + "    a.accRole = 'Shipper'\n"
                    + "    AND a.accStatus = 'Active'\n"
                    + "    AND s.shipperAvailability = 'Online'\n"
                    + "GROUP BY\n"
                    + "    a.accId,\n"
                    + "    a.accUsername,\n"
                    + "    a.accFname,\n"
                    + "    a.accLname,\n"
                    + "    s.shipperNote\n"
                    + "ORDER BY\n"
                    + "    lastDeliveryTime ASC,\n"
                    + "    currentShippingOrders ASC;";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("accId"));
                account.setAccFname(rs.getString("accFname"));
                account.setAccLname(rs.getString("accLname"));
                account.setAccUsername(rs.getString("accUsername"));
                list.add(new Shipper(account,
                        rs.getString("shipperNote"),
                        rs.getTimestamp("lastDeliveryTime"),
                        rs.getInt("currentShippingOrders")));
            }
            return list;
        } catch (Exception ex) {
            Logger.getLogger(ShipperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Shipper getShipperById(int id) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT \n"
                    + "    a.accId,\n"
                    + "    a.accUsername,\n"
                    + "    a.accEmail,\n"
                    + "    a.accFname,\n"
                    + "    a.accLname,\n"
                    + "    a.accDob,\n"
                    + "    a.accAddress,\n"
                    + "    a.accPhoneNumber,\n"
                    + "    a.accRole,\n"
                    + "    a.accDescription,\n"
                    + "    a.accCreateDate,\n"
                    + "    a.accImage,\n"
                    + "    a.accStatus,\n"
                    + "    s.shipperAvailability,\n"
                    + "    s.shipperNote\n"
                    + "FROM \n"
                    + "    AccountTB a\n"
                    + "JOIN \n"
                    + "    ShipperTB s ON a.accId = s.shipperId\n"
                    + "WHERE \n"
                    + "    a.accRole = 'Shipper' AND a.accId=?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getInt("accId"));
                account.setAccUsername(rs.getString("accUsername"));
                account.setAccEmail(rs.getString("accEmail"));
                account.setAccFname(rs.getString("accFname"));
                account.setAccLname(rs.getString("accLname"));
                account.setAccDob(rs.getDate("accDob"));
                account.setAccPhoneNumber(rs.getString("accPhoneNumber"));
                account.setAccRole(rs.getString("accRole"));
                account.setAccDescription(rs.getString("accDescription"));
                account.setAccCreateDate(rs.getString("accCreateDate"));
                account.setAccImage(rs.getBytes("accImage"));
                account.setAccStatus(rs.getString("accStatus"));

                Shipper shipper = new Shipper();

                shipper.setShipperId(rs.getInt("accId"));
                shipper.setShipperAvailability(rs.getString("shipperAvailability"));
                shipper.setShipperNote(rs.getString("shipperNote"));
                shipper.setShipperAccount(account);

                return shipper;
            }
        } catch (Exception ex) {
            Logger.getLogger(ShipperDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateStatusShipper(int shipperid, String status) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE Shippertb SET shipperAvailability = ? WHERE shipperId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, shipperid);

            int row = ps.executeUpdate();
            return row > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
