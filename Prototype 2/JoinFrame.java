import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

public class JoinFrame extends JFrame {
    private Client client;
    private BufferedReader in;

    public JoinFrame(Client client, BufferedReader in) {
        this.client = client;
        this.in = in;

        setTitle("Join a Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLayout(new GridLayout(3, 1));

        JTextField tokenField = new JTextField();
        JTextField usernameField = new JTextField();
        JButton joinTokenButton = new JButton("Join with Token");
        JButton joinRandomButton = new JButton("Join Random Game");

        joinTokenButton.addActionListener(e -> {
            String token = tokenField.getText();
            String username = usernameField.getText();
            if (!token.isEmpty() && !username.isEmpty()) {
                client.sendJoinGameRequest(username, token);
            }
        });

        joinRandomButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                client.sendJoinRandomGameRequest(username);
            }
        });

        add(new JLabel("Enter game token:"));
        add(tokenField);
        add(new JLabel("Enter username:"));
        add(usernameField);
        add(joinTokenButton);
        add(joinRandomButton);

        setVisible(true);
    }
}

