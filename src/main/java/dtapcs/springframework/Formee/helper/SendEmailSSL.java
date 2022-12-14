package dtapcs.springframework.Formee.helper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendEmailSSL {
    static Session currentSession = null;

    private static Session CreateSession() {
        final String username = "22u.folder.2@gmail.com";
        final String password = "uxdszvsdrgfnoycg";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    private static boolean IsGmail(String gmail) {
        //check if gmail has right syntax
        return true;
    }

    public static void SendEmail(String title, String content, String recipients) {
        if (!IsGmail(recipients)) {
            return;
        }
        if (currentSession == null) {
            currentSession = CreateSession();
        }

        try {
            MimeMessage message = new MimeMessage(currentSession);
            InternetAddress address = new InternetAddress("from@gmail.com", "Hệ thống Formee");
            String personal = address.getPersonal();
            if (personal != null) {
                address.setPersonal(personal, "utf-8");
            }
            message.setFrom(address);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipients)
            );
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setSubject(title, "UTF-8");
            message.setContent(content, "text/plain;charset=UTF-8");

            Transport.send(message);
            System.out.println("Email sent to: " + recipients);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
