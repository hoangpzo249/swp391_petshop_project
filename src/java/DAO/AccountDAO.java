package DAO;

import Models.Account;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class AccountDAO {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("SELECT * FROM AccountTB");
            rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = extractAccount(rs);
                list.add(acc);
            }
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeAll();
        }
        return list;
    }

    public Account FindUserInfo(String username) {
        Account acc = null;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("SELECT * FROM AccountTB WHERE accUsername = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                acc = extractAccount(rs);
            }
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeAll();
        }
        return acc;
    }

    public boolean LoginUser(String username, String password) {
        Account acc = FindUserInfo(username);
        return acc != null && BCrypt.checkpw(password, acc.getAccPassword());
    }

    public void createAccount(String username, String password, String fname, String lname, String phone, String address) {
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("INSERT INTO AccountTB (accUsername, accPassword, accFname, accLname, accPhoneNumber, accAddress) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeAll();
        }
    }

    public void createAccountHashed(String username, String password, String fname, String lname, String phone, String address) {
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("INSERT INTO AccountTB (accUsername, accPassword, accFname, accLname, accPhoneNumber, accAddress) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeAll();
        }
    }

    public void updateAccount(Account acc) {
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("UPDATE AccountTB SET accPassword = ?, accFname = ?, accLname = ?, accPhoneNumber = ?, accAddress = ? WHERE accId = ?");
            ps.setString(1, acc.getAccPassword());
            ps.setString(2, acc.getAccFname());
            ps.setString(3, acc.getAccLname());
            ps.setString(4, acc.getAccPhoneNumber());
            ps.setString(5, acc.getAccAddress());
            ps.setInt(6, acc.getAccId());
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeAll();
        }
    }

    public void updatePassword(String username, String password) {
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("UPDATE AccountTB SET accPassword = ? WHERE accUsername = ?");
            ps.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeAll();
        }
    }

    public boolean nameAvailable(String username) {
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("SELECT accId FROM AccountTB WHERE accUsername = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            return !rs.next();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            closeAll();
        }
    }

    public boolean phoneAvailable(String phone) {
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement("SELECT accId FROM AccountTB WHERE accPhoneNumber = ?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            return !rs.next();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            closeAll();
        }
    }

    public boolean passwordEligible(String password) {
        return password.length() >= 8
                && password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*\\W.*");
    }

    private Account extractAccount(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("accId"),
                rs.getString("accUsername"),
                rs.getString("accEmail"),
                rs.getString("accPassword"),
                rs.getString("accFname"),
                rs.getString("accLname"),
                rs.getDate("accDob"),
                rs.getString("accAddress"),
                rs.getString("accPhoneNumber"),
                rs.getString("accRole"),
                rs.getString("accDescription"),
                rs.getTimestamp("accCreateDate"),
                rs.getBytes("accImage"),
                rs.getString("accStatus")
        );
    }
    public int createGuestAccount(String email, String phone, String address) {
    String guestUsername = "guest_" + System.currentTimeMillis();
    String defaultPassword = "Guest123@";
    String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt());

    String sql = "INSERT INTO AccountTB " +
                 "(accUsername, accPassword, accFname, accLname, accPhoneNumber, accAddress, accEmail, accRole, accStatus) " +
                 "VALUES (?, ?, 'Khách', '', ?, ?, ?, 'Customer', 'Inactive')";

    try (
        Connection conn = new DBContext().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    ) {
        ps.setString(1, guestUsername);
        ps.setString(2, hashedPassword);
        ps.setString(3, phone);
        ps.setString(4, address);
        ps.setString(5, email);

        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1;
}




    private void closeAll() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}