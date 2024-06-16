import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private static PrintWriter out;
    private JFrame mainMenuFrame;
    private JFrame joinFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client().createAndShowGUI());
    }

    public void createAndShowGUI() {
        mainMenuFrame = new MainMenu(this);
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCreateGameDialog() {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username != null && !username.isEmpty()) {
            sendMessage("CREATE_GAME " + username);
            try {
                String response = in.readLine();
                if (response.startsWith("GAME_CREATED")) {
                    String token = response.split(" ")[1];
                    JOptionPane.showMessageDialog(null, "Game created with token: " + token);
                    mainMenuFrame.dispose(); // Close main menu
                    new MainFrame(username, token, true, in);
                } else {
                    JOptionPane.showMessageDialog(null, response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showJoinGameDialog() {
        joinFrame = new JoinFrame(this, in);
    }

    public void sendJoinGameRequest(String username, String token) {
        sendMessage("JOIN_GAME " + username + " " + token);
        try {
            String response = in.readLine();
            if (response.startsWith("JOINED_GAME")) {
                JOptionPane.showMessageDialog(null, "Joined game with token: " + token);
                joinFrame.dispose(); // Close join menu
                mainMenuFrame.dispose(); // Close main menu
                new MainFrame(username, token, false, in);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendJoinRandomGameRequest(String username) {
        sendMessage("JOIN_RANDOM_GAME " + username);
        try {
            String response = in.readLine();
            if (response.startsWith("JOINED_GAME")) {
                String token = response.split(" ")[1];
                JOptionPane.showMessageDialog(null, "Joined random game with token: " + token);
                joinFrame.dispose(); // Close join menu
                mainMenuFrame.dispose(); // Close main menu
                new MainFrame(username, token, false, in);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String message) {
        out.println(message);
    }
}


