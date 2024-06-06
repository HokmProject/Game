package userInterface;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {

    private JFrame login;
    public login() {
        login = new JFrame("login");
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\ASUS\\Desktop\\Project\\src\\main\\java\\images\\hokm6.jpg");
        JLabel background = new JLabel("", imageIcon, JLabel.CENTER);
        login.setContentPane(background);
        login.setSize(823, 750);
        login.setLayout(null);
        login.setVisible(true);
        login.setResizable(false);
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton create = new JButton("Create Game");
        create.setBounds(10, 10, 250, 100);
        create.setFont(new Font("Arial", Font.ITALIC, 20));
        login.add(create);

        JButton join = new JButton("Join Game");
        join.setBounds(10, 120, 250, 100);
        join.setFont(new Font("Arial", Font.ITALIC , 20));
        login.add(join);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame m1 = new MainFrame();
                login.setVisible(false);
                MainFrame.mainFrame.setVisible(true);
            }
        });
    }
}
