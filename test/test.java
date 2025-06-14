
import DAO.AccountDAO;
import java.util.Random;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author HuyHoang
 */
public class test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Nhập username: ");
        String input = sc.nextLine();
        boolean checkname = checkName(input);
        if(checkname) {
            System.out.println("Hợp lệ");
        } else {
            System.err.println("Error");
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

    public static String genUsername(String email) {
        String username = email.substring(0, email.indexOf("@"));
        AccountDAO accdao = new AccountDAO();
        String newUsername = "";

        boolean existUsername = true;

        while (existUsername) {
            Random random = new Random();
            int randomNumber = 100000 + random.nextInt(1000000);

            newUsername = username + randomNumber;

            existUsername = accdao.isUsernameExist(newUsername);
        }
        return newUsername;
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if ("!@#$%^&*()_+-=[]{}|;:'\",.<>?/`~".indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static boolean checkName(String name) {
        String checkName = "^[a-z0-9_]{5,30}$";
            return name.matches(checkName);
    }

}
