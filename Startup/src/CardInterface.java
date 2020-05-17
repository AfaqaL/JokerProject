public interface CardInterface extends Comparable<Card>{
    String[] cardNames = {"SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING", "ACE", "JOKER"};
    String[] cardTypes = {"CLUBS", "DIAMONDS", "SPADES", "HEARTS", "NOTYPE"};
    int CLUBS = 0;
    int DIAMONDS = 1;
    int SPADES = 2;
    int HEARTS = 3;
    int NOTYPE = 4;

    int SIX = 0;
    int SEVEN = 1;
    int EIGHT = 2;
    int NINE = 3;
    int TEN = 4;
    int JACK = 5;
    int QUEEN = 6;
    int KING = 7;
    int ACE = 8;

    int JOKER = 9;

    int LOW_PRIO = 1;
    int EQUAL_PRIO = 2;
    int HIGHER_PRIO = 3;

    @Override
    String toString();
    @Override
    int compareTo(Card card);
    void setPriority(int priority);
    void setSuperior(int superior);
}
