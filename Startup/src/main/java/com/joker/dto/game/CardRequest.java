package com.joker.dto.game;

import com.joker.game.CardColor;
import com.joker.game.CardValue;
import com.joker.game.JokerMode;

public class CardRequest {

    private CardColor color;

    private CardValue value;

    private JokerMode jokerMode;

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }

    public JokerMode getJokerMode() {
        return jokerMode;
    }

    public void setJokerMode(JokerMode jokerMode) {
        this.jokerMode = jokerMode;
    }
}
