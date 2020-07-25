package com.joker.controller;

import com.joker.game.Card;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;

@Controller
@RequestMapping("/tables")
public class TableController {


    @GetMapping
    public String table(HttpSession ses){
        ArrayList<Card> arr = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 9; j++){
                arr.add(new Card(j,i));
            }
        }
        ArrayList<Card> hand = new ArrayList<>();
        Random rand = new Random();
        for (int i = 1; i <= 9; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            hand.add(arr.get(randomIndex));
        }
        ArrayList <String> usernames = new ArrayList<>();
        for (char c = 'A'; c<='D'; c++) {
            usernames.add(c + "");
        }
        ArrayList <Card> putCards = new ArrayList<>();
        for (int i = 1; i <= 4; i++){
            int randomIndex = (int) (rand.nextFloat() * arr.size());
            putCards.add(arr.get(randomIndex));
        }

        ArrayList<String> colorNames = new ArrayList<>();
        colorNames.add("suitclubs");
        colorNames.add("suitdiamonds");
        colorNames.add("suitspades");
        colorNames.add("suithearts");
        ses.setAttribute("hand", hand);
        ses.setAttribute("usernames",usernames);
        ses.setAttribute("cards",putCards);
        ses.setAttribute("colorNames",colorNames);
        return "TableInterface/table";
    }
}
