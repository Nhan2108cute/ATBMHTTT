package services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private final String senderEmail = "tquan957@gmail.com"; // Email gửi đi
    private final String senderPassword = "chjr iowo jkjj tycy"; // Mật khẩu ứng dụng của Gmail

    public void sendEmail(String recipientEmail, String subject, String body) {
        // Cấu hình các thuộc tính kết nối SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Xác thực tài khoản email
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Tạo email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail)); // Địa chỉ email gửi đi
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail) // Địa chỉ email người nhận
            );
            message.setSubject(subject); // Tiêu đề email
            message.setText(body); // Nội dung email

            // Gửi email
            Transport.send(message);

            System.out.println("Email đã được gửi thành công đến " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Gửi email thất bại.");
        }
    }
}

