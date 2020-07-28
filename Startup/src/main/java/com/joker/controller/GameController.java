package com.joker.controller;

import com.joker.game.Card;
import com.joker.game.JokerCard;
import com.joker.model.User;
import com.joker.model.dto.CardDTO;
import com.joker.model.dto.DeclareRequest;
import com.joker.model.dto.TableResponse;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.services.game.GameService;
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
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final List<CardDTO> arr = new ArrayList<>();

    private final Random rand = new Random();

    @Autowired
    private GameService gameService;

    @GetMapping("/table")
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

        // არაა საჭირო. NullPointer რომ არ მოხდეს იმიტომ მიწერია
        ses.setAttribute("tableId", 1L);

        return "TableInterface/table";
    }

    @PostMapping("/table/update")
    public @ResponseBody TableResponse update(HttpSession session) {
        TableResponse response = new TableResponse();
        response.setChanged(true);
        response.setId(23);
        response.setDeclares(null);

        List<CardDTO> cards = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            cards.add(arr.get(randomIndex));
        }
        response.setCards(cards);

        List<CardDTO> playedCards = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            playedCards.add(arr.get(randomIndex));
        }
        response.setPlayedCards(playedCards);

        List<Integer> taken = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            taken.add(rand.nextInt() % 3);
        }
        response.setTaken(taken);

        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            scores.add(rand.nextInt() % 100);
        }
        response.setScores(scores);

        List<Integer> declares = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            declares.add(rand.nextInt() % 9);
        }
        response.setDeclares(declares);


        int randomIndex = (int) (rand.nextFloat() * arr.size());
        CardDTO superior = arr.get(randomIndex);
        response.setSuperior(superior);

        response.setInvalidCall(4);
        response.setPlayerIndex(ThreadLocalRandom.current().nextInt(0, 4));

        int randomStage = ThreadLocalRandom.current().nextInt(0, 4);;
        response.setCurrentStage(randomStage);

        if (randomStage % 2 == 0) {
            response.setCurrentRound(ThreadLocalRandom.current().nextInt(0, 9));
        } else {
            response.setCurrentRound(ThreadLocalRandom.current().nextInt(0, 4));
        }
        return response;
//        long tableId = (long) session.getAttribute("tableId");
//        User user = (User) session.getAttribute("user");
//        return gameService.getTable(tableId, user.getId());
    }

    @PostMapping("/table/declare")
    public void declare(HttpSession session, @RequestBody DeclareRequest request) {
        gameService.declareNumber((long) session.getAttribute("tableId"), request.getNumber());
    }

    @PostMapping("/table/put")
    public void put(HttpSession session, @RequestBody CardDTO request) {
        Card card;
        if (request.getValue() == CardValue.JOKER) {
            card = new JokerCard();
        } else {
            card = new Card(request.getValue(), request.getColor());
        }

        gameService.putCard((long) session.getAttribute("tableId"), card);
    }

    @PostMapping("/table/set-superior")
    public void setSuperiorCard(HttpSession session, @RequestBody CardDTO request) {
        Card card = new Card(request.getValue(), request.getColor());
        gameService.setSuperiorCard((long) session.getAttribute("tableId"), card);
    }
}
