package com.joker.controller;

import com.joker.authentication.Mail;
import com.joker.databases.UsersDao;
import com.joker.helper.AuthenticationAction;
import com.joker.helper.MailDemo;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UsersDao users;

    @Autowired
    private Mail mailSender;

    @GetMapping
    public String register() {
        return "registration/register";
    }

    @PostMapping
    public ModelAndView registerUser(HttpSession session,
                                     @RequestParam String username,
                                     @RequestParam String mail,
                                     @RequestParam String password) throws UnsupportedEncodingException, MessagingException {
        User user = users.searchByUsernameAndMail(username, mail);
        if (user != null) {
            return new ModelAndView("registration/registerError");
        }

        String code = com.joker.helperClasses.RandomCodeGenerator.randomCode();
        mailSender.sendVerificationCode( mail, "Verification Code", code);

        session.setAttribute("user", new User(username, mail, password));
        session.setAttribute("mail", mail);
        session.setAttribute("code", code);
        session.setAttribute("action", AuthenticationAction.REGISTER);

        return new ModelAndView("redirect:/verifyCode");
    }
}
