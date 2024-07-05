import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JoinFrame extends JFrame {
    private Client client;
    private ObjectInputStream ois;

    public JoinFrame(Client client, ObjectInputStream ois) {
        this.client = client;
        this.ois = ois;

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
                try {
                    client.sendJoinGameRequest(username, token);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        joinRandomButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                try {
                    client.sendJoinRandomGameRequest(username);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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

