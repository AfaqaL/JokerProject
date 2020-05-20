public class JokerCard extends Card {
    public static final int UNDER_MODE = 0;
    public static final int TAKE_MODE = 1;
    public static final int GIVE_HIGHEST_MODE = 2;
    public static final int OVER_MODE = 3;

    private int mode;

    /**
     * Sets up a joker card
     */
    public JokerCard() {
        super(0, 0);
    }

    public void setMode(int mode){
        this.mode = mode;
        setJokerPriority();
    }

    private void setJokerPriority() {
        switch (mode){
            case UNDER_MODE:
        }
    }

    @Override
    boolean isValid(Card first, int mode) {
        return true;
    }
}
