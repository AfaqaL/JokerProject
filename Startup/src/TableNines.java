import java.util.List;

public class TableNines extends BasicTable {
    private static final int ROUNDS = 16;
    private static final int TURNS_PER_STAGE = 4;
    private static final int CARDS_PER_TURN = 9;

    private int currFirstPlayer;
    private int currRound;
    private int playersMoved;
    private int currActivePlayer;
    public TableNines(int id1, int id2, int id3, int id4){
        super(id1,id2,id3,id4);
        currFirstPlayer = 0;
        currRound = 0;
        playersMoved = 0;
        currActivePlayer = 0;
    }

    @Override
    public boolean shuffleCards() {
        if(currRound == ROUNDS) return false;

        super.shuffle(CARDS_PER_TURN);
        return true;
    }

    @Override
    public void setSuperiorCard(int color) {
        superior = color;
    }

    @Override
    public List<Card> getUserCards() {
        return players[currActivePlayer].getPlayerCards();
    }

    @Override
    public int getActiveUser() {
        return currActivePlayer;
    }

    @Override
    public int declareNumber(int x) {
        return 0;
    }

    @Override
    public void putCard(Card card) {

    }

    @Override
    public int[] getRoundScores() {
        return new int[0];
    }

    @Override
    public boolean isStageFinished() {
        return false;
    }

    @Override
    public int[] getStageScores() {
        return new int[0];
    }

    @Override
    public int[] getFinalScores() {
        return new int[0];
    }
}
