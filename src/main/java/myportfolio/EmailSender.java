package myportfolio;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSender {
    public static void sendContactEmail(Contact contact) {
        // 本来は設定ファイルに書くべき情報
        final String from = "tamago.k.work@gmail.com";
        final String password = "tamago.k.work.1023"; 

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(from)); // 自分宛に届く
            message.setSubject("【お問い合わせ】" + contact.getName() + "様より");
            message.setText("名前: " + contact.getName() + "\n内容: \n" + contact.getBody());

            Transport.send(message);
            System.out.println("メール送信成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}