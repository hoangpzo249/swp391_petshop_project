/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import DAO.OrderDAO;
import Models.Order;
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
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
            Order order = new OrderDAO().getOrderById(orderId);
            if (order.getPaymentMethod().equals("Credit Card")) {
                htmlContent.append("Xin vui lòng điền vào form này để được hoàn tiền.");
                htmlContent.append("<p><a href='https://docs.google.com/forms/d/e/1FAIpQLSelI0Ic97jVlEEvtDBADrbjvGHW4vkjT369P8KjQgCT5mvKkw/viewform?usp=sharing&ouid=110330081905382109968' ");
            }
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

    public static void sendShippingOrderByShipper(String receiveEmail, int orderId) {
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

    public static void sendShipperRejectOrder(String receiveEmail, int orderId) {
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
            mess.setSubject("PETFPT Shop: Đơn hàng #" + orderId + " đã bị từ chối nhận vận chuyển", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #e74c3c;'>Thông báo: Đơn hàng bị từ chối vận chuyển</h2>");
            htmlContent.append("<p>Xin chào Quý Nhà Bán,</p>");
            htmlContent.append("<p>Chúng tôi xin thông báo rằng đơn hàng <strong>#").append(orderId).append("</strong> đã bị <strong>Shipper từ chối nhận vận chuyển</strong>.</p>");

            htmlContent.append("<p>Lý do từ chối có thể liên quan đến kích thước hàng hóa, thời gian giao không phù hợp, hoặc lý do khác. Vui lòng kiểm tra lại thông tin đơn hàng và thực hiện điều phối lại đơn vị vận chuyển nếu cần thiết.</p>");
            htmlContent.append("<p>Quý Nhà Bán có thể truy cập hệ thống PETFPT để kiểm tra chi tiết và tiến hành các bước xử lý tiếp theo.</p>");

            htmlContent.append("<p>Chúng tôi xin lỗi vì sự bất tiện này và cảm ơn sự hợp tác của Quý Nhà Bán.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendDeliveredOrderByShipper(String receiveEmail, int orderId) {
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
            mess.setSubject("PETFPT Shop: Đơn hàng #" + orderId + " đã được giao thành công!", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #28a745;'>Đơn hàng của bạn đã được giao thành công!</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Chúng tôi xin thông báo rằng đơn hàng #").append(orderId)
                    .append(" của bạn tại PETFPT Shop đã được giao đến thành công.</p>");
            htmlContent.append("<p>Hy vọng bạn hài lòng với sản phẩm thú cưng đã nhận. Nếu có bất kỳ phản hồi hoặc vấn đề nào, đừng ngần ngại liên hệ với chúng tôi để được hỗ trợ kịp thời.</p>");
            htmlContent.append("<p>Cảm ơn bạn đã tin tưởng và đồng hành cùng PETFPT Shop.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendCancelledOrder(String receiveEmail, int orderId) {
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
            mess.setSubject("PETFPT Shop: Đơn hàng #" + orderId + " đã được hủy theo yêu cầu của bạn", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #dc3545;'>Đơn hàng đã được hủy thành công</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Đơn hàng <strong>#").append(orderId).append("</strong> của bạn tại <strong>PETFPT Shop</strong> đã được hủy thành công theo yêu cầu.</p>");
            htmlContent.append("<p>Vì đơn hàng này sử dụng phương thức <strong>thanh toán khi nhận hàng (COD)</strong>, nên bạn không cần thực hiện thêm bất kỳ thao tác nào.</p>");
            htmlContent.append("<p>Nếu bạn có bất kỳ câu hỏi hay cần hỗ trợ thêm, đừng ngần ngại liên hệ với chúng tôi.</p>");
            htmlContent.append("<p>Chúng tôi hy vọng sẽ tiếp tục được phục vụ bạn trong thời gian tới.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendCancelledOrderBanked(String receiveEmail, int orderId) {
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
            mess.setSubject("PETFPT Shop: Hủy đơn hàng #" + orderId + " thành công – Cung cấp thông tin hoàn tiền", "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");
            htmlContent.append("<h2 style='color: #dc3545;'>Hủy đơn hàng thành công</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Chúng tôi xác nhận rằng đơn hàng <strong>#").append(orderId).append("</strong> của bạn tại <strong>PETFPT Shop</strong> đã được hủy thành công.</p>");
            htmlContent.append("<p>Vì bạn đã thanh toán trước cho đơn hàng này, chúng tôi cần bạn cung cấp thông tin tài khoản để thực hiện hoàn tiền.</p>");
            htmlContent.append("<p>Vui lòng điền biểu mẫu tại liên kết dưới đây để chúng tôi xử lý hoàn tiền cho bạn:</p>");
            htmlContent.append("<p><a href='https://docs.google.com/forms/d/e/1FAIpQLSelI0Ic97jVlEEvtDBADrbjvGHW4vkjT369P8KjQgCT5mvKkw/viewform?usp=sharing&ouid=110330081905382109968' ")
                    .append("style='color: #f26f21; font-weight: bold;' target='_blank'>→ Điền thông tin hoàn tiền tại đây</a></p>");
            htmlContent.append("<p>Sau khi nhận được thông tin, chúng tôi sẽ xử lý hoàn tiền trong vòng 3-5 ngày làm việc.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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
        mess.setText("Xin chào,\n\nMã xác nhận đăng ký tài khoản của bạn là: " + otp + "\nMã OTP có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

    public static void sendOTP2FALogin(String receiveEmail, String otp, String role) throws MessagingException {
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
        mess.setSubject("Xác thực đăng nhập hệ thống - FPTPet Shop", "UTF-8");
        mess.setText(
                "Xin chào,\n\n"
                + "Bạn đang thực hiện đăng nhập vào hệ thống FPTPet Shop với vai trò: " + role + ".\n"
                + "Mã xác thực OTP của bạn là: " + otp + "\n"
                + "Mã OTP có hiệu lực trong vòng 5 phút.\n"
                + "Vui lòng không chia sẻ mã này cho bất kỳ ai để đảm bảo an toàn tài khoản.\n\n"
                + "Trân trọng,\nFPTPet Shop",
                "UTF-8"
        );
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
        mess.setText("Xin chào,\n\nMã xác nhận đặt lại mật khẩu tài khoản của bạn là: " + otp + "\nMã OTP có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này cho người khác.\n\nCảm ơn bạn!\nFPTPet Shop", "UTF-8");

        Transport.send(mess);
    }

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

    public static void registerInfo(String receiveEmail) throws MessagingException {

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

        mess.setSubject("Chào mừng bạn đến với FPTPet Shop!", "UTF-8");

        String messto = "Xin chào bạn,\n\n"
                + "Cảm ơn bạn đã đăng ký nhận thông tin từ FPTPet Shop! 🎉\n"
                + "Chúng tôi sẽ thường xuyên gửi đến bạn các cập nhật mới nhất về thú cưng, mẹo chăm sóc, và những ưu đãi hấp dẫn dành riêng cho bạn.\n\n"
                + "Nếu bạn không thực hiện đăng ký này, vui lòng bỏ qua email này hoặc liên hệ với chúng tôi để được hỗ trợ.\n\n"
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

    public static void sendConfirmOrderCustomer(String receiveEmail, int orderId, List<Pet> listpet, double discountAmount, double totalPrice) {
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
            if (discountAmount > 0) {
                htmlContent.append("<h3 style='text-align: right;'>Giảm giá: <span style='color: green;'>")
                        .append(currencyFormatter.format(discountAmount))
                        .append("</span></h3>");
            }
            htmlContent.append("<h3 style='text-align: right; margin-top: 10px;'>Tổng cộng: <span style='color: #f26f21;'>")
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

    public static void sendRejectOrderRefund(String receiveEmail, int orderId, double totalPrice) {
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

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");

            htmlContent.append("<h2 style='color: #e74c3c;'>Thông báo: Đơn hàng của bạn đã bị từ chối</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Chúng tôi rất tiếc phải thông báo rằng đơn hàng #")
                    .append(orderId)
                    .append(" của bạn không thể được xử lý vì <strong>thú cưng không còn khả dụng</strong>.</p>");

            htmlContent.append("<div style='background-color: #f9eaea; border-left: 4px solid #e74c3c; padding: 10px 15px; margin: 15px 0;'>");
            htmlContent.append("<p style='margin: 0;'><strong>Lý do từ chối:</strong> Thú cưng không còn khả dụng</p>");
            htmlContent.append("</div>");

            htmlContent.append("<h3 style='text-align: right; margin-top: 20px;'>Tổng giá trị đơn hàng: <span style='color: #333;'>")
                    .append(currencyFormatter.format(totalPrice))
                    .append("</span></h3>");

            htmlContent.append("<p>Vì bạn đã thanh toán cho đơn hàng này, chúng tôi sẽ tiến hành hoàn tiền trong thời gian sớm nhất.</p>");
            htmlContent.append("<p>Để quá trình hoàn tiền diễn ra chính xác, vui lòng điền thông tin vào biểu mẫu dưới đây và gửi kèm ảnh bằng chứng thanh toán:</p>");
            htmlContent.append("<p style='margin: 20px 0;'><a href='https://docs.google.com/forms/d/e/1FAIpQLSelI0Ic97jVlEEvtDBADrbjvGHW4vkjT369P8KjQgCT5mvKkw/viewform?usp=sharing&ouid=110330081905382109968' target='_blank' ")
                    .append("style='display: inline-block; background-color: #f26f21; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px;'>")
                    .append("Gửi thông tin hoàn tiền tại đây</a></p>");

            htmlContent.append("<p>Chúng tôi xin lỗi vì sự bất tiện này và mong được phục vụ bạn trong tương lai gần.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendRefundStatusUpdate(String receiveEmail, int orderId, String status, String reason, byte[] proofRefundedImage) {
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
            mess.setSubject("Cập nhật trạng thái hoàn tiền cho đơn hàng #" + orderId, "UTF-8");

            String statusText = switch (status) {
                case "Approved" ->
                    "<span style='color: green; font-weight: bold;'>ĐÃ DUYỆT</span>";
                case "Rejected" ->
                    "<span style='color: red; font-weight: bold;'>TỪ CHỐI</span>";
                case "Completed" ->
                    "<span style='color: blue; font-weight: bold;'>THÀNH CÔNG</span>";
                case "Incomplete" ->
                    "<span style='color: orange; font-weight: bold;'>KHÔNG THÀNH CÔNG</span>";
                default ->
                    "<span style='color: gray;'>ĐANG CHỜ</span>";
            };

            StringBuilder html = new StringBuilder();
            html.append("<html><body style='font-family: Arial, sans-serif;'>")
                    .append("<h2 style='color: #f26f21;'>PETFPT Shop - Cập nhật hoàn tiền</h2>")
                    .append("<p>Xin chào,</p>")
                    .append("<p>Yêu cầu hoàn tiền của bạn cho đơn hàng <strong>#").append(orderId).append("</strong> đã được cập nhật:</p>")
                    .append("<p><strong>Trạng thái mới: </strong>").append(statusText).append("</p>");

            if (reason != null && !reason.trim().isEmpty()) {
                html.append("<p><strong>Lý do: </strong>").append(reason).append("</p>");
            }
            if ("Completed".equalsIgnoreCase(status) && proofRefundedImage != null) {
                html.append("<p><strong>Ảnh bằng chứng đã hoàn tiền được đính kèm bên dưới.</strong></p>");
                MimeMultipart multipart = new MimeMultipart();

                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(html.toString(), "text/html; charset=utf-8");
                multipart.addBodyPart(htmlPart);

                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setFileName("proof_refunded_image.jpg");
                imagePart.setContent(proofRefundedImage, "image/jpeg");
                multipart.addBodyPart(imagePart);

                mess.setContent(multipart);
            } else {
                mess.setContent(html.toString(), "text/html; charset=utf-8");
            }

            html.append("<p>Vui lòng đăng nhập để xem chi tiết.</p>")
                    .append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>")
                    .append("</body></html>");

            Transport.send(mess);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendNewDeliveryAssignment(String shipperEmail, int orderId) {
        final String senderEmail = "fptpet@gmail.com";
        final String senderPassword = "mfjm zfut ledv svkn";

        Properties pro = new Properties();
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.starttls.enable", "true");
        pro.put("mail.smtp.host", "smtp.gmail.com");
        pro.put("mail.smtp.port", "587");

        Session session = Session.getInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage mess = new MimeMessage(session);

            mess.setFrom(new InternetAddress(senderEmail));
            mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(shipperEmail));
            mess.setSubject("Thông báo Giao hàng Mới - Đơn hàng #" + orderId, "UTF-8");

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>");
            htmlContent.append("<div style='max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;'>");
            htmlContent.append("<h2 style='color: #007bff;'>Bạn có một đơn hàng giao mới!</h2>");
            htmlContent.append("<p>Xin chào,</p>");
            htmlContent.append("<p>Bạn vừa được chỉ định để thực hiện giao hàng cho đơn hàng có mã số sau:</p>");
            htmlContent.append("<h3 style='background-color: #f2f2f2; padding: 15px; border-left: 5px solid #007bff; font-size: 1.2em;'>Mã đơn hàng: #")
                    .append(orderId)
                    .append("</h3>");
            htmlContent.append("<p>Vui lòng truy cập vào trang quản lý (dashboard) của bạn để xem thông tin chi tiết về địa chỉ giao hàng và các sản phẩm trong đơn.</p>");
            htmlContent.append("<p style='text-align: center; margin: 30px 0;'>");
            htmlContent.append("<a href='http://localhost:8080/PetShopFPT/shipper_panel' style='background-color: #28a745; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;'>Xem chi tiết trên Dashboard</a>");
            htmlContent.append("</p>");
            htmlContent.append("<p>Cảm ơn sự hợp tác của bạn.</p>");
            htmlContent.append("<p>Trân trọng,<br>Đội ngũ PETFPT Shop</p>");
            htmlContent.append("</div>");
            htmlContent.append("</body></html>");

            mess.setContent(htmlContent.toString(), "text/html; charset=utf-8");
            Transport.send(mess);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
