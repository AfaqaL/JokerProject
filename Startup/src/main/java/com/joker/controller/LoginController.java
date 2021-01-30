package com.joker.controller;

import com.joker.model.User;
import com.joker.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public @ResponseBody String loginUser(@RequestParam String username,
                                          @RequestParam String password, HttpSession session) {

        System.out.println("username: " + username + "\npassword: " + password);
        User user = userService.getByUsernameAndPassword(username, password);
        if (user == null) {
            session.setAttribute("authorised",false);
            return "../example.html";
        }

        session.setAttribute("authorised",true);
        session.setAttribute("user", user);
        return "../wr/waiting-room.html";
    }
}
