package com.joker.controller;

import com.joker.databases.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/verifyController")
public class VerifyCodeController {

    @GetMapping
    public String verifyCode() {
        return "verifyCode/verifyCode";
    }

    @PostMapping
    public ModelAndView validateCode(@RequestParam String code, HttpSession ses,
                                     HttpServletResponse resp) throws IOException {
        String requestedCode = (String) ses.getAttribute("code");
        if (!requestedCode.equals(code)) {
            ses.setAttribute("error", "Code was Incorrect, please try again.");
            resp.sendRedirect("/forgetPassword");
            return null;
        }
        resp.sendRedirect("/passwordRecovery");
        return null;
    }
}
