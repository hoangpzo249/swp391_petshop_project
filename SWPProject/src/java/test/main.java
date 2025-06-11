/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class main {

    public static void main(String[] args) {
        // test khi làm phần add của discountDao
        System.out.println("hehe");
        float testFloat = Float.parseFloat("0.02");
        System.out.println(testFloat);

        //test phần Date của discountDAO
        Date now = new Date(125, 1, 14);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(dateFormat.format(now));
        Date after = new Date(126, 1, 24);
        System.out.println(after.after(now));
 
//        gemini code
//        LocalDate localDate = LocalDate.of(2025, 6, 7);
//        Date sqlDate = Date.valueOf(localDate); // Tạo từ LocalDate
//
//        System.out.println("Kiểu của đối tượng: " + sqlDate.getClass().getName());
//        System.out.println("Giá trị: " + sqlDate);
    }
}
