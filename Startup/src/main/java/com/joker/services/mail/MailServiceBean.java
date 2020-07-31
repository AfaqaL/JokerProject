package com.joker.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * A utility class for sending e-mail messages
 *
 * @author www.codejava.net
 */
@Service("mailServiceSender")
public class MailServiceBean implements MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Qualifier("getJavaMailSender")
    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationCode(
            String to, String subject, String text) throws UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setFrom(from, "Yomarbazi.ge");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(mimeMessage);
    }
}

