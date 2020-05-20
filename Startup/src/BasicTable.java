import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BasicTable implements Table {
    protected List<Card> cards;
    protected Player[] players;
    protected Card currTaker;
    protected int firstCardColor;
    protected int superior;
    protected int currTakerID;
    protected int currStage;
    protected int[][] declaresGrid;
    protected int[][] scoresGrid;


    protected BasicTable(int id1, int id2, int id3, int id4){
        int first = ThreadLocalRandom.current().nextInt() % 4;
        players = new Player[4];
        players[first++] = new Player(id1);
        first %= 4;
        players[first++] = new Player(id2);
        first %= 4;
        players[first++] = new Player(id3);
        first %= 4;
        players[first] = new Player(id4);
        initCards();
    }
    private void initCards(){
        cards = new ArrayList<>(36);
        for (int i = Card.CLUBS; i <= Card.HEARTS; i++) {
            for (int j = Card.SIX; j <= Card.ACE; j++) {
                cards.add(new Card(j, i));
            }
        }
    }
    protected void shuffle(int numCards){
        Collections.shuffle(cards);
        int cardIdx = 0;
        List<List<Card>> playerCards;
        for (int i = 0; i < Table.NUM_PLAYERS; i++) {
            playerCards = new ArrayList<>(5);
            for (int j = 0; j < 5; j++) {
                playerCards.add(new ArrayList<>(9));
            }
            for (int j = cardIdx; j < cardIdx + numCards; j++) {
                Card curr = cards.get(j);
                int idx = ((curr instanceof JokerCard) ? 4 : curr.color);
                playerCards.get(idx).add(curr);
            }
            players[i].setDealtCards(playerCards);
            cardIdx += numCards;
        }
    }
}
