import com.sun.tools.javac.Main;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client implements Serializable{
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;
    private Socket socket;
    private ObjectInputStream ois;
    static ObjectOutputStream oos;
    private JFrame mainMenuFrame;

    private JFrame mainFrame;
    private JFrame joinFrame;
    private boolean isHakem;

    private String username;
    private ArrayList<Cards> playercards;

    private List<MainFrame> mainFrameList;
    public Client() {}

    public Client(String username) {
        playercards = new ArrayList<>();
        isHakem = false;
        this.username = username;
        this.mainFrameList = new ArrayList<>();
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
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainFrame showCreateGameDialog() throws IOException {
        String username = JOptionPane.showInputDialog("Enter username:"); // a dialog JOptionPane opens for input
        this.username = username;
        if (username != null && !username.isEmpty()) {
            sendMessage("CREATE_GAME " + username); // sends the request
            try {
                Object response = ois.readObject();
                if (response instanceof String & ((String) response).startsWith("GAME_CREATED")) {
                    String token = ((String) response).split(" ")[1];
                    JOptionPane.showMessageDialog(null, "Game created with token: " + token);
                    mainMenuFrame.dispose();  // Closes the main menu
//                    setMainFrame(new MainFrame(username , token , true , in));
                    sendMessage(new MainFrame(username , token , true , ois));
//                    sendMessage(this);
//                    addClientMainFrame(username , mainFrame);
//                    JOptionPane.showMessageDialog(null, response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void showJoinGameDialog() {
        joinFrame = new JoinFrame(this, ois);
    }

    public MainFrame sendJoinGameRequest(String username, String token) throws IOException {
        sendMessage("JOIN_GAME " + username + " " + token);
        this.username = username;
        try {
            Object response = ois.readObject();
            if (response instanceof String && ((String) response).startsWith("JOINED_GAME")) {
                JOptionPane.showMessageDialog(null, "Joined game with token: " + token);
                joinFrame.dispose(); // Close join menu
                mainMenuFrame.dispose(); // Close main menu
//                setMainFrame(new MainFrame(username , token , false , in));
                sendMessage(new MainFrame(username , token , false , ois));
//                sendMessage(this);
//                addClientMainFrame(username , mainFrame);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public MainFrame sendJoinRandomGameRequest(String username) throws IOException {
        sendMessage("JOIN_RANDOM_GAME " + username);
        this.username = username;
        try {
            Object response = ois.readObject();
            if (response instanceof String && ((String) response).startsWith("JOINED_GAME")) {
                String token = ((String) response).split(" ")[1];
                JOptionPane.showMessageDialog(null, "Joined random game with token: " + token);
                joinFrame.dispose(); // Close join menu
                mainMenuFrame.dispose(); // Close main menu
//                setMainFrame(new MainFrame(username , token , false , in));
//                addClientMainFrame(username , mainFrame);
                sendMessage(new MainFrame(username , token , false , ois));
//                sendMessage(this);
            } else {
                JOptionPane.showMessageDialog(null, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void sendMessage(Object object) throws IOException {
        oos.writeObject(object);// this is where the requests go to ClientHandler's ObjectInputStream
        oos.flush();
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


    public String getUsername() {
        return username;
    }

    public List<MainFrame> getMainFrame() {
        return mainFrameList;
    }


//    public void addClientMainFrame(String username, JFrame mainFrame) {
//        clientMainFrame.put(username, (MainFrame) mainFrame);
//    }
//
//    public MainFrame getClientMainFrame(String username) {
//        return clientMainFrame.get(username);
//    }
//
//    public void removeClient(String username) {
//        clientMainFrame.remove(username);
//    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }


}
