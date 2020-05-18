import java.util.Scanner;

public class GameConsole {
    private Scanner scanner;
    private DummyTable table;

    public GameConsole() {
        scanner = new Scanner(System.in);
        table = new DummyTable();

        showFirstOrder();

        int numRounds = table.getNumRounds();
        for (int round = 1; round <= numRounds; round++) {
            playRound(round);
            System.out.println("Round finished");
        }
    }

    private void playRound(int round) {
        System.out.println("Round: " + round);

        table.shuffleCards();
        showAllUsersCards();
        declareNums();

        int numCards = table.getNumCards();
        for (int turn = 1; turn <= numCards; turn++) {
            putCards();
            System.out.println("Turn finished");
        }
        table.updateScores();
    }

    private void putCards() {
        for (int turn = 0; turn < 4; turn++) {
            int id = table.getActiveUser();
            showUserCards(id, true);
            System.out.printf("User#%d put: ", id);

            int card = scanner.nextInt();
            table.putCard(card);
        }
    }

    private void declareNums() {
        for (int turn = 0; turn < 4; turn++) {
            int id = table.getActiveUser();
            System.out.printf("User#%d declare: ", id);

            int num = scanner.nextInt();
            table.declareNumber(num);
        }
    }

    private void showAllUsersCards() {
        for (int id = 0; id < 4; id++) {
            showUserCards(id, false);
        }
    }

    private void showUserCards(int id, boolean valid) {
        int[] cards = table.getUserCards(id);
        if (valid) System.out.printf("User#%d valid cards: ", id);
        else System.out.printf("User#%d have: ", id);
        for (int card : cards) {
            System.out.print(card + " ");
        }
        System.out.println();
    }

    private void showFirstOrder() {
        int[] order = table.getOrder();
        System.out.print("Order: ");
        for (int player : order) {
            System.out.print(player + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        GameConsole console = new GameConsole();
    }
}
