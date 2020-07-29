package com.joker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.joker.model.dto.WaitingRoomResponse;
import com.joker.model.enums.GameMode;
import com.joker.services.game.GameService;
import com.joker.services.waitingroom.WaitingRoomService;
import com.joker.services.waitingroom.WaitingRoomServiceBean;
import com.joker.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping
public class WaitingRoomController {
    private static final Logger log = LoggerFactory.getLogger(WaitingRoomController.class);

    @Autowired
    private WaitingRoomService waitingRoomService;

    @Autowired
    private GameService gameService;

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
    public @ResponseBody String joinTable(HttpServletResponse response,
                                          HttpSession session,
                                          @RequestBody Room roomData ){
        User player = (User)session.getAttribute("user");
        String password = roomData.getPassword();
        long id = roomData.getId();

        if(waitingRoomService.isRoomReady(id)) {
            log.info("Already 4 in there");
            return "FALSE";
        }

        boolean result =  waitingRoomService.addUser(player, id, password);

        if(waitingRoomService.isRoomReady(id)) {
            Room room = waitingRoomService.getReadyRoom(id);
            gameService.createTable(room);
        }

        if(result) {
            session.setAttribute("tableId", id);
            return "TRUE";
        } else {
            log.info("Add user returns false");
            return "FALSE";
        }
    }

    @GetMapping("/waitingRoom/update")
    public @ResponseBody String update(HttpSession session, HttpServletResponse response) throws IOException {
        WaitingRoomResponse res = new WaitingRoomResponse();
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
                log.error("Bad GSON format  " + e.getMessage());
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
            log.error("Bad GSON format  " + e.getMessage());
            return "";
        }
    }
}

