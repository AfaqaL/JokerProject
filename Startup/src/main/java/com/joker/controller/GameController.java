package com.joker.controller;

import com.joker.model.Room;
import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.DeclareRequest;
import com.joker.model.dto.TableResponse;
import com.joker.services.game.GameService;
import com.joker.services.waitingroom.WaitingRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private WaitingRoomService waitingRoomService;

    @GetMapping("/table")
    public String table(HttpSession ses) {
        Room room = waitingRoomService.getReadyRoom((long) ses.getAttribute("tableId"));
        int playerIndex = gameService.getIndex(room.getId(), ((User) ses.getAttribute("user")).getId());
        List<String> usernames = new ArrayList<>();
        List <String> usernamesGridSeq = new ArrayList<>();
        for (int i = 0; i < room.getPlayers().size(); i++){
            usernames.add(room.getPlayers().get((playerIndex + i + 1)%4).getUsername());
            usernamesGridSeq.add(room.getPlayers().get(i).getUsername());
        }

        ses.setAttribute("usernames", usernames);
        ses.setAttribute("usernamesGridSeq",usernamesGridSeq);
        ses.setAttribute("gameVersion",-1);
        ses.setAttribute("gameMode", room.getGameMode());
        return "TableInterface/table";
    }

    @PostMapping("/table/update")
    public @ResponseBody
    TableResponse update(HttpSession session) {

        long tableId = (long) session.getAttribute("tableId");
        User user = (User) session.getAttribute("user");
        int gameVersion = gameService.getVersion(tableId);
        TableResponse response;
//        if (gameVersion == (int)session.getAttribute("gameVersion")) {
//            response = new TableResponse();
//            response.setChanged(false);
//        }else {
            response = gameService.getTable(tableId, user.getId());
            response.setChanged(true);
//        }
        return response;
    }

    @PostMapping("/table/declare")
    public void declare(HttpSession session, @RequestBody DeclareRequest request) {
        long tableId = (long) session.getAttribute("tableId");
        int version = gameService.getVersion(tableId);
        gameService.declareNumber(tableId, request.getNumber());
//        session.setAttribute("gameVersion",version + 1);
    }

    @PostMapping("/table/put")
    public void put(HttpSession session, @RequestBody CardDTO request) {
        long tableId = (long) session.getAttribute("tableId");
        long playerId = ((User)session.getAttribute("user")).getId();
        int version = gameService.getVersion(tableId);
        gameService.putCard(tableId, request, playerId);
//        session.setAttribute("gameVersion",version + 1);
    }

    @PostMapping("/table/set-superior")
    public void setSuperiorCard(HttpSession session, @RequestBody CardDTO request) {
        long tableId = (long) session.getAttribute("tableId");
        int version = gameService.getVersion(tableId);
        gameService.setSuperiorCard(tableId, request);
//        session.setAttribute("gameVersion",version + 1);
    }
}
