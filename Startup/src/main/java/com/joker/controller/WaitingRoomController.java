package com.joker.controller;

import com.joker.managers.WaitingRoomManager;
import com.joker.managers.WaitingRoomManagerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/waitingRoom")
public class WaitingRoomController {

    private final WaitingRoomManager waitingRoomManager;

    @Autowired
    public WaitingRoomController(WaitingRoomManagerBean waitingRoomManager) {
        this.waitingRoomManager = waitingRoomManager;
    }

    @GetMapping
    public String getHomepage(){
        return "waitingRoom/waitingRoom";
    }

    @PostMapping
    public ModelAndView enterTable() {

        return null;
    }
}
