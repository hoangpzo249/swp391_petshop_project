/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Models.Pet;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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

    public static void sendConfirmOrder(String receiveEmail, int orderId, List<Pet> listpet, double totalPrice) {
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
        try {
            MimeMessage mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(senderEmail));
            mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
            mess.setSubject("Xác nhận đơn hàng #" + orderId + " - Cảm ơn bạn đã mua sắm tại PETFPT Shop!", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #f26f21;'>Cảm ơn bạn đã đặt hàng tại PETFPT Shop!</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Chúng tôi đã nhận được đơn hàng của bạn và đang tiến hành xử lý. Dưới đây là thông tin chi tiết về đơn hàng của bạn:</p>");
            htmlContent.append("<h3>Thông tin đơn hàng: #" + orderId + "</h3>");
            htmlContent.append("<table border='1' cellpadding='10' cellspacing='0' style='width: 100%; border-collapse: collapse;'>");
            htmlContent.append("<tr style='background-color: #f2f2f2;'><th>Tên thú cưng</th><th>Giống</th><th>Giá</th></tr>");

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            for (Pet pet : listpet) {
                htmlContent.append("<tr>");
                htmlContent.append("<td>").append(pet.getPetName()).append("</td>");
                htmlContent.append("<td>").append(pet.getBreed().getBreedName()).append("</td>");
                double price = (pet.getPriceAtOrder() > 0) ? pet.getPriceAtOrder() : pet.getPetPrice();
                htmlContent.append("<td style='text-align: right;'>").append(currencyFormatter.format(price)).append("</td>");
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");
            htmlContent.append("<h3 style='text-align: right; margin-top: 20px;'>Tổng cộng: <span style='color: #f26f21;'>")
                    .append(currencyFormatter.format(totalPrice))
                    .append("</span></h3>");
            htmlContent.append("<p>Chúng tôi sẽ thông báo cho bạn khi đơn hàng được vận chuyển. Bạn có thể theo dõi trạng thái đơn hàng trong mục 'Lịch sử mua hàng' trên website của chúng tôi.</p>");
            htmlContent.append("<p>Cảm ơn bạn một lần nữa!</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendRejectOrder(String receiveEmail, int orderId, List<Pet> listpet, double totalPrice, String reason) {
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

        try {
            MimeMessage mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(senderEmail));
            mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
            mess.setSubject("Thông báo về đơn hàng #" + orderId + " tại PETFPT Shop", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #e74c3c;'>Thông báo: Đơn hàng của bạn đã bị từ chối</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Chúng tôi rất tiếc phải thông báo rằng đơn hàng #").append(orderId).append(" của bạn đã không thể được xử lý và đã bị từ chối.</p>");

            htmlContent.append("<div style='background-color: #f9eaea; border-left: 4px solid #e74c3c; padding: 10px 15px; margin: 15px 0;'>");
            htmlContent.append("<p style='margin: 0;'><strong>Lý do từ chối:</strong> ").append(reason).append("</p>");
            htmlContent.append("</div>");

            htmlContent.append("<p>Dưới đây là chi tiết các sản phẩm trong đơn hàng đã bị từ chối:</p>");

            htmlContent.append("<table border='1' cellpadding='10' cellspacing='0' style='width: 100%; border-collapse: collapse;'>");
            htmlContent.append("<tr style='background-color: #f2f2f2;'><th>Tên thú cưng</th><th>Giống</th><th>Giá</th></tr>");

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            for (Pet pet : listpet) {
                htmlContent.append("<tr>");
                htmlContent.append("<td>").append(pet.getPetName()).append("</td>");
                htmlContent.append("<td>").append(pet.getBreed().getBreedName()).append("</td>");
                double price = (pet.getPriceAtOrder() > 0) ? pet.getPriceAtOrder() : pet.getPetPrice();
                htmlContent.append("<td style='text-align: right;'>").append(currencyFormatter.format(price)).append("</td>");
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");

            htmlContent.append("<h3 style='text-align: right; margin-top: 20px;'>Tổng giá trị đơn hàng: <span style='color: #333;'>")
                    .append(currencyFormatter.format(totalPrice))
                    .append("</span></h3>");

            htmlContent.append("<p>Nếu bạn đã thanh toán trước cho đơn hàng này, khoản tiền sẽ được hoàn lại cho bạn trong thời gian sớm nhất. Vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi nếu bạn có bất kỳ câu hỏi nào.</p>");
            htmlContent.append("<p>Chúng tôi thành thật xin lỗi vì sự bất tiện này và mong có cơ hội phục vụ bạn trong tương lai.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendShippingOrder(String receiveEmail, int orderId, List<Pet> listpet, double totalPrice) {
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

        try {
            MimeMessage mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(senderEmail));
            mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiveEmail));
            mess.setSubject("PETFPT Shop: Đơn hàng #" + orderId + " của bạn đã được vận chuyển!", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #3498db;'>Đơn hàng của bạn đang trên đường đến!</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Tin vui! Đơn hàng #").append(orderId).append(" của bạn tại PETFPT Shop đã được bàn giao cho đơn vị vận chuyển.</p>");

//            htmlContent.append("<div style='background-color: #eaf2f8; border-left: 4px solid #3498db; padding: 10px 15px; margin: 15px 0;'>");
//            htmlContent.append("<p style='margin: 0;'><strong>Đơn vị vận chuyển:</strong> ").append(shippingCarrier).append("</p>");
//            htmlContent.append("<p style='margin: 0;'><strong>Mã vận đơn (Tracking Number):</strong> ").append(trackingNumber).append("</p>");
//            htmlContent.append("</div>");

            htmlContent.append("<p>Dưới đây là chi tiết các sản phẩm trong đơn hàng của bạn:</p>");

            htmlContent.append("<table border='1' cellpadding='10' cellspacing='0' style='width: 100%; border-collapse: collapse;'>");
            htmlContent.append("<tr style='background-color: #f2f2f2;'><th>Tên thú cưng</th><th>Giống</th><th>Giá</th></tr>");

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            for (Pet pet : listpet) {
                htmlContent.append("<tr>");
                htmlContent.append("<td>").append(pet.getPetName()).append("</td>");
                htmlContent.append("<td>").append(pet.getBreed().getBreedName()).append("</td>");
                double price = (pet.getPriceAtOrder() > 0) ? pet.getPriceAtOrder() : pet.getPetPrice();
                htmlContent.append("<td style='text-align: right;'>").append(currencyFormatter.format(price)).append("</td>");
                htmlContent.append("</tr>");
            }
            htmlContent.append("</table>");

            htmlContent.append("<h3 style='text-align: right; margin-top: 20px;'>Tổng cộng: <span style='color: #f26f21;'>")
                    .append(currencyFormatter.format(totalPrice))
                    .append("</span></h3>");

            htmlContent.append("<p>Vui lòng chuẩn bị nhận hàng và kiểm tra thú cưng cẩn thận khi giao tới. Nếu có bất kỳ vấn đề gì, xin liên hệ với chúng tôi ngay lập tức.</p>");
            htmlContent.append("<p>Cảm ơn bạn đã tin tưởng và mua sắm tại PETFPT Shop.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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
        mess.setText("Xin chào,\n\nMã xác nhận đăng ký tài khoản của bạn là: " + otp + "\nMã OTP có hiệu lực trong 3 phút. Vui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

    public static void sendOTPRecover(String receiveEmail, String otp) throws MessagingException {
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
        mess.setSubject("Mã xác nhận đặt lại mật khẩu - FPTPet Shop", "UTF-8");
        mess.setText("Xin chào,\n\nMã xác nhận đặt lại mật khẩu tài khoản của bạn là: " + otp + "\nMã OTP có hiệu lực trong 3 phút. Vui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

//    public static void sendOTPChangeEmail(String receiveEmail, String otp) throws MessagingException {
//        final String senderEmail = "kaded429@gmail.com";
//        final String senderPassword = "kwfo nzqf nrmz cvlx";
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

    public static void sendUpdateRole(String receiveEmail, String fullName) throws MessagingException {
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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Chào mừng bạn đến với FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!"
                + "\nBạn đã cập nhật vai trò mới\n"
                + "Đăng nhập tài khoản để xem thêm thông tin.\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void sendBanAcc(String receiveEmail, String fullName, String reasonBan) throws MessagingException {
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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Chào mừng bạn đến với FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!"
                + "\nTài khoản của bạn đã bị vô hiệu hóa với lí do sau:\n" + reasonBan + "\n\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }

    public static void sendUnBanAcc(String receiveEmail, String fullName) throws MessagingException {
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
//        mess.setSubject("Mật khẩu mới của bạn - FPTPet Shop", "UTF-8");
//        mess.setText("Xin chào,\n\nMật khẩu mới tài khoản của bạn là: " + newpass + "\nVui lòng không chia sẻ này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        mess.setSubject("Chào mừng bạn đến với FPTPet Shop!", "UTF-8");

        String messto = "Xin chào, " + fullName + "!"
                + "\nTài khoản của bạn đã được mở khóa thành công\n"
                + "Trân trọng,\n"
                + "FPTPet Shop Team";
        mess.setText(messto, "UTF-8");

        Transport.send(mess);
    }
}
