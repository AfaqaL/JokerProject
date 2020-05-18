public class Card {
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

    private int card;
    private int type;
    private int priority;

    public Card(int card, int type){
        this.card = card;
        this.type = type;
    }

    public Card(int x) {

    }
}