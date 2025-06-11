/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
//import java.util.Date;
import model.Discount;

import java.util.ArrayList;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DiscountDAO extends DBContext {
    

//        R-1
    public ArrayList<Discount> getAllDiscountFromDatabase() {
        ArrayList<Discount> discountList = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String sql = "select * from discountTb";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Discount presentDiscount = new Discount();
                presentDiscount.setDiscountId(resultSet.getInt("discountid"));
                presentDiscount.setDiscountCode(resultSet.getString("discountCode"));
                presentDiscount.setDiscountType(resultSet.getString("discounttype"));
                presentDiscount.setDisCountValue(resultSet.getFloat("disCountValue"));
                presentDiscount.setDescription(resultSet.getString("description"));
                presentDiscount.setValidFrom(resultSet.getDate("validFrom"));
                presentDiscount.setValidTo(resultSet.getDate("validTo"));
                presentDiscount.setMinOrderAmount(resultSet.getInt("minOrderAmount"));
                presentDiscount.setMaxUsage(resultSet.getInt("maxUsage"));
                presentDiscount.setUsageCount(resultSet.getInt("usageCount"));
                presentDiscount.setIsActive(resultSet.getBoolean("isActive"));
                discountList.add(presentDiscount);
            }
        } catch (SQLException e) {
            System.err.println("Đã có lõi khi thực thi sql" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                    System.out.println("đã đóng resultSet");
                } catch (Exception e) {
                    System.out.println("Lỗi khi đóng ResultSet" + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                    System.out.println("đã đóng statement");
                } catch (Exception e) {
                    System.err.println("Đã có khi đóng statement: " + e.getMessage());
                }
            }
        }
        return discountList;
    }

//   U: 2
    public boolean UpdateDiscountFromDatabase(Discount updated) {
        int rowAffected = 0;
        PreparedStatement preStatement = null;
        try {
            String sql = "update DiscountTB "
                    + "set discountcode = ?"
                    + " where discountid = ? ";
            preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, updated.getDiscountCode());
            preStatement.setInt(2, updated.getDiscountId());
            rowAffected = preStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Đã có lỗi khi thực thi sql" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (preStatement != null) {
                try {
                    preStatement.close();
                    System.out.println("đã đóng prestatement");
                } catch (SQLException e) {
                    System.err.println("Đã có lỗi khhi đóng preStatement: " + e.getMessage());
                }
            }
        }
        return rowAffected == 1;
    }

    // 5  
    public boolean UpdateDiscountStatus(int updatedDiscountId, boolean status) {
        int rowAffected = 0;
        PreparedStatement prestatement = null;
//        int bitValue = status==true?1:0; //this return value from boolean
        try {
            String sql = "Update DiscountTB Set isActive = ?  where discountId = ? ";
            prestatement = connection.prepareStatement(sql);
            prestatement.setBoolean(1, status);
            prestatement.setInt(2, updatedDiscountId);
            rowAffected = prestatement.executeUpdate();
        } catch (Exception e) {
            System.err.println("Đã có lỗi khi thực thi sql: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (prestatement != null) {
                try {
                    prestatement.close();
                    System.out.println("đã đóng preparestatement");
                } catch (Exception e) {
                    System.err.println("Đã có lỗi khi đóng prepareStatement");
                }
            }
        }
        return rowAffected == 1;
    }

    // C: 3   
    public boolean createDiscountToDatabase(Discount added) {
        int rowAffected = 0;
        PreparedStatement preStatement = null;
        try {
            String sql = "insert into DiscountTB "
                    + "(discountCode,discountType,discountValue,description,"
                    + "validFrom,validTo,minOrderAmount,maxUsage,isActive)"
                    + " values( ?, ?, ?, ?, "
                    + "?, ?, ?, ?, ?) ";
            preStatement = connection.prepareStatement(sql);
            
            preStatement.setString(1, added.getDiscountCode());
            preStatement.setString(2, added.getDiscountType());
            preStatement.setFloat(3, added.getDisCountValue());
            preStatement.setString(4, added.getDescription());
            preStatement.setDate(5, new Date(added.getValidFrom().getTime()));
            preStatement.setDate(6, new Date(added.getValidTo().getTime()));
            preStatement.setInt(7, added.getMinOrderAmount());
            preStatement.setInt(8, added.getMaxUsage());
            preStatement.setBoolean(9, added.isIsActive());

            rowAffected = preStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("đã có lỗi xảy ra khi add: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (preStatement != null) {
                try {
                    preStatement.close();
                    System.out.println("đã đóng prestatment");
                } catch (Exception e) {
                    System.err.println("đã có lỗi khi đóng prestatement: " + e.getMessage());
                }
            }
        }
        return rowAffected == 1;
    }

    // D: 4
    public boolean deleteDiscountInDataBase(int deletedDiscountId) {
        int rowAffected = 0;
        PreparedStatement preStatement = null;
        try {
            String sql = "Delete From DisCountTB "
                    + "Where DiscountId = ? ";
            preStatement = connection.prepareStatement(sql);
            preStatement.setInt(1, deletedDiscountId);
            rowAffected = preStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Đã có lỗi khi xóa Discount: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (preStatement != null) {
                try {
                    preStatement.close();
                    System.out.println("đã đóng PreparedStatement");
                } catch (Exception e) {
                    System.err.println("Đã có lỗi khi đóng PreStatement: " + e.getMessage());
                }
            }
        }
        return rowAffected == 1;
    }

    public boolean isExistDiscountCode(String discountCode) {
        boolean isExist = false;
        PreparedStatement preStatement = null;
        ResultSet rs = null;
        try {
            String sql = "select Count(*) from discountTB where discountCode = ? ";
            preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, discountCode);
            rs = preStatement.executeQuery();
            while (rs.next()) {
                int rowContainCode = rs.getInt(1);
                if (rowContainCode != 0) {
                    isExist = true;
                }
            }
        } catch (Exception e) {
            System.err.println("Có lỗi khi truy vấn");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    System.err.println(" có lỗi khi đóng resultset");
                }
            }
            if (preStatement != null) {
                try {
                    preStatement.close();
                } catch (Exception e) {
                    System.err.println("Có lỗi khi đóng Statement");
                }
            }
        }
        return isExist;
    }

    public static void main(String[] args) {
        DiscountDAO UnitTsstDao = new DiscountDAO();
        // unit test for All
        // 1. Unit test for getAll
        System.out.println("trước khi làm gì đó");
        ArrayList<Discount> discountList = UnitTsstDao.getAllDiscountFromDatabase();
        for (Discount discount : discountList) {
            System.out.println(discount);
        }

        // 2. unit test for Update 
//        Discount updated = discountList.get(0);
//        updated.setDiscountCode("chay duoc roi");
//        if (UnitTsstDao.UpdateDiscountFromDatabase(updated)) {
//            System.out.println("update thành công");
//        } else {
//            System.out.println("up date failed");
//        }
//        System.out.println("Result: ");
//        discountList = UnitTsstDao.getAllDiscountFromDatabase();
        // 3.Unit test for add - đa phần sẽ chuyển từ String sang
//        Discount added = new Discount();
//        added.setDiscountCode("hehe");
//        added.setDiscountType("Fixed");
//        added.setDisCountValue((float) 0.05);
//        added.setDescription("test cho description");
//        added.setValidFrom(new Date(125, 5, 5));
//        added.setValidTo(new Date(125, 5, 10));
//        added.setMinOrderAmount(1200000);
//        added.setMaxUsage(30);
//        added.setIsActive(true);
//        if (UnitTsstDao.createDiscountToDatabase(added)) {
//            System.out.println("đã add thành công");
//        } else {
//            System.out.println("Addfailed");
//        }
        // Unit test for delete a Discount
//        int test = 6;
//        if (UnitTsstDao.deleteDiscountInDataBase(test)) {
//            System.out.println("delete thành công ");
//        } else {
//            System.out.println("delete Failed");
//        }
        // 5.Unit test for updateStatus       
//        if (UnitTsstDao.UpdateDiscountStatus(1, false)) {
//            System.out.println("update thành công");
//        } else {
//            System.out.println("upodate failed");
//        }
        //6. unit test for checkExistCode
        String testCode = "chay duoc roI";
//        ở đây thì database có hành vi ko kiểu tra thừa 1 dấu cách- liệu nhiều dấu thì sao? cũng vẫn chạy bth
        if (UnitTsstDao.isExistDiscountCode(testCode+"      ")) {
            System.out.println("đã tìm thấy discount code: "+testCode);
        } else {
            System.out.println("không tìm thấy discount code: "+testCode+" trong database");
        }
        // 1. Unit test for getAll
        System.out.println("sau khi làm gì đó; ");
        discountList = UnitTsstDao.getAllDiscountFromDatabase();
        for (Discount discount : discountList) {
            System.out.println(discount);
        }
    }
}
