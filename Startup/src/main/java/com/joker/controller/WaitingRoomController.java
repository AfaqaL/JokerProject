package com.joker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.joker.dao.InMemoryWaitingRoomDao;
import com.joker.managers.WaitingRoomManager;
import com.joker.managers.WaitingRoomManagerBean;
import com.joker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
    public @ResponseBody String joinTable( HttpSession session,
                           @RequestBody String roomInfo ){
        Gson converter = new Gson();
        EnterRoom roomData = converter.fromJson(roomInfo, EnterRoom.class);

        User player = (User)session.getAttribute("user");
        String password = roomData.getPassword();
        Long id = roomData.getId();

        if(waitingRoomManager.isRoomReady(id)) {
            waitingRoomManager.removeRoom(id);
            return "FALSE";
        }
        return waitingRoomManager.addUser(player, id, password) ? "TRUE" : "FALSE";
    }

    @GetMapping("/waitingRoom/update")
    public @ResponseBody String update(HttpSession session){
        UIinfo res = new UIinfo();
        ObjectMapper mapper = new ObjectMapper();

        int managerVersion = waitingRoomManager.getVersion();
        int userVersion = (int)session.getAttribute("version");
        if (managerVersion == userVersion) {
            res.setIsChanged("FALSE");
            res.setRooms(new ArrayList<>());
            try {
                return mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                System.out.println("Bad JSON Format");
                return "";
            }
        }
        res.setIsChanged("TRUE");
        res.setRooms(waitingRoomManager.getAllRooms());
        try {
            return mapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            System.out.println("Bad JSON Format");
            return "";
        }
    }
}

