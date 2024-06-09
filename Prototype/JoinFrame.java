import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JoinFrame extends JFrame {
    private Client client;
    private JTextArea textArea;
    private JTextField inputField;
    private JTextField tokenField;
    private JButton sendButton;
    private JButton joinButton;
    private JButton joinRandomButton;

    public JoinFrame(String serverAddress, int serverPort) {
        try {
            client = new Client(serverAddress, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Hokm Game Client");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

//        inputField = new JTextField();
//        sendButton = new JButton("Send");
//
//        sendButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sendRequest();
//            }
//        });

        tokenField = new JTextField();
        joinButton = new JButton("Join Game");

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinGame();
            }
        });

        joinRandomButton = new JButton("Join Random Game");

        joinRandomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinRandomGame();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        JPanel joinPanel = new JPanel();
        joinPanel.setLayout(new BorderLayout());
        joinPanel.add(new JLabel("Game Token: "), BorderLayout.WEST);
        joinPanel.add(tokenField, BorderLayout.CENTER);
        joinPanel.add(joinButton, BorderLayout.EAST);

        JPanel randomJoinPanel = new JPanel();
        randomJoinPanel.setLayout(new BorderLayout());
        randomJoinPanel.add(joinRandomButton, BorderLayout.CENTER);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);
        getContentPane().add(joinPanel, BorderLayout.NORTH);
        getContentPane().add(randomJoinPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void sendRequest() {
        String request = inputField.getText();
        inputField.setText("");
        try {
            client.sendRequest(request);
            Object response = client.receiveResponse();
            textArea.append("Server: " + response + "\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void joinGame() {
        String token = tokenField.getText();
        try {
            client.sendRequest("joinGame:" + token);
            Object response = client.receiveResponse();
            textArea.append("Server: " + response + "\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void joinRandomGame() {
        try {
            client.sendRequest("joinRandomGame");
            Object response = client.receiveResponse();
            textArea.append("Server: " + response + "\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //need a frame to capture the name of the client and then run the server and GUI
                new JoinFrame("localhost", 12345);
            }
        });
    }
}
