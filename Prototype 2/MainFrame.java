import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

public class MainFrame extends JFrame {
    private String username;
    private String token;
    private boolean isCreator;
    private BufferedReader in;

    public MainFrame(String username, String token, boolean isCreator, BufferedReader in) {
        this.username = username;
        this.token = token;
        this.isCreator = isCreator;
        this.in = in;

        setTitle("Hokm Game - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        if (isCreator) {
            JButton startButton = new JButton("Start Game");
            startButton.addActionListener(e -> {
                Client.sendMessage("START_GAME " + token);
            });
            add(startButton, BorderLayout.SOUTH);
        }

        setVisible(true);

        // Listen for server messages
        new Thread(() -> {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    messageArea.append(response + "\n");
                    if (response.equals("Game Started")) {
                        // Handle game start logic here
                    } else if (response.equals("All players are joined.")) {
                        // Handle all players joined logic here
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
