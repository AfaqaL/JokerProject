import java.util.List;

public class Player {
    private List<Card> cards;
    private final int id;

    public Player(int id){
        this.id = id;
    }

    public void validCards(Card currentHighest) {

    }

    public void removeCard(Card chosen){

    }

    public void getDealtCards(List<Card> dealtCards){
        cards = dealtCards;
    }
}