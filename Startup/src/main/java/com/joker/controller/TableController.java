package com.joker.controller;

import com.joker.game.CardColor;
import com.joker.game.CardValue;
import com.joker.model.Card;
import com.joker.model.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class TableController {

    private static final Logger log = LoggerFactory.getLogger(TableController.class);

    private final List<Card> arr = new ArrayList<>();

    private final Random rand = new Random();

    @GetMapping("/tables")
    public String table(HttpSession ses){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 9; j++){
                Card card = new Card();
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
    public @ResponseBody Table update(HttpSession session) {
        Table response = new Table();
        response.setId(23);
        response.setSuperior(null);
        response.setDeclares(null);
        response.setScores(null);

        // ჩამოსული კარტები
        List<Card> playedCards = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            playedCards.add(arr.get(randomIndex));
        }
        response.setPlayedCards(playedCards);

        // მოთამაშის კარტები
        List<Card> cards = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            cards.add(arr.get(randomIndex));
        }
        response.setCards(cards);

        return response;
    }
}
