import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainFrame extends JFrame {
    private String username;
    private String token;
    private Socket socket;
    private JButton startGameButton;
    private PrintWriter out;
    private JTextArea area;

    public MainFrame(String username, String token, Socket socket , boolean isCreator) {
        this.username = username;
        this.token = token;
        this.socket = socket;
        setTitle("Hokm Game - " + username);

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        area = new JTextArea();
        area.setBounds(20, 20, 793, 500);
        area.setEditable(true);
        area.setFont(area.getFont().deriveFont(15f));
        add(area);
        //this area can contain 25 lines



        Score.score(this);
        CardsInterface.cards(this);

        setSize(825, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel tokenLabel = new JLabel("Game Token: " + token);
        tokenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(tokenLabel, BorderLayout.NORTH);

        // Add game components here

        startGameButton = new JButton("Start Game");
        startGameButton.setEnabled(false);
        add(startGameButton, BorderLayout.SOUTH);

        if(isCreator){
            startGameButton.setEnabled(true);
        }

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("START_GAME "+ token);
                //this process goes through ClientHandler
                System.out.println("Sent START_GAME message from MainFrame"); //debug
            }
        });
    }

    public void updateGameState(String gameState) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                area.append(gameState);
            }
        });
    }

    public void enableStartButton(boolean enable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startGameButton.setEnabled(enable);
            }
        });
    }

    public void showMessage(String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                area.append("\n" + message);
            }
        });
    }

    public void showNotEnoughPlayersMessage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(MainFrame.this, "Not enough players to start the game.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

