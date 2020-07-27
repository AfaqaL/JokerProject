package com.joker.game;

import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;

import java.util.Arrays;
import java.util.List;

public class JokerCard extends Card {

    public JokerMode mode;

    /**
     * Sets up a joker card
     */
    public JokerCard() {
        super(CardValue.JOKER, null);
    }

    public void setMode(JokerMode mode, CardColor color) {
        this.mode = mode;
        this.color = color;
    }

    @Override
    boolean isValid() {
        return true;
    }

    @Override
    public int compare(Card taker, CardColor superior) {
        if (this.mode == JokerMode.OVER)
            return 1;
        else // UNDER_MODE
            return -1;
    }

    public List<JokerMode> getValidModes(int turn) {
        if (turn == 0) {
            return Arrays.asList(JokerMode.TAKE,JokerMode.GIVE);
        } else {
            return Arrays.asList(JokerMode.UNDER,JokerMode.OVER);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return ((Card) obj).value.ordinal() == CardValue.JOKER.ordinal();
    }
}
