import java.util.List;

public class TableNines extends BasicTable {
    public static final int ROUNDS = 16;
    public static final int TURNS_PER_STAGE = 4;
    public static final int CARDS_PER_TURN = 9;

    private int currRound;
    private int playersMoved;
    private int currActivePlayer;
    private int alreadyDeclared;
    
    public TableNines(int id1, int id2, int id3, int id4){
        super(id1,id2,id3,id4);
        setVariables();
        initGrids();
    }
    
    private void setVariables() {
        currFirstPlayer = 0;
        currRound = 0;
        playersMoved = 0;
        currActivePlayer = 0;
        alreadyDeclared = 0;
    }
    
    private void initGrids() {
        declaresGrid = new int[ROUNDS][NUM_PLAYERS];
        scoresGrid = new int[ROUNDS][NUM_PLAYERS];
        sumsGrid = new int[NUM_STAGES][NUM_PLAYERS];
    }
    
    @Override
    public void startRound() {
        currActivePlayer = ++currFirstPlayer;
        currRound++;
        alreadyDeclared = 0;
        currTaker = null;
        currTakerID = -1;
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
        players[currActivePlayer++].setDeclared(x);
        alreadyDeclared += x;
        return ++playersMoved == 3 ? CARDS_PER_TURN - alreadyDeclared : -1;
    }

    @Override
    public void putCard(Card card) {
        players[currActivePlayer].removeCard(card);
        if(currTaker == null){
            currTaker = card;
            firstCardColor = card.color;
            currTakerID = currActivePlayer;
            for (int others = 0; others < 4; others++) {
                if(others != currActivePlayer)
                    players[others].setValidCards(card, superior);
            }
        }else{
            int res = currTaker.compare(card, superior);
            //TODO: ask how compare works and write logic
        }
        
        if(++playersMoved == 4){
            players[currTakerID].increaseTaken();
        }
        
        currActivePlayer++;
    }

    @Override
    public int[] getRoundScores() {
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            res[i] = players[i].getScore(CARDS_PER_TURN);
        }
        currRound++;
        updateSums(res);
        return res;
    }
    
    private void updateSums(int[] res) {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            sumsGrid[currStage][i] += res[i];
        }
    }
    
    @Override
    public boolean isStageFinished() {
        return currRound % 4 == 0;
    }

    @Override
    public int[] getStageScores() {
        return sumsGrid[currStage++];
    }

    @Override
    public int[] getFinalScores() {
        int[] res = new int[NUM_PLAYERS];
        for (int i = 0; i < NUM_STAGES; i++) {
            for (int j = 0; j < NUM_PLAYERS; j++) {
                res[j] = sumsGrid[i][j];
            }
        }
        return res;
    }
}
