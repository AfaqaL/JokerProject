package com.joker.game;

import com.joker.model.dto.CardDTO;
import com.joker.model.enums.CardColor;
import com.joker.model.enums.CardValue;
import com.joker.model.enums.JokerMode;

public class Card implements Comparable {

    public CardValue value;
    public CardColor color;
    private boolean valid;

    /**
     * Sets up a card with its "color" and "number" values
     *
     * @param value "number"
     * @param color "color"
     */
    public Card(CardValue value, CardColor color) {
        this.value = value;
        this.color = color;
        valid = false;
    }


    /**
     * Checks if this card is valid to be put or not
     *
     * @return
     */
    boolean isValid() {
        return valid;
    }

    void setValid(boolean arg) {
        this.valid = arg;
    }


    /**
     * @param taker
     * @param superior
     * @return 1 if this > taker, -1 if this < taker
     */
    public int compare(Card taker, CardColor superior) {
        if (taker instanceof JokerCard) {
            return compareJoker((JokerCard) taker, superior);
        }

        if (taker.color.ordinal() == this.color.ordinal()) {
            if (this.value.ordinal() > taker.value.ordinal())
                return 1;
            else
                return -1;
        }

        if (taker.color.ordinal() == superior.ordinal()) {
            return -1;
        }

        if (this.color.ordinal() == superior.ordinal()) {
            return 1;
        }

        return -1;
    }

    private int compareJoker(JokerCard taker, CardColor superior) {
        switch (taker.mode) {
            case GIVE:
                if (this.color.ordinal() == superior.ordinal() &&
                        taker.color.ordinal() != superior.ordinal())
                    return 1;
                else
                    return -1;
            case TAKE:
                if (this.color.ordinal() == taker.color.ordinal() ||
                        this.color.ordinal() == superior.ordinal())
                    return 1;
                else
                    return -1;
        }
        return -1;
    }

    @Override
    public int compareTo(Object o) {
        return ((Card) o).value.ordinal() - this.value.ordinal();
    }

    @Override
    public boolean equals(Object obj) {
        Card curr = (Card) obj;
        return this.color.ordinal() == curr.color.ordinal() &&
                this.value.ordinal() == curr.value.ordinal();
    }
//if necessary
//    @Override
//    public String toString() {
//        String s = "(";
//        switch (this.value) {
//            case SIX:
//                s += "6";
//                break;
//            case SEVEN:
//                s += "7";
//                break;
//            case EIGHT:
//                s += "8";
//                break;
//            case NINE:
//                s += "9";
//                break;
//            case TEN:
//                s += "10";
//                break;
//            case JACK:
//                s += "J";
//                break;
//            case QUEEN:
//                s += "Q";
//                break;
//            case KING:
//                s += "K";
//                break;
//            case ACE:
//                s += "A";
//                break;
//        }
//        s += ", ";
//        switch (color) {
//            case CLUBS:
//                s += "jv";
//                break;
//            case SPADES:
//                s += "yv";
//                break;
//            case HEARTS:
//                s += "gu";
//                break;
//            case DIAMONDS:
//                s += "ag";
//                break;
//        }
//        s += ")";
//        return s;
//    }

    public CardDTO convertToTransferObj(){
        CardDTO card = new CardDTO();
        card.setColor(this.color);
        card.setValue(this.value);
        return card;
    }
}
