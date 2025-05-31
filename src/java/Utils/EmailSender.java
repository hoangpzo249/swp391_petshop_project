/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author HuyHoang
 */
public class EmailSender {

    public static void sendOTP(String receiveEmail, String otp) throws MessagingException {
        final String senderEmail = "kaded429@gmail.com";
        final String senderPassword = "kwfo nzqf nrmz cvlx";

        Properties pro = new Properties();
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");

        Session session = Session.getInstance(pro, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        MimeMessage mess = new MimeMessage(session);
        mess.setFrom(new InternetAddress(senderEmail));
        mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
        mess.setSubject("Mã xác nhận đăng ký - FPTPet Shop", "UTF-8");
        mess.setText("Xin chào,\n\nMã xác nhận đăng ký tài khoản của bạn là: " + otp + "\nVui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

    public static void recoverPass(String receiveEmail, String newpass) throws MessagingException {
        final String senderEmail = "kaded429@gmail.com";
        final String senderPassword = "kwfo nzqf nrmz cvlx";

        Properties pro = new Properties();
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");

        Session session = Session.getInstance(pro, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        MimeMessage mess = new MimeMessage(session);
        mess.setFrom(new InternetAddress(senderEmail));
        mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }
}
