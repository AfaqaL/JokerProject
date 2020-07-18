package com.joker.controller;

import com.joker.dao.user.UserDao;
import com.joker.model.User;
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
@RequestMapping("/passwordRecovery")
public class PasswordRecoveryController {

    @Autowired
    private UserDao users;

    @GetMapping
    public String passwordRecovery() {
        return "passwordRecovery/passwordRecovery";
    }

    @PostMapping
    public ModelAndView validatePassword(HttpServletResponse resp,
                                         HttpSession ses,
                                         @RequestParam String pass,
                                         @RequestParam String confirmedPass) throws IOException {
        ModelAndView ret = new ModelAndView("passwordRecovery/passwordRecovery");
        if (!pass.equals(confirmedPass)) {
            ret.addObject("incorrectPassword", "New password doesn't match confirm password");
            return ret;
        }
        ses.removeAttribute("changePassword");
        User user = (User) ses.getAttribute("user");
        users.changePassword(user.getUsername(), pass);
        resp.sendRedirect("/login");
        return null;
    }
}
