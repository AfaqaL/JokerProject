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
import java.io.UnsupportedEncodingException;

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

    @GetMapping("/forgot_password")
    public String requestMail(){ return "Forgot_Password/requestMail"; }

    @PostMapping("/forgot_password")
    public ModelAndView post(HttpSession ses, HttpServletResponse resp, @RequestParam String mail) throws IOException {
        ModelAndView ret = new ModelAndView("Forgot_Password/requestMail");
        if (!mails.mailExists(mail)) {
            ret.addObject("error", "No such mail " + mail + " in database");
            ret.addObject("mail", mail);
            return ret;
        }
        ses.setAttribute("mail", mail);
        resp.sendRedirect("forgot_password/sendCode");
        return null;
    }

    @GetMapping("/forgot_password/sendCode")
    public String sendCode() {
        return "Forgot_Password/sendCode";
    }

    @PostMapping("/forgot_password/verifyCode")
    public String verifyCodeForm(HttpSession ses) throws UnsupportedEncodingException, MessagingException {
        String mail = (String) ses.getAttribute("mail");
        String code = RandomCodeGenerator.randomCode();
        Mail.sendEmail(host, port, email,
                name, pass, mail,
                "Verification Code", code);
        ses.setAttribute("code", code);
        return "Forgot_Password/verifyCode";
    }

}
