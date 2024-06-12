import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class JoinGameUsernameFrame extends JFrame {
    private JTextField usernameField;
    private JButton joinButton;

    public JoinGameUsernameFrame() {
        setTitle("Join Game");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameField = new JTextField();
        joinButton = new JButton("Next");

        usernameLabel.setBounds(50, 30, 100, 30);
        usernameField.setBounds(150, 30, 100, 30);
        joinButton.setBounds(100, 70, 100, 30);

        add(usernameLabel);
        add(usernameField);
        add(joinButton);

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    new JoinGameFrame(username).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty.");
                }
            }
        });
    }
}
