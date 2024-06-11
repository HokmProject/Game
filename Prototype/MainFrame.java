import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class MainFrame extends JFrame {
    private String username;
    private String token;
    private Socket socket;

    public MainFrame(String username, String token, Socket socket) {
        this.username = username;
        this.token = token;
        this.socket = socket;
        setTitle("Hokm Game - " + username);

        JTextArea area = new JTextArea();
        area.setBounds(20, 20, 793, 500);
        area.setEditable(true);
        area.setFont(area.getFont().deriveFont(15f));
        add(area);
        //this area can contain 25 lines

        Score.score(this);
        CardsInterface.cards(this);

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel tokenLabel = new JLabel("Game Token: " + token);
        tokenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(tokenLabel, BorderLayout.NORTH);

        // Add game components here
    }
}

