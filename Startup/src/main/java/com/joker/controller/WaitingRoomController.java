package com.joker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/waitingRoom")
public class WaitingRoomController {

    @GetMapping
    public String getHomepage(){
        return "waitingRoom/waitingRoom";
    }

    @PostMapping
    public ModelAndView enterTable() {

        return null;
    }
}
