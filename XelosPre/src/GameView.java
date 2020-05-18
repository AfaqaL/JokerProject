import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private JTextField numField1, numField2, numField3, numField4;
    private JTextField validCards1, validCards2, validCards3, validCards4;
    private JTextField chosenCard1, chosenCard2, chosenCard3, chosenCard4;

    public GameView() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(4, 4));

        JPanel userPanel1 = new JPanel();
        userPanel1.setLayout(new BoxLayout(userPanel1, BoxLayout.Y_AXIS));
        userPanel1.add(new JLabel("User1"));
        numField1 = new JTextField();
        userPanel1.add(numField1);
        JButton declareButton1 = new JButton("Declare");
        declareButton1.addActionListener(actionEvent -> {

        });
        userPanel1.add(declareButton1);
        validCards1 = new JTextField();
        validCards1.setEditable(false);
        userPanel1.add(validCards1);
        chosenCard1 = new JTextField();
        userPanel1.add(chosenCard1);
        JButton putCard1 = new JButton("Put");
        putCard1.addActionListener(actionEvent -> {

        });
        userPanel1.add(putCard1);

        JPanel userPanel2 = new JPanel();
        userPanel2.setLayout(new BoxLayout(userPanel2, BoxLayout.X_AXIS));
        userPanel2.add(new JLabel("User2"));
        numField2 = new JTextField();
        userPanel2.add(numField2);
        JButton declareButton2 = new JButton("Declare");
        declareButton2.addActionListener(actionEvent -> {

        });
        userPanel2.add(declareButton2);
        validCards2 = new JTextField();
        validCards2.setEditable(false);
        userPanel2.add(validCards2);
        chosenCard2 = new JTextField();
        userPanel2.add(chosenCard2);
        JButton putCard2 = new JButton("Put");
        putCard2.addActionListener(actionEvent -> {

        });
        userPanel2.add(putCard2);

        JPanel userPanel3 = new JPanel();
        userPanel3.setLayout(new BoxLayout(userPanel3, BoxLayout.Y_AXIS));
        userPanel3.add(new JLabel("User3"));
        numField3 = new JTextField();
        userPanel3.add(numField3);
        JButton declareButton3 = new JButton("Declare");
        declareButton3.addActionListener(actionEvent -> {

        });
        userPanel3.add(declareButton3);
        validCards3 = new JTextField();
        validCards3.setEditable(false);
        userPanel3.add(validCards3);
        chosenCard3 = new JTextField();
        userPanel3.add(chosenCard3);
        JButton putCard3 = new JButton("Put");
        putCard3.addActionListener(actionEvent -> {

        });
        userPanel3.add(putCard3);

        JPanel userPanel4 = new JPanel();
        userPanel4.setLayout(new BoxLayout(userPanel4, BoxLayout.X_AXIS));
        userPanel4.add(new JLabel("User4"));
        numField4 = new JTextField();
        userPanel4.add(numField4);
        JButton declareButton4 = new JButton("Declare");
        declareButton4.addActionListener(actionEvent -> {

        });
        userPanel4.add(declareButton4);
        validCards4 = new JTextField();
        validCards4.setEditable(false);
        userPanel4.add(validCards4);
        chosenCard4 = new JTextField();
        userPanel4.add(chosenCard4);
        JButton putCard4 = new JButton("Put");
        putCard4.addActionListener(actionEvent -> {

        });
        userPanel4.add(putCard4);

        mainPanel.add(userPanel1, BorderLayout.WEST);
        mainPanel.add(userPanel3, BorderLayout.EAST);
        mainPanel.add(userPanel4, BorderLayout.NORTH);
        mainPanel.add(userPanel2, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(400, 400));
        //setResizable(false);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new GameView();
    }
}
