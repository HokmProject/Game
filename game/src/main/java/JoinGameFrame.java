import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class JoinGameFrame extends JFrame {
    private String username;
    private JTextField tokenField;
    private JButton joinRandomButton;
    private JButton joinWithTokenButton;

    public JoinGameFrame(String username) {
        this.username = username;
        setTitle("Join Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel tokenLabel = new JLabel("Enter Token:");
        tokenField = new JTextField();
        joinRandomButton = new JButton("Join Random Game");
        joinWithTokenButton = new JButton("Join with Token");

        tokenLabel.setBounds(50, 30, 100, 30);
        tokenField.setBounds(150, 30, 100, 30);
        joinRandomButton.setBounds(50, 70, 200, 30);
        joinWithTokenButton.setBounds(50, 110, 200, 30);

        add(tokenLabel);
        add(tokenField);
        add(joinRandomButton);
        add(joinWithTokenButton);

        joinRandomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinGame("RANDOM");
            }
        });

        joinWithTokenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String token = tokenField.getText();
                if (!token.isEmpty()) {
                    joinGame("TOKEN " + token);
                } else {
                    JOptionPane.showMessageDialog(null, "Token cannot be empty.");
                }
            }
        });
    }

    private void joinGame(String command) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("JOIN " + command);
            out.println(username);
            String response = in.readLine();
            if (response.startsWith("GAME_NOT_FOUND")) {
                JOptionPane.showMessageDialog(null, "Game not found. Try another one.");
            } else if (response.startsWith("JOINED_GAME")) {
                String token = response.substring(12);
                new MainFrame(username, token, socket).setVisible(true);
                dispose();
            } else if (response.equals("NO_AVAILABLE_GAMES")) {
                JOptionPane.showMessageDialog(null, "No available games. Try again later.");
            } else if (response.equals("GAME_FULL")) {
                JOptionPane.showMessageDialog(null, "Game is full. Try another one.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
