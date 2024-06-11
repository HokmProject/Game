import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton createGameButton;
    private JButton joinGameButton;

    public MainMenu() {
        setTitle("Hokm Game");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        createGameButton = new JButton("Create Game");
        joinGameButton = new JButton("Join Game");

        createGameButton.setBounds(50, 30, 200, 30);
        joinGameButton.setBounds(50, 70, 200, 30);

        add(createGameButton);
        add(joinGameButton);

        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateGameFrame().setVisible(true);
                dispose();
            }
        });

        joinGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JoinGameFrame().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}

