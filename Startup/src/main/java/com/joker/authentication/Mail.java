package com.joker.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * A utility class for sending e-mail messages
 *
 * @author www.codejava.net
 */
@Service("mail")
public class Mail {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationCode(
            String to, String subject, String text) throws UnsupportedEncodingException, MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper message =new MimeMessageHelper(mimeMessage,true);
        message.setFrom(from, "Yomarbazi.ge");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(mimeMessage);
    }
}

