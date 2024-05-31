package UserInterface;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    static JFrame mainFrame;


    public MainFrame() {
        mainFrame = new JFrame("Hokm");

        JTextArea area = new JTextArea();
        area.setBounds(10, 10, 793, 500);
        area.setEditable(false);
        area.setFont(area.getFont().deriveFont(15f));
        mainFrame.add(area);
        //this area can contain 25 lines

        JLabel team1score= new JLabel("team 1:                                                rounds:");
        team1score.setBounds(10, 600, 400, 40);
        team1score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,Color.black));
        mainFrame.add(team1score);

        JLabel team2score = new JLabel("team 2:                                                rounds:");
        team2score.setBounds(10, 650, 400, 40);
        team2score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        mainFrame.add(team2score);

        Cards.cards(mainFrame);

        mainFrame.setSize(823, 750);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        MainFrame mf1 = new MainFrame();
    }
}
