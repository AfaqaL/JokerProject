package com.joker.controller;

import com.joker.databases.UsersDao;
import com.joker.helperClasses.RandomCodeGenerator;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/forgetPassword")
public class ForgetPasswordController {

    @Value("${host}")
    private String host;
    @Value("${port}")
    private String port;
    @Value("${email}")
    private String email;
    @Value("${name}")
    private String name;
    @Value("${pass}")
    private String pass;

    @Autowired
    private UsersDao users;

    @GetMapping
    public String forgetPassword() {
        return "forgetPassword/forgetPassword";
    }

    @PostMapping
    public ModelAndView validateMail(HttpSession ses, HttpServletResponse resp, @RequestParam String mail) throws IOException, MessagingException {
        User user = users.searchByMail(mail);
        ModelAndView ret = new ModelAndView("forgetPassword/forgetPassword");
        if (user == null) {
            ret.addObject("error", "No such mail " + mail + " in database");
            ret.addObject("mail", mail);
            return ret;
        }
        ses.setAttribute("user", user);
        String code = sendEmail(mail);
        ses.setAttribute("code",code);
        resp.sendRedirect("/verifyController");
        return null;
    }

    private String sendEmail(String mail) throws UnsupportedEncodingException, MessagingException {
        String code = RandomCodeGenerator.randomCode();
        Authentication.Mail.sendEmail(host, port, email,
                name, pass, mail,
                "Verification Code", code);
        return code;
    }
}
