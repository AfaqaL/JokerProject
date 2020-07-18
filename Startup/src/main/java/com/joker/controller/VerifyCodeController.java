package com.joker.controller;

import com.joker.services.mail.MailServiceBean;
import com.joker.helper.AuthenticationAction;
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
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailServiceBean mailServiceBeanSender;

    @GetMapping
    public String verifyCode() {
        return "verifyCode/verifyCode";
    }

    @PostMapping
    public ModelAndView validateCode(HttpSession session,
                                     @RequestParam String code) throws MessagingException, UnsupportedEncodingException {
        AuthenticationAction action = (AuthenticationAction) session.getAttribute("action");
        String expectedCode = (String) session.getAttribute("code");

        if (!expectedCode.equals(code)) {
            resendEmail(session);
            return new ModelAndView("verifyCode/verifyCodeError");
        }

        session.removeAttribute("sentCode");
        if (action == AuthenticationAction.REGISTER) {
            User user = (User) session.getAttribute("user");
            userService.addUser(user);
            return new ModelAndView("redirect:/login");
        } else {
            session.setAttribute("changePassword",true);
            return new ModelAndView("redirect:/passwordRecovery");
        }
    }

    private void resendEmail(HttpSession session) throws UnsupportedEncodingException, MessagingException {
        String code = RandomCodeGenerator.randomCode();
        session.setAttribute("code",code);
        mailServiceBeanSender.sendVerificationCode((String) session.getAttribute("mail"),"verification code",code);
    }
}
