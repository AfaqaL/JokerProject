package com.joker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.joker.managers.WaitingRoomManager;
import com.joker.managers.WaitingRoomManagerBean;
import com.joker.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public @ResponseBody String createTable(HttpServletRequest req,
                                            @RequestBody String waitingRoom) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Gson converter = new Gson();
        Room user = converter.fromJson(waitingRoom, Room.class);
        System.out.println(user.getPassword() + "Sssssssssssssssssss");
        return mapper.writeValueAsString(req.getSession().getAttribute("user"));

    }
}
