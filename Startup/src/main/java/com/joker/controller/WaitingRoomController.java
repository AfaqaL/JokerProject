package com.joker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.joker.dao.InMemoryWaitingRoomDao;
import com.joker.managers.WaitingRoomManager;
import com.joker.managers.WaitingRoomManagerBean;
import com.joker.model.GameMode;
import com.joker.model.Room;
import com.joker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping
public class WaitingRoomController {

    private final WaitingRoomManager waitingRoomManager;

    @Autowired
    public WaitingRoomController(WaitingRoomManagerBean waitingRoomManager) {
        this.waitingRoomManager = waitingRoomManager;
    }

    @GetMapping("/waitingRoom")
    public String getHomepage() {
        return "waitingRoom/waitingRoom";
    }

    @PostMapping("/waitingRoom/create")
    public @ResponseBody long createTable(HttpSession session,
                                            @RequestBody String waitingRoom) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Gson converter = new Gson();
        Room roomData = converter.fromJson(waitingRoom, Room.class);
        User creator = (User)session.getAttribute("user");
        String password = roomData.getPassword();
        int bayonet = roomData.getBayonet();
        GameMode gameMode = roomData.getGameMode();

        long id = waitingRoomManager.createWaitingRoom(creator, password, bayonet, gameMode);

        return id;
    }


    @PostMapping("/waitingRoom/join")
    public @ResponseBody String joinTable(){
        return "";
    }
}
