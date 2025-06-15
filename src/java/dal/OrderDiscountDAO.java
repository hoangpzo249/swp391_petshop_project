/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class OrderDiscountDAO {
    // This function was created to check is the discount id is existed, if it have not exist in any order
    // we can delete it, if it exist, we send back error to inform to the client
    public boolean isExist(String discountId){
        boolean result = false; 
        PreparedStatement prestatement = null;
        return result;
    }
}
