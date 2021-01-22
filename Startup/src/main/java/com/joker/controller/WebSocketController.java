package com.joker.controller;

import com.joker.game.Card;
import com.joker.helper.CardHelper;
import com.joker.model.dto.CardDTO;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public CardDTO testM(@RequestBody String msg) {
        System.out.println("Entered test method");
        System.out.println("msg");

        CardDTO card = new CardDTO();
        card.setColor(CardColor.CLUBS);
        card.setValue(CardValue.ACE);
        card.setValid(true);
        card.setJokerMode(JokerMode.UNDER);
        return card;
    }

    @GetMapping("/random-test")
    public ModelAndView rand(){
        return new ModelAndView("wstest");
    }

}