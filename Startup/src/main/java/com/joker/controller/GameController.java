package com.joker.controller;

import com.joker.model.Room;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.DeclareRequest;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.services.game.GameService;
import com.joker.services.game.GameServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameController {


    private GameService gameService;

    @Autowired
    public GameController(GameServiceBean gsBean){
        gameService = gsBean;
    }

    @GetMapping("/table")
    public String tablePage() {
        return "tablePage/table";
    }

    @PostMapping("/table/declare")
    public void declare(HttpSession session,
                        @RequestBody DeclareRequest request) {

    }

    @PostMapping("/table/put")
    public void put(HttpSession session,
                    @RequestBody CardDTO request) {

    }

    @PostMapping("/table/set-superior")
    public void setSuperiorCard(HttpSession session,
                                @RequestBody CardDTO request) {

    }


    private final List<CardDTO> arr = new ArrayList<>();

    private final Random rand = new Random();

    @GetMapping("/tables")
    public String table(HttpSession ses){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 9; j++){
                CardDTO card = new CardDTO();
                card.setColor(CardColor.values()[i]);
                card.setValue(CardValue.values()[j]);
                card.setValid(rand.nextBoolean());

                arr.add(card);
            }
        }

        ArrayList <String> usernames = new ArrayList<>();
        for (char c = 'A'; c<='D'; c++) {
            usernames.add("user_" + c);
        }

        ses.setAttribute("usernames",usernames);
        return "TableInterface/table";
    }

    @PostMapping("/table/update")
    public @ResponseBody
    TableResponse update(HttpSession session) {
        TableResponse response = new TableResponse();
        response.setId(23);
        response.setSuperior(null);
        response.setDeclares(null);
        response.setScores(null);

        // ჩამოსული კარტები
        List<CardDTO> playedCards = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            playedCards.add(arr.get(randomIndex));
        }
        response.setPlayedCards(playedCards);

        // მოთამაშის კარტები
        List<CardDTO> cards = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            cards.add(arr.get(randomIndex));
        }
        response.setCards(cards);

        return response;
    }

}
