package com.joker.controller;

import com.joker.databases.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UsersDao users;

    @GetMapping
    public String register() {
        return "registration/register";
    }

    @PostMapping
    public ModelAndView registerUser() {

        return null;
    }
}
