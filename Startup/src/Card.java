public class Card implements Comparable{


    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int SPADES = 2;
    public static final int HEARTS = 3;

    public static final int SIX = 0;
    public static final int SEVEN = 1;
    public static final int EIGHT = 2;
    public static final int NINE = 3;
    public static final int TEN = 4;
    public static final int JACK = 5;
    public static final int QUEEN = 6;
    public static final int KING = 7;
    public static final int ACE = 8;

    public static final int JOKER = 9;

    public static final int LOW_PRIO = 1;
    public static final int EQUAL_PRIO = 2;
    public static final int HIGHER_PRIO = 3;

    private int value;
    private int color;
    private int priority;
    private boolean isSuperior;

    /**
     * Sets up a card with its "color" and "number" values
     * @param value "number"
     * @param color "color"
     */
    public Card(int value, int color){
        this.value = value;
        this.color = color;
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

    @Override
    public String toString() {
        return "";
    }


    /**
     * Sets card priority according to the
     * first card on the current move
     * @param firstCardType -> color of first card on this move
     */
    public void setPriority(int firstCardType) {
        if(priority != HIGHER_PRIO){
            priority = (color == firstCardType ? EQUAL_PRIO : LOW_PRIO);
        }
    }

    /**
     * sets card priority according to the
     * superior type card before each turn
     * @param superiorType -> card color set before every turn
     */
    public void setSuperior(int superiorType) {
        if(color == superiorType)
            priority = HIGHER_PRIO;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
