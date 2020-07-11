package com.joker.controller;

import com.joker.databases.UsersDao;
import com.joker.helper.AuthenticationAction;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @Autowired
    private UsersDao users;
    @GetMapping
    public String verifyCode() {
        return "verifyCode/verifyCode";
    }

    @PostMapping
    public ModelAndView validateCode(HttpSession session,
                                     @RequestParam String code) {
        AuthenticationAction action = (AuthenticationAction) session.getAttribute("action");
        String expectedCode = (String) session.getAttribute("code");

        if (!expectedCode.equals(code)) {
            return new ModelAndView("verifyCode/verifyCodeError");
        }

        if (action == AuthenticationAction.REGISTER) {
            User user = (User) session.getAttribute("user");
            users.addUser(user);
            return new ModelAndView("redirect:/login");
        } else {
            return new ModelAndView("redirect:/passwordRecovery");
        }
    }
}
