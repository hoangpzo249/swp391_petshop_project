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
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
        mess.setText("Xin chào,\n\nMã xác nhận đăng ký tài khoản của bạn là: " + otp + "\nMã OTP có hiệu lực trong 3 phút. Vui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

    public static void sendOTPRecover(String receiveEmail, String otp) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
        mess.setSubject("Mã xác nhận đặt lại mật khẩu - FPTPet Shop", "UTF-8");
        mess.setText("Xin chào,\n\nMã xác nhận đặt lại mật khẩu tài khoản của bạn là: " + otp + "\nMã OTP có hiệu lực trong 3 phút. Vui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

//    public static void sendOTPChangeEmail(String receiveEmail, String otp) throws MessagingException {
//        final String senderEmail = "fptpet@gmail.com";
//        final String senderPassword = "mfjm zfut ledv svkn";
//
//        Properties pro = new Properties();
//        pro.put("mail.smtp.auth", "true");
//        pro.put("mail.smtp.starttls.enable", "true");
//        pro.put("mail.smtp.host", "smtp.gmail.com");
//        pro.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(pro, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmail, senderPassword);
//            }
//        });
//
//        MimeMessage mess = new MimeMessage(session);
//        mess.setFrom(new InternetAddress(senderEmail));
//        mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
//        mess.setSubject("Mã xác nhận đổi tài khoản Email - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMã xác nhận đổi tài khoản Email của bạn là: " + otp + "\nVui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");
//
//        Transport.send(mess);
//    }
    public static void registerSuccess(String receiveEmail, String fullName) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Chào mừng bạn đến với FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!"
                + "\nBạn đã đăng ký tài khoản thành công tại FPTPet Shop.\n"
                + "Chúc bạn có trải nghiệm mua sắm thú vị cùng chúng tôi!\n\n"
                + "Nếu bạn không thực hiện đăng ký này, vui lòng liên hệ với chúng tôi để được hỗ trợ.\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void createAccCusSuccess(String receiveEmail, String fullName, String emailCus, String passCus) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Chào mừng bạn đến với FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!\n\n"
                + "Bạn đã được tạo tài khoản thành công tại FPTPet Shop.\n"
                + "Thông tin tài khoản của bạn:\n"
                + "- Email: " + emailCus + "\n"
                + "- Mật khẩu tạm thời: " + passCus + "\n\n"
                + "Hãy đăng nhập vào hệ thống và đổi mật khẩu để bảo mật tài khoản.\n"
                + "Nếu bạn không thực hiện yêu cầu này, hãy liên hệ với chúng tôi sớm nhất.\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void createAccStaffSuccess(String receiveEmail, String fullName, String emailStaff, String passStaff, String roleStaff) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Tài khoản nhân viên mới tại FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!\n\n"
                + "Bạn đã được thêm vào hệ thống FPTPet Shop với vai trò: " + roleStaff + ".\n\n"
                + "Thông tin đăng nhập:\n"
                + "- Email: " + emailStaff + "\n"
                + "- Mật khẩu tạm thời: " + passStaff + "\n\n"
                + "Hãy đăng nhập vào hệ thống và đổi mật khẩu của bạn để bảo mật tài khoản của bạn.\n"
                + "Nếu bạn không phải người nhận, hãy liên hệ quản trị viên.\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void sendUpdateRole(String receiveEmail, String fullName, String oldRole, String newRole) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Thông báo cập nhật vai trò tại FPTPet Shop", "UTF-8");

        String messto = "Xin chào, " + fullName + "!\n\n"
                + "Thông báo: Vai trò tài khoản của bạn tại FPTPet Shop đã được cập nhật.\n"
                + "- Vai trò cũ: " + oldRole + "\n"
                + "- Vai trò mới: " + newRole + "\n\n"
                + "Vui lòng đăng nhập vào hệ thống để xem các quyền hạn và chức năng mới được cấp.\n"
                + "Nếu bạn không yêu cầu thay đổi này, vui lòng liên hệ với quản trị viên để được hỗ trợ.\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }
    
    public static void sendResetPassByAdmin(String receiveEmail, String fullName, String email, String newpass) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Mật khẩu tài khoản của bạn đã được cập nhật tại FPTPet Shop", "UTF-8");

        String messto = "Xin chào, " + fullName + "!\n\n"
                + "Mật khẩu của tài khoản bạn tại FPTPet Shop đã được đặt lại bởi quản trị viên.\n\n"
                + "Thông tin tài khoản:\n"
                + "- Email: " + email + "\n"
                + "- Mật khẩu mới: " + newpass + "\n\n"
                + "Bạn đăng nhập và đổi mật khẩu ngay để đảm bảo an toàn cho tài khoản.\n"
                + "Nếu bạn không yêu cầu thay đổi này, vui lòng liên hệ bộ phận hỗ trợ của chúng tôi ngay lập tức.\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void sendBanAcc(String receiveEmail, String fullName, String reasonBan) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Thông báo từ FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!"
                + "\nTài khoản của bạn đã bị vô hiệu hóa với lí do sau:\n" + reasonBan + "\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void sendUnBanAcc(String receiveEmail, String fullName) throws MessagingException {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Thông báo từ FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!"
                + "\nTài khoản của bạn đã được mở khóa thành công\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }
}
