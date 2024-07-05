import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenu extends JFrame {
    private Client client;

    public MainMenu(Client client) {
        this.client = client;

        setTitle("Hokm Game - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        JButton createGameButton = new JButton("Create a Game");
        JButton joinGameButton = new JButton("Join a Game");

        createGameButton.addActionListener(e -> {
            try {
                client.showCreateGameDialog();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }); // the Method of sending a Create
                                                                                //request from client is called
        joinGameButton.addActionListener(e -> client.showJoinGameDialog()); // for joining a Game

        menuPanel.add(createGameButton);
        menuPanel.add(joinGameButton);
        add(menuPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

