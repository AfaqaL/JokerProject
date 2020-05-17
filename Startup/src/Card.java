import java.util.Arrays;
import java.util.Optional;

public class Card implements CardInterface{

    private int card;
    private int type;
    private int priority;
    private boolean isSuperior;

    /**
     * Sets up a card with its "color" and "number" values
     * @param card "number"
     * @param type "color"
     */
    public Card(int card, int type){
        this.card = card;
        this.type = type;
    }

    /**
     * Checks if this card is valid to be put or not
     * !! TODO !!
     * @param first
     * @return
     */
    boolean isValid(Card first) {
        return false;
    }

    public String toString() {
        return cardNames[card] + " " + cardTypes[type];
    }

    @Override
    public int compareTo(Card card) {
        return 0;
    }


    /**
     * Sets card priority according to the
     * first card on the current move
     * @param firstCardType -> color of first card on this move
     */
    public void setPriority(int firstCardType) {
        if(priority != HIGHER_PRIO){
            priority = (type == firstCardType ? EQUAL_PRIO : LOW_PRIO);
        }
    }

    /**
     * sets card priority according to the
     * superior type card before each turn
     * @param superiorType -> card color set before every turn
     */
    public void setSuperior(int superiorType) {
        if(type == superiorType)
            priority = HIGHER_PRIO;
    }
}
