public class Card implements Comparable{

    public static final int VALID_COLORS = 0;
    public static final int VALID_SUPERIOR = 1;


    public static final int NO_COLOR = -1;
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


    public int value;
    public int color;
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
     * @param first
     * @return
     */
    boolean isValid(Card first) {
        return valid;
    }

    void setValid (boolean arg) {
        this.valid = arg;
    }


    /**
     *
     * @param taker
     * @param superior
     * @return 1 if this > taker, -1 if this < taker
     */
    public int compare(Card taker, int superior) {
        if (taker instanceof JokerCard)  { return compareJoker((JokerCard)taker, superior); }

        if (taker.color == this.color)  {
            if (this.value > taker.value)
                return 1;
            else
                return -1;
        }

        if (taker.color == superior) { return -1; }

        if (this.color == superior) { return 1; }

        return -1;
    }

    private int compareJoker(JokerCard taker, int superior) {
        switch (taker.mode) {
            case JokerCard.OVER_MODE:
                return -1;
            case JokerCard.GIVE_HIGHEST_MODE:
                if (this.color == superior && taker.color != superior)
                    return 1;
                else
                    return -1;
            case JokerCard.TAKE_MODE:
                if (this.color == taker.color || this.color == superior)
                    return 1;
                else
                    return -1;
        }
        return -1;
    }

    @Override
    public int compareTo(Object o) {
        return ((Card)o).value - this.value;
    }

    @Override
    public boolean equals(Object obj) {
        Card curr = (Card)obj;
        return this.color == curr.color && this.value == curr.value;
    }
    
    @Override
    public String toString(){
        String s = "(";
        switch(this.value){
            case SIX:
                s += "6";
                break;
            case SEVEN:
                s += "7";
                break;
            case EIGHT:
                s += "8";
                break;
            case NINE:
                s += "9";
                break;
            case TEN:
                s += "10";
                break;
            case JACK:
                s += "J";
                break;
            case QUEEN:
                s += "Q";
                break;
            case KING:
                s += "K";
                break;
            case ACE:
                s += "A";
                break;
        }
        s += ", ";
        switch (color){
            case CLUBS:
                s += "jv";
                break;
            case SPADES:
                s += "yv";
                break;
            case HEARTS:
                s += "gu";
                break;
            case DIAMONDS:
                s += "ag";
                break;
        }
        s+= ")";
        return s;
    }
}
