package com.joker.model.dto;

import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;

public class CardDTO {

    private CardColor color;

    private CardValue value;

    private boolean valid;

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

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public JokerMode getJokerMode() {
        return jokerMode;
    }

    public void setJokerMode(JokerMode jokerMode) {
        this.jokerMode = jokerMode;
    }
}
