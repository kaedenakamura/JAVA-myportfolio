package myportfolio;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSender {

    public static void sendContactEmail(Contact contact) {

        // Gmailアドレス
        final String username = "tamago.k.work@gmail.com";

        // Googleアプリパスワード
        final String password = "dfxv zoff wzha tmtd";

        // SMTP設定
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // ログイン認証
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            // メール本体作成
            Message message = new MimeMessage(session);

            // 送信元
            message.setFrom(new InternetAddress(username));

            // 送信先（自分宛）
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(username)
            );

            // 件名
            message.setSubject("【お問い合わせ】" + contact.getName() + "様より");

            // 本文
            String body =
                    "【お問い合わせ内容】\n\n"
                    + "名前: " + contact.getName() + "\n"
                    + "カテゴリID: " + contact.getCategory() + "\n\n"
                    + "内容:\n"
                    + contact.getBody();

            message.setText(body);

            // 送信
            Transport.send(message);

            System.out.println("メール送信成功！");

        } catch (Exception e) {

            System.out.println("メール送信失敗");

            e.printStackTrace();
        }
    }
}