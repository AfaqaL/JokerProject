import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BasicTable implements Table {
    protected List<Card> cards;
    protected Player[] players;
    public BasicTable(int id1, int id2, int id3, int id4){
        players = new Player[4];
        players[0] = new Player(id1);
        players[1] = new Player(id2);
        players[2] = new Player(id3);
        players[3] = new Player(id4);
    }
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
        int cardIdx = 0;
        for (int i = 0; i < Table.NUM_PLAYERS; i++) {
            players[i].getDealtCards(cards.subList(cardIdx, cardIdx + numCards));
            cardIdx += numCards;
        }
    }
}
