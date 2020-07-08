package com.joker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
        if (!requestedCode.equals(code)){
            ses.setAttribute("error", "Code was Incorrect, please try again.");
            resp.sendRedirect("/forgot_password");
            return;
        }
        resp.sendRedirect("/");
    }
}
