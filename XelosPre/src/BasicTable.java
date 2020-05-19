import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BasicTable implements Table {
    protected List<Card> cards;
    protected Player[] players;
    void initCards(){
        cards = new ArrayList<>(36);
        for (int i = Card.CLUBS; i <= Card.HEARTS; i++) {
            for (int j = Card.SIX; j <= Card.ACE; j++) {
                cards.add(new Card(j, i));
            }
        }
    }
    void shuffle(int numCards){
        Collections.shuffle(cards);
        for (int i = 0; i < 4; i++) {

        }

    }
}
