import java.util.List;
import java.util.Scanner;

public class Play {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 1st player id:");
        int id1 = scanner.nextInt();
        System.out.println("Enter 2nd player id:");
        int id2 = scanner.nextInt();
        System.out.println("Enter 3rd player id:");
        int id3 = scanner.nextInt();
        System.out.println("Enter 4th player id:");
        int id4 = scanner.nextInt();

        Table table = new TableNines(id1, id2, id3, id4);

        //here should come request from the server about superior color called by first player
        System.out.println("Call superior color: ");
        table.setSuperiorCard(scanner.nextInt());

        //here server must give first player declaration
        System.out.println("Say first: ");


    }
}
