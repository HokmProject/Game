
import javax.swing.*;
import java.awt.*;

public class Score {

    static String points, points2, rounds, rounds2;
    static JFrame score(JFrame frame) {
        points = points2 = rounds2 = rounds = "0";
        JLabel team1score= new JLabel("team 1: "+points+"                                               rounds: "+rounds);
        team1score.setBounds(10, 620, 400, 40);
        team1score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        frame.add(team1score);

        JLabel team2score = new JLabel("team 2: "+points2+"                                               rounds: "+rounds2);
        team2score.setBounds(10, 670, 400, 40);
        team2score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        frame.add(team2score);
//
//        JLabel ID = new JLabel("Group ID: ");
//        ID.setBounds(450, 610, 300, 70);
//        ID.setFont(new Font("Arial", Font.BOLD, 30));
//        frame.add(ID);
        return frame;
    }

}
