package com.joker.controller;

import com.joker.model.AuthenticationAction;
import com.joker.services.mail.MailServiceBean;
import com.joker.helper.*;
import com.joker.model.User;
import com.joker.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailServiceBean mailServiceBeanSender;

    @GetMapping
    public String register() {
        return "registration/register";
    }

    @PostMapping
    public ModelAndView registerUser(HttpSession session,
                                     @RequestParam String username,
                                     @RequestParam String mail,
                                     @RequestParam String password) {
        User user = userService.getByUsernameAndMail(username, mail);
        if (user != null) {
            session.setAttribute("authorised", false);
            return new ModelAndView("registration/registerError");
        }

        String code = RandomCodeGenerator.randomCode();
        try {
            mailServiceBeanSender.sendVerificationCode(mail, "Verification Code", code);
        } catch (MessagingException | UnsupportedEncodingException m) {
            return new ModelAndView("registration/registerError");
        }

        session.setAttribute("sentCode", true);
        session.setAttribute("user", new User(username, mail, password));
        session.setAttribute("mail", mail);
        session.setAttribute("code", code);
        session.setAttribute("action", AuthenticationAction.REGISTER);

        return new ModelAndView("redirect:/verifyCode");
    }
}
