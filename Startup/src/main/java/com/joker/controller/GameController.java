package com.joker.controller;

import com.joker.game.Card;
import com.joker.model.Room;
import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.DeclareRequest;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
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
import java.util.Random;

@Controller
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final List<CardDTO> arr = new ArrayList<>();

    private final Random rand = new Random();

    @Autowired
    private GameService gameService;

    @Autowired
    private WaitingRoomService waitingRoomService;

    @GetMapping("/table")
    public String table(HttpSession ses) {
        Room room = waitingRoomService.getReadyRoom((long) ses.getAttribute("tableId"));
        List<String> usernames = new ArrayList<>();
        for (User user : room.getPlayers()) {
            usernames.add(user.getUsername());
        }
        ses.setAttribute("usernames", usernames);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                CardDTO card = new CardDTO();
                card.setColor(CardColor.values()[i]);
                card.setValue(CardValue.values()[j]);
                card.setValid(rand.nextBoolean());

                arr.add(card);
            }
        }

        return "TableInterface/table";
    }

    @PostMapping("/table/update")
    public @ResponseBody
    TableResponse update(HttpSession session) {

        long tableId = (long) session.getAttribute("tableId");
        User user = (User) session.getAttribute("user");

        TableResponse response = gameService.getTable(tableId, user.getId());
        response.setChanged(true);

        return response;
    }

    @PostMapping("/table/declare")
    public void declare(HttpSession session, @RequestBody DeclareRequest request) {
        gameService.declareNumber((long) session.getAttribute("tableId"), request.getNumber());
    }

    @PostMapping("/table/put")
    public void put(HttpSession session, @RequestBody CardDTO request) {
        gameService.putCard((long) session.getAttribute("tableId"), request);
    }

    @PostMapping("/table/set-superior")
    public void setSuperiorCard(HttpSession session, @RequestBody CardDTO request) {
        gameService.setSuperiorCard((long) session.getAttribute("tableId"), request);
    }
}
