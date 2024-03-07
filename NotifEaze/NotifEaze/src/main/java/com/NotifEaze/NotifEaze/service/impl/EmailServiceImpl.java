package com.NotifEaze.NotifEaze.service.impl;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import org.apache.logging.log4j.message.Message;
import org.springframework.boot.rsocket.server.RSocketServer.Transport;

import com.NotifEaze.NotifEaze.dto.BaseEmailResponse;
import com.NotifEaze.NotifEaze.service.EmailService;

import jakarta.websocket.Session;

public class EmailServiceImpl implements EmailService {

    // SMTP server configuration
    private static final String SMTP_HOST = "smtp.example.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USERNAME = "your_username";
    private static final char[] SMTP_PASSWORD = "your_password";

    @Override
    public BaseEmailResponse sendEmail(String to, String subject, String body) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", SMTP_HOST);
        properties.setProperty("mail.smtp.port", SMTP_PORT);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(SMTP_USERNAME));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);
            return new BaseEmailResponse(true, "Email sent successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return new BaseEmailResponse(false, "Failed to send email: " + mex.getMessage());
        }
    }
}
