import java.util.List;

public class TableNines implements Table {
    private Player[] players;
    private int currFirstPlayer;
    private static final int ROUNDS = 16;
    private static final int TURNS_PER_STAGE = 4;
    private int currTurn;
    public TableNines(int id1, int id2, int id3, int id4){
        players = new Player[4];
        players[0] = new Player(id1);
        players[1] = new Player(id2);
        players[2] = new Player(id3);
        players[3] = new Player(id4);
        currFirstPlayer = 0;
    }
    @Override
    public int getNumRounds() {
        return 0;
    }

    @Override
    public void shuffle() {

    }

    @Override
    public int getNumCards() {
        return 0;
    }

    @Override
    public List<Card> getCards(int id) {
        return null;
    }

    @Override
    public int getCurrentUser() {
        return 0;
    }

    @Override
    public void declare(int x) {

    }

    @Override
    public void putCard(Card card) {

    }

    @Override
    public void updateScores() {

    }
}
