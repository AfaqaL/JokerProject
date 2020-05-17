import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest extends TestCase {

    @Test
    public void testEnum(){
        ArrayList <Card> deck = new ArrayList<>();
        for (int i = Card.CLUBS; i <= Card.HEARTS; i++){
            for (int j = Card.SIX; j < Card.ACE; j++)
            deck.add(new Card(j,i));
        }

        deck.add(new Card(Card.JOKER, Card.NOTYPE));
        deck.add(new Card(Card.JOKER, Card.NOTYPE));
        System.out.println(deck);
    }

}