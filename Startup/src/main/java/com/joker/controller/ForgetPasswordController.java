package com.joker.controller;

import com.joker.services.mail.MailService;
import com.joker.model.enums.AuthenticationAction;
import com.joker.helper.RandomCodeGenerator;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/forget-password")
public class ForgetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailServiceSender;

    @GetMapping
    public String forgetPassword() {
        return "forgetPassword/forgetPassword";
    }

    @PostMapping
    public ModelAndView validateMail(HttpSession ses, HttpServletResponse resp, @RequestParam String mail) throws IOException, MessagingException {
        User user = userService.getByMail(mail);
        ModelAndView ret = new ModelAndView("forgetPassword/forgetPassword");
        if (user == null) {
            ret.addObject("error", "No such mail " + mail + " in database");
            ret.addObject("mail", mail);
            return ret;
        }
        ses.setAttribute("user", user);
        String code = sendEmail(mail);
        ses.setAttribute("code",code);
        ses.setAttribute("action", AuthenticationAction.FORGET_PASSWORD);
        ses.setAttribute("sentCode",true);
        resp.sendRedirect("/verify-code");
        return null;
    }

    private String sendEmail(String mail) throws UnsupportedEncodingException, MessagingException {
        String code = RandomCodeGenerator.randomCode();
        mailServiceSender.sendVerificationCode(mail, "Verification Code", code);

        return code;
    }
}
