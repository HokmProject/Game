import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private static PrintWriter out;
    private JFrame mainMenuFrame;
    private static MainFrame ClientMainFrame;
    private JFrame joinFrame;
    private boolean isHakem;

    private String username;
    private ArrayList<Cards> playercards;

    public Client() {}

    public Client(String username) {
        playercards = new ArrayList<>();
        isHakem = false;
        this.username = username;
    }

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
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Collecting Data from Socket
            out = new PrintWriter(socket.getOutputStream(), true); // for input of user to go output
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainFrame showCreateGameDialog() {
        String username = JOptionPane.showInputDialog("Enter username:"); // a dialog JOptionPane opens for input
        this.username = username;
        if (username != null && !username.isEmpty()) {
            sendMessage("CREATE_GAME " + username); // sends the request
            try {
                String response = in.readLine();
                if (response.startsWith("GAME_CREATED")) {
                    String token = response.split(" ")[1];
                    JOptionPane.showMessageDialog(null, "Game created with token: " + token);
                    mainMenuFrame.dispose();  // Closes the main menu
                    ClientMainFrame = new MainFrame(username, token, true, in); // opens up a new MainFrame and saves the mainFrame to a Variable (might be used for passing mainframe)
                    ClientHandler.addClient(username , ClientMainFrame); // puts the mainFrame in a list(explain problem)
                } else {
                    JOptionPane.showMessageDialog(null, response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void showJoinGameDialog() {
        joinFrame = new JoinFrame(this, in);
    }

    public MainFrame sendJoinGameRequest(String username, String token) {
        sendMessage("JOIN_GAME " + username + " " + token);
        this.username = username;
        try {
            String response = in.readLine();
            if (response.startsWith("JOINED_GAME")) {
                JOptionPane.showMessageDialog(null, "Joined game with token: " + token);
                joinFrame.dispose(); // Close join menu
                mainMenuFrame.dispose(); // Close main menu
                ClientMainFrame = new MainFrame(username, token, false, in);
                ClientHandler.addClient(username , ClientMainFrame);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MainFrame sendJoinRandomGameRequest(String username) {
        sendMessage("JOIN_RANDOM_GAME " + username);
        this.username = username;
        try {
            String response = in.readLine();
            if (response.startsWith("JOINED_GAME")) {
                String token = response.split(" ")[1];
                JOptionPane.showMessageDialog(null, "Joined random game with token: " + token);
                joinFrame.dispose(); // Close join menu
                mainMenuFrame.dispose(); // Close main menu
                ClientMainFrame = new MainFrame(username, token, false, in);
                ClientHandler.addClient(username , ClientMainFrame);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendMessage(String message) {
        out.println(message); // this is where the requests go to ClientHandler's BufferedReader
    }

    public boolean getIsHakem() {
        return this.isHakem;
    }

    public ArrayList<Cards> getCards() {
        return this.playercards;
    }

    public void setHakem(boolean bool) {
        this.isHakem = bool;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.playercards = cards;
    }

    public MainFrame getMainFrame() {
        return ClientMainFrame;
    }

    public String getUsername() {
        return username;
    }

}
