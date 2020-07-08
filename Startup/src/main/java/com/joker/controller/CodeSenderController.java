package com.joker.controller;

import Authentication.Mail;
import com.joker.databases.InMemoryMailDao;
import com.joker.helperClasses.RandomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CodeSenderController extends HttpServlet {
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
    private InMemoryMailDao mails;

    @GetMapping(value = {"/forgot_password", "/send_code"})
    public String requestMail(){ return "requestMail"; }

    @PostMapping(value = {"/forgot_password", "/send_code"})
    public ModelAndView post(HttpSession ses, HttpServletResponse resp, @RequestParam String mail) throws IOException, MessagingException {
        ModelAndView ret = new ModelAndView("requestMail");
        if (!mails.mailExists(mail)) {
            ret.addObject("error", "No such mail " + mail + " in database");
            ret.addObject("mail", mail);
            return ret;
        }
        String code = RandomCodeGenerator.randomCode();
        Mail.sendEmail(host, port, email,
                name, pass, mail,
                 "Verification Code", code);
        ses.setAttribute("code", code);
        resp.sendRedirect("verifyCode");
        return null;
    }

}
