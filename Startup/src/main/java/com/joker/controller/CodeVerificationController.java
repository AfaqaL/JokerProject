package com.joker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class CodeVerificationController {

    @GetMapping(value = {"/verifyCode"})
    public String verifyCodeForm(){
        return "verifyCode";
    }

    @PostMapping(value = {"/verifyCode"})
    public void verifyCode(@RequestParam String code, HttpSession ses, HttpServletResponse resp) throws IOException {
        String requestedCode = (String) ses.getAttribute("code");
        if (!requestedCode.equals(code)) {
            ses.setAttribute("error", "Code was Incorrect, please try again.");
            resp.sendRedirect("/forgot_password");
            return;
        }
        resp.sendRedirect("/newPassword");
    }

    @GetMapping("/newPassword")
    public String newPassword(){
        return "PasswordRecovery";
    }

    @PostMapping("/newPassword")
    public ModelAndView passwordVerification(HttpServletResponse resp,
                                             @RequestParam String pass,
                                             @RequestParam String confirmedPass) throws IOException {
        ModelAndView ret = new ModelAndView("PasswordRecovery");
        if (!pass.equals(confirmedPass)){
            ret.addObject("error", "New password doesn't match confirm password");
            return ret;
        }
        resp.sendRedirect("/");
        return  null;
    }
}
