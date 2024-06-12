import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private String username;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private boolean isHakem;
    private String partner;
    private ArrayList<Cards> cards;


    public Client(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.isHakem = false;
    }

//    public void notifyGameStarted() {
//        // Update GUI to show the start button for the client who created the game
//        if (this == game.getPlayers().get(0)) {
//            Platform.runLater(() -> {
//                // Show start button in the GUI
//            });
//        }
//
//        // Update GUI to display the shuffled cards for all players
//        Platform.runLater(() -> {
//            // Update GUI to display the shuffled cards
//        });
//    }
    public String getUsername() {
        return username;
    }

    public void sendGameState(HokmGame game) {
        // Serialize game state and send to client
        out.println("GAME_STATE " + serializeGameState(game));
    }

    public void sendRoundResult(Client roundWinner) {
        out.println("ROUND_RESULT " + roundWinner.getUsername());
    }

    private String serializeGameState(HokmGame game) {
        // Convert the game state to a string format
        return ""; // Implement actual serialization logic
    }

    public void handleServerMessages() {
        // Listen to server messages and update UI accordingly
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("GAME_STATE ")) {
                        // Update the client UI with the new game state
                        //this is where we write the game state in the text filed of mainFrame
                    } else if (message.startsWith("ROUND_RESULT ")) {
                        // Update the client UI with the round result
                        //this is where we update the result in Score class
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean getIsHakem() {
        return this.isHakem;
    }

    public ArrayList<Cards> getCards() {
        return this.cards;
    }

    public void setHakem(boolean bool) {
        this.isHakem = bool;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

}
