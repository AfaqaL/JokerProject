package com.joker.controller;

import com.joker.model.Room;
import com.joker.model.User;
import com.joker.model.dto.WaitingRoomResponse;
import com.joker.model.enums.GameMode;
import com.joker.services.game.GameService;
import com.joker.services.waitingroom.WaitingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class WaitingRoomController {

    @Autowired
    private WaitingRoomService waitingRoomService;

    @Autowired
    private GameService gameService;

    @GetMapping("/waiting-room")
    public @ResponseBody String waitingRoom(HttpSession session) {
        if (session.getAttribute("tableId") == null) {
            session.setAttribute("tableId", -1L);
        }
//        session.setAttribute("roomVersion", -1);
//
//        User user = (User)session.getAttribute("user");
//        String res = user.getUsername() + '_' + user.getRank();
//        return res;
        return "afaqa5";
    }

    @PostMapping("/waiting-room/create")
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

    @PostMapping("/waiting-room/join")
    public @ResponseBody Boolean joinTable(HttpSession session,
                                          @RequestBody Room roomData ){
        User player = (User) session.getAttribute("user");
        String password = roomData.getPassword();
        long id = roomData.getId();

        if(waitingRoomService.isRoomReady(id)) {
            return false;
        }

        boolean result =  waitingRoomService.addUser(player, id, password);

        if(waitingRoomService.isRoomReady(id)) {
            Room room = waitingRoomService.getReadyRoom(id);
            gameService.createTable(room);
        }

        if(result) {
            session.setAttribute("tableId", id);
        }
        return result;
    }

    @PostMapping("/waiting-room/update")
    public @ResponseBody WaitingRoomResponse update(HttpSession session) {
        WaitingRoomResponse response = new WaitingRoomResponse();

        int waitingRoomVersion = waitingRoomService.getVersion();
        int userVersion = (int) session.getAttribute("roomVersion");
        if (waitingRoomVersion == userVersion) {
            response.setChanged(false);
            response.setRooms(new ArrayList<>());
            response.setTableId((long)session.getAttribute("tableId"));
        } else {
            response.setChanged(true);
            response.setRooms(waitingRoomService.getAllRooms());
            response.setTableId((long) session.getAttribute("tableId"));
            session.setAttribute("roomVersion", waitingRoomVersion);
        }

        return response;
    }

    @PostMapping("/waiting-room/leave-table")
    public @ResponseBody Boolean leaveTable(HttpSession session){
        User user = (User) session.getAttribute("user");
        long tableId = (long) session.getAttribute("tableId");

        boolean res = waitingRoomService.removeUser(user, tableId);
        if (!res) {
            session.setAttribute("tableId", -1L);
        }

        return res;
    }

    @MessageMapping("create")
    @SendTo("/rooms/update")
    public void createTable(@RequestBody Room room){
        System.out.println(room.getBayonet());
        System.out.println(room.getGameMode().name());
        System.out.println(room.getPassword());
        System.out.println(room.getGameMode());
    }

    @MessageMapping("join")
    @SendTo("/rooms/update")
    public void joinTable(){

    }

    @MessageMapping("leave")
    @SendTo("/rooms/update")
    public void leaveTable(){

    }
}

