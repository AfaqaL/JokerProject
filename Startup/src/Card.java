public class Card{

    public static final int VALID_COLORS = 0;
    public static final int VALID_SUPERIOR = 1;


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
    private boolean isSuperior;
    private boolean valid;

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
    boolean isValid(Card first, int mode) {
        if(first instanceof JokerCard){
            JokerCard jk = (JokerCard)first;
            return checkJokerValid(jk);
        }
        switch(mode){
            case VALID_COLORS:
                return first.color == this.color;
            case VALID_SUPERIOR:
                return isSuperior;
        }
        return false;
    }

    private boolean checkJokerValid(JokerCard jk) {
        return false;
    }



    public int compare(Card c, int color) {
        return -1;
    }
}
