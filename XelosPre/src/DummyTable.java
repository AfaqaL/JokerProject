import java.util.Random;

public class DummyTable {
    private Random random;
    private int numCards, numRounds;

    public DummyTable() {
        random = new Random();
        numCards = 1;
        numRounds = 8;
    }

    public int[] getOrder() {
        return new int[] {0, 1, 2, 3};
    }


    public int getNumRounds() {
        return numRounds;
    }

    public int getNumCards() {
        return numCards;
    }

    public void shuffleCards() {}

    public int[] getUserCards(int id) {
        int[] res = new int[numCards];
        for (int i = 0; i < numCards; i++) {
            res[i] = Math.abs(random.nextInt()) % 36;
        }
        return res;
    }

    public int getActiveUser() {
        return Math.abs(random.nextInt()) % 4;
    }

    public void declareNumber(int num) {

    }

    public void putCard(int card) {

    }

    public void updateScores() {
        numCards++;
    }
}
