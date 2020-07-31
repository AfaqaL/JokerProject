package com.joker.controller;

import com.joker.model.User;
import com.joker.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login() {
        return "login/login";
    }

    @PostMapping
    public ModelAndView loginUser(@RequestParam String username,
                                  @RequestParam String password, HttpSession session) {

        User user = userService.getByUsernameAndPassword(username, password);
        if (user == null) {
            session.setAttribute("authorised",false);
            return new ModelAndView("login/loginError");
        }

        session.setAttribute("authorised",true);
        session.setAttribute("user", user);
        return new ModelAndView("redirect:/waiting-room");
    }
}
