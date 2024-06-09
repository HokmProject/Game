import javax.swing.*;
import java.awt.*;

public class MainFrame {
    public static JFrame mainFrame;


    public MainFrame() {
        mainFrame = new JFrame("Hokm");

        JTextArea area = new JTextArea();
        area.setBounds(10, 10, 793, 500);
        area.setEditable(false);
        area.setFont(area.getFont().deriveFont(15f));
        mainFrame.add(area);
        //this area can contain 25 lines

        Score.score(mainFrame);
        CardsInterface.cards(mainFrame);

        mainFrame.setSize(823, 765);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
