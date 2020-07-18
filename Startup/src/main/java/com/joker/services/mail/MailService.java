package com.joker.services.mail;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    void sendVerificationCode(String to, String subject, String text) throws UnsupportedEncodingException, MessagingException;
}
