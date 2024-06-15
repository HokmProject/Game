import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private String username;
    private Socket socket;
    private String token;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isCreator;
    private MainFrame mainFrame;

    public Client(String username, Socket socket ,boolean isCreator) throws IOException {
        this.username = username;
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String getUsername() {
        return username;
    }

    private class ServerMessageHandler implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received message: " + message);
                    if (message.startsWith("GAME_START")) {
                        mainFrame.updateGameState("Game Started!");
                        // Implement card distribution and game start logic
                    }
                    else if (message.startsWith("UPDATE_STATE")) {
                        mainFrame.updateGameState(message.substring(12));
                    }
                    else if (message.startsWith("ENABLE_START_BUTTON")) {
                        mainFrame.enableStartButton(true);
                    }
                    else if (message.startsWith("NOT_ENOUGH_PLAYERS")) {
                        mainFrame.showNotEnoughPlayersMessage();
                    }
                    // Handle other server messages
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
//                      where is out going?????????????
    public void sendMessage(String message) {
        this.out.println(message);
        if (message.startsWith("GAME_START")) {
            mainFrame.updateGameState("Game Started!");
            // Implement card distribution and game start logic
        }
        else if (message.startsWith("UPDATE_STATE")) {
            mainFrame.updateGameState(message.substring(12));
        }
        else if (message.startsWith("ENABLE_START_BUTTON")) {
            mainFrame.enableStartButton(true);
        }
        else if (message.startsWith("NOT_ENOUGH_PLAYERS")) {
            mainFrame.showNotEnoughPlayersMessage();
        }
        System.out.println("Sent message: " + message);
    }
}

