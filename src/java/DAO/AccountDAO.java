/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Account;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public void registerAccCusByAdmin(Account account) {
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
            ps.setDate(6, new java.sql.Date(account.getAccDob().getTime()));
            ps.setString(7, account.getAccAddress());
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

    public void registerAccStaffByAdmin(Account account) {
        try {
            con = db.getConnection();
            String sql = "INSERT INTO AccountTB(accUsername, accEmail, accPassword, accFname, accLname, accDob, accAddress, accPhoneNumber, accRole, accDescription, accCreateDate, accImage, accStatus) \n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), Null, ?);";
            ps = con.prepareStatement(sql);

            ps.setString(1, account.getAccUsername());
            ps.setString(2, account.getAccEmail());
            ps.setString(3, account.getAccPassword());
            ps.setString(4, account.getAccFname());
            ps.setString(5, account.getAccLname());
            ps.setDate(6, new java.sql.Date(account.getAccDob().getTime()));
            ps.setString(7, account.getAccAddress());
            ps.setString(8, account.getAccPhoneNumber());
            ps.setString(9, account.getAccRole());
            ps.setString(10, "New account");
            ps.setString(11, account.getAccStatus());

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

    public boolean checkPass(String email, String password) {
        try {
            con = db.getConnection();
            String sql = "SELECT accPassword FROM AccountTB WHERE accEmail = ?;";
            ps = con.prepareStatement(sql);

            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                String hassPass = rs.getString("accPassword");
                return BCrypt.checkpw(password, hassPass);
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean checkPassAdmin(String password) {
        try {
            con = db.getConnection();
            String sql = "SELECT accPassword FROM AccountTB WHERE accId = 1;";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {
                String hassPass = rs.getString("accPassword");
                return BCrypt.checkpw(password, hassPass);
            }
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
//                    acc.setAccPassword(rs.getString("accPassword"));
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

    public boolean updateRole(String role, int accId) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accRole = ? WHERE accId = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, role);
            ps.setInt(2, accId);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean banAcc(int accId) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accStatus = 'Inactive' WHERE accId = ?";
            ps = con.prepareStatement(sql);

            ps.setInt(1, accId);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unBanAcc(int accId) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accStatus = 'Active' WHERE accId = ?";
            ps = con.prepareStatement(sql);

            ps.setInt(1, accId);

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
                acc.setAccPhoneNumber(rs.getString("accPhoneNumber"));
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
            ps.setString(8, account.getAccPhoneNumber());
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

    public boolean uploadAvatar(int accId, String avatarPath) {
        try {
            con = db.getConnection();
            String sql = "UPDATE AccountTB SET accImage = ? WHERE accId = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, avatarPath);
            ps.setInt(2, accId);

            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAllAccount() {
        List<Account> accList = new ArrayList<>();
        try {
            con = db.getConnection();
            String sql = "select * from AccountTB";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int accId = rs.getInt("accId");
                String accUsername = rs.getString("accUsername");
                String accEmail = rs.getString("accEmail");
                String accPassword = rs.getString("accPassword");
                String accFname = rs.getString("accFname");
                String accLname = rs.getString("accLname");
                Date accDob = rs.getDate("accDob");
                String accAddress = rs.getString("accAddress");
                String accPhoneNumber = rs.getString("accPhoneNumber");
                String accRole = rs.getString("accRole");
                String accDescription = rs.getString("accDescription");
                String accCreateDate = rs.getString("accCreateDate");
                String accImage = rs.getString("accImage");
                String accStatus = rs.getString("accStatus");
                Account acc = new Account(accId, accUsername, accEmail, accPassword, accFname, accLname, accDob, accAddress, accPhoneNumber, accRole, accDescription, accCreateDate, accImage, accStatus);
                accList.add(acc);
            }
        } catch (Exception e) {
        }
        return accList;
    }

    public List<Account> get10AccountNew() {
        List<Account> accList = new ArrayList<>();
        try {
            con = db.getConnection();
            String sql = "select TOP 10 * from AccountTB order by accCreateDate DESC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int accId = rs.getInt("accId");
                String accUsername = rs.getString("accUsername");
                String accEmail = rs.getString("accEmail");
                String accPassword = rs.getString("accPassword");
                String accFname = rs.getString("accFname");
                String accLname = rs.getString("accLname");
                Date accDob = rs.getDate("accDob");
                String accAddress = rs.getString("accAddress");
                String accPhoneNumber = rs.getString("accPhoneNumber");
                String accRole = rs.getString("accRole");
                String accDescription = rs.getString("accDescription");
                String accCreateDate = rs.getString("accCreateDate");
                String accImage = rs.getString("accImage");
                String accStatus = rs.getString("accStatus");
                Account acc = new Account(accId, accUsername, accEmail, accPassword, accFname, accLname, accDob, accAddress, accPhoneNumber, accRole, accDescription, accCreateDate, accImage, accStatus);
                accList.add(acc);
            }
        } catch (Exception e) {
        }
        return accList;
    }
//    
//    public static void main(String[] args) {
//        AccountDAO acc = new AccountDAO();
//        List<Account> accList  = acc.get10AccountNew();
//        for (Account account : accList) {
//            System.out.println(account);
//        }
//    }

    public Account getAccId(int id, String role) {
        try {
            con = db.getConnection();
            String sql = "Select * from AccountTB where accId = ? and accRole = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, role);

            rs = ps.executeQuery();
            if (rs.next()) {
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
        } catch (Exception e) {
        }
        return null;
    }

    public List<Account> getAccRole(String role) {
        List<Account> accList = new ArrayList<>();
        try {
            con = db.getConnection();
            String sql = "Select * from AccountTB where accRole = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, role);

            rs = ps.executeQuery();
            while (rs.next()) {
                int accId = rs.getInt("accId");
                String accUsername = rs.getString("accUsername");
                String accEmail = rs.getString("accEmail");
                String accPassword = rs.getString("accPassword");
                String accFname = rs.getString("accFname");
                String accLname = rs.getString("accLname");
                Date accDob = rs.getDate("accDob");
                String accAddress = rs.getString("accAddress");
                String accPhoneNumber = rs.getString("accPhoneNumber");
                String accRole = rs.getString("accRole");
                String accDescription = rs.getString("accDescription");
                String accCreateDate = rs.getString("accCreateDate");
                String accImage = rs.getString("accImage");
                String accStatus = rs.getString("accStatus");
                Account acc = new Account(accId, accUsername, accEmail, accPassword, accFname, accLname, accDob, accAddress, accPhoneNumber, accRole, accDescription, accCreateDate, accImage, accStatus);
                accList.add(acc);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Account getAccIdStatus(int id, String role, String ban) {
        try {
            con = db.getConnection();
            String sql = "Select * from AccountTB where accId = ? and accRole = ? and accStatus =?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, role);
            ps.setString(3, ban);

            rs = ps.executeQuery();
            if (rs.next()) {
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
        } catch (Exception e) {
        }
        return null;
    }

    public int countAllAcc() {
        int count = 0;
        try {
            con = db.getConnection();
            String sql = "SELECT COUNT(*) FROM AccountTB";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public int countAllAccActive() {
        int count = 0;
        try {
            con = db.getConnection();
            String sql = "SELECT COUNT(*) FROM AccountTB WHERE accStatus='active';";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public int countAllAccInactive() {
        int count = 0;
        try {
            con = db.getConnection();
            String sql = "SELECT COUNT(*) FROM AccountTB WHERE accStatus='Inactive';";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return count;
    }

    public List<Account> searchAcc(String keyword) {
        List<Account> list = new ArrayList<>();
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM AccountTB WHERE accFname LIKE ? OR accLname LIKE ? OR accEmail LIKE ?";
            ps = con.prepareStatement(sql);
            String likeKeyword = "%" + keyword + "%";
            
            ps.setString(1, likeKeyword);
            ps.setString(2, likeKeyword);
            ps.setString(3, likeKeyword);

            rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccId(rs.getInt("accId"));
                acc.setAccUsername(rs.getString("accUsername"));
                acc.setAccEmail(rs.getString("accEmail"));
                acc.setAccFname(rs.getString("accFname"));
                acc.setAccLname(rs.getString("accLname"));
                acc.setAccRole(rs.getString("accRole"));
                acc.setAccStatus(rs.getString("accStatus"));
                acc.setAccDob(rs.getDate("accDob"));
                acc.setAccAddress(rs.getString("accAddress"));
                
                acc.setAccPhoneNumber(rs.getString("accPhoneNumber"));
                acc.setAccDescription(rs.getString("accDescription"));
                acc.setAccCreateDate(rs.getString("accCreateDate"));
                acc.setAccImage(rs.getString("accImage"));
                list.add(acc);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
