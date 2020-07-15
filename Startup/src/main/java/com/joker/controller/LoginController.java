package com.joker.controller;

import com.joker.dao.UsersDao;
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
                                  @RequestParam String password, HttpSession session) {

        User user = users.searchByUsernameAndPassword(username, password);
        if (user == null)
            return new ModelAndView("login/loginError");

        session.setAttribute("user", user);
        return new ModelAndView("redirect:/");

    }
}
