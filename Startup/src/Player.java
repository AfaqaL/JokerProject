import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<List<Card>> cards;
    private final int id;
    private int taken;

    public Player(int id){
        this.id = id;
        taken = 0;
        cards = new ArrayList<>(5);
        for (int i=0; i<5; i++)
            cards.add(new ArrayList<>(9));
    }

    public void setValidCards(Card firstCard, int superior) {
        int color = firstCard.color;
        setValid(cards.get(4));

        if (!cards.get(color).isEmpty()) {
            setValid(cards.get(color));
            return;
        }

        if (!cards.get(superior).isEmpty()) {
            setValid(cards.get(superior));
            return;
        }

        for (int i = 0; i < 4; i++)
            setValid(cards.get(i));
    }

    private void setValid(List<Card> cards) {
        for (Card c : cards)
                c.setValid(true);
    }

    public void removeCard(Card chosen){
        if(chosen instanceof JokerCard)
            cards.get(4).remove(chosen);
        else
            cards.get(chosen.color).remove(chosen);
    }

    public void setDealtCards(List<List<Card>> dealtCards){
        this.cards = dealtCards;
    }

    public void increaseTaken() {
        taken++;
    }
}
