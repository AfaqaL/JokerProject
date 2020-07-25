package com.joker.model;

import com.joker.game.CardColor;
import com.joker.game.CardValue;

public class Card {

    private CardColor color;

    private CardValue value;

    private boolean valid;

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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
