package com.joker.controller;

import com.joker.model.dto.CardDTO;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;
import com.joker.model.socketDTOs.Greeting;
import com.joker.model.socketDTOs.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public CardDTO greeting(String message) throws Exception {
        System.out.println(message);
        CardDTO card = new CardDTO();
        card.setColor(CardColor.CLUBS);
        card.setValue(CardValue.ACE);
        card.setValid(true);
        card.setJokerMode(JokerMode.GIVE);
        return card;
    }

}