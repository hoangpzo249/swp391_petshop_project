/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;

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
        Date now = new Date(125, 5, 4);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy//hh/mm/ss");
        System.out.println(dateFormat.format(now));
//        gemini code
        LocalDate localDate = LocalDate.of(2025, 6, 7);
        Date sqlDate = Date.valueOf(localDate); // Tạo từ LocalDate

        System.out.println("Kiểu của đối tượng: " + sqlDate.getClass().getName());
        System.out.println("Giá trị: " + sqlDate);
    }
}
