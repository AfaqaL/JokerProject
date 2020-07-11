package com.joker.controller;

import com.joker.databases.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsersDao users;

    @GetMapping
    public String login() {
        return "login/login";
    }

    @PostMapping
    public ModelAndView loginUser(@RequestParam String username,
                                  @RequestParam String password) {

        return null;
    }
}
