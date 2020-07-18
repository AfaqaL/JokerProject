package com.joker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.joker.services.waitingroom.WaitingRoomService;
import com.joker.services.waitingroom.WaitingRoomServiceBean;
import com.joker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping
public class WaitingRoomController {

    private final WaitingRoomService waitingRoomService;

    @Autowired
    public WaitingRoomController(WaitingRoomServiceBean waitingRoomManager) {
        this.waitingRoomService = waitingRoomManager;
    }

    @GetMapping("/waitingRoom")
    public String getHomepage() {
        return "waitingRoom/waitingRoom";
    }

    @PostMapping("/waitingRoom/create")
    public @ResponseBody long createTable(HttpSession session,
                                          @RequestBody Room roomData) {
        User creator = (User)session.getAttribute("user");
        String password = roomData.getPassword();
        int bayonet = roomData.getBayonet();
        GameMode gameMode = roomData.getGameMode();

        long id = waitingRoomService.createWaitingRoom(creator, password, bayonet, gameMode);
        session.setAttribute("tableId", id);

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


        if(waitingRoomService.isRoomReady(id)) {
            return "FALSE";
        }

        Boolean result =  waitingRoomService.addUser(player, id, password);

        if(waitingRoomService.isRoomReady(id)) {
            waitingRoomService.removeRoom(id);
        }

        if(result) {
            session.setAttribute("tableId", id);
            return "TRUE";
        } else {
            return "FALSE";
        }
    }

    @GetMapping("/waitingRoom/update")
    public @ResponseBody String update(HttpSession session){
        UIinfo res = new UIinfo();
        ObjectMapper mapper = new ObjectMapper();

        int managerVersion = waitingRoomService.getVersion();
        int userVersion = (Integer) session.getAttribute("version");
        if (managerVersion == userVersion) {
            res.setIsChanged("FALSE");
            res.setRooms(new ArrayList<>());
            res.setTableId((Long)session.getAttribute("tableId"));

            try {
                return mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                System.out.println(res);
                System.out.println("Bad JSON Format");
                return "";
            }
        }
        res.setIsChanged("TRUE");
        res.setRooms(waitingRoomService.getAllRooms());
        res.setTableId((Long) session.getAttribute("tableId"));
        session.setAttribute("version", managerVersion);
        try {
            return mapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            System.out.println(res);
            System.out.println("Bad JSON Format");
            return "";
        }
    }
}

