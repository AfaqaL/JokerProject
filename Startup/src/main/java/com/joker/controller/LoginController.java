package com.joker.controller;

import com.joker.model.User;
import com.joker.model.dto.LoginResponse;
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
    public @ResponseBody LoginResponse loginUser(@RequestParam String username,
                                          @RequestParam String password, HttpSession session) {

        User user = userService.getByUsernameAndPassword(username, password);
        if (user == null) {
            session.setAttribute("authorised",false);
            System.out.println("User doesnt exist left unhandled");
            return new LoginResponse(false);
        }

        session.setAttribute("authorised",true);
        session.setAttribute("user", user);
        return new LoginResponse(true, username, user.getRank(), user.getId());
    }
}
