package com.joker.helper;

import com.joker.game.Card;
import com.joker.game.JokerCard;
import com.joker.model.dto.CardDTO;
import com.joker.model.enums.CardValue;

public class CardHelper {

    public static Card fromDTO(CardDTO dto) {
        Card card;
        if (dto.getValue() == CardValue.JOKER) {
            card = new JokerCard();
            card.color = dto.getColor();
            ((JokerCard)card).mode = dto.getJokerMode();
        } else {
            card = new Card(dto.getValue(), dto.getColor());
        }
        return card;
    }
}
