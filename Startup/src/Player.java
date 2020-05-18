import java.util.List;

public class Player {
    private List<Card> cards;
    private final int id;

    public Player(int id){
        this.id = id;
    }
    public List<Card> validCards(Card currentHighest){
        return null;
    }

    public void removeCard(Card chosen){

    }
    public void getDealtCards(List<Card> dealtCards){
        cards = dealtCards;
    }
}
