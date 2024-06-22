import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private Game game;
    private static Map<String, MainFrame> clients = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String command = in.readLine();
                if (command.startsWith("CREATE_GAME")) {
                    handleCreateGame(command);
                } else if (command.startsWith("JOIN_GAME")) {
                    handleJoinGame(command);
                } else if (command.startsWith("JOIN_RANDOM_GAME")) {
                    handleJoinRandomGame(command);
                } else if (command.startsWith("START_GAME")) {
                    handleStartGame(command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (username != null) {
                Server.activeUsers.remove(username);
            }
            if (game != null) {
                game.removePlayer(username);
            }
        }
    }

    private void handleCreateGame(String command) {
        // Example: CREATE_GAME username
        String[] tokens = command.split(" ");
        username = tokens[1];
        if (Server.activeUsers.contains(username)) { // if there is already a username
            out.println("[ERROR] : Username already exists."); // send a request(message) to
            return;
        }
        Server.activeUsers.add(username);
        String gameToken = TokenGenerator(10).toString(); // a token is generated for Game
        game = new Game(gameToken, username, this);
        Server.activeGames.put(gameToken, game); // a Game object is added to the Server's List of Games
        Server.waitingGames.add(game); // will be waiting for other players to join
        out.println("GAME_CREATED " + gameToken); // sends a request to Client's Input
    }

    private void handleJoinGame(String command) {
        // Example: JOIN_GAME username token
        String[] tokens = command.split(" ");
        username = tokens[1];
        String token = tokens[2];
        if (Server.activeUsers.contains(username)) {
            out.println("[ERROR] : Username already exists.");
            return;
        }
        game = Server.activeGames.get(token);
        if (game == null || game.isFull()) {
            out.println("[ERROR] : Game not available.");
            return;
        }
        Server.activeUsers.add(username);
        game.addPlayer(username, this);
        out.println("JOINED_GAME " + token);
        if (game.isFull()) {
            game.notifyPlayers("[SERVER] : All players are joined. You can start the game now !");
            Server.waitingGames.remove(game);
        }
    }

    private void handleJoinRandomGame(String command) {
        // Example: JOIN_RANDOM_GAME username
        String[] tokens = command.split(" ");
        username = tokens[1];
        if (Server.activeUsers.contains(username)) {
            out.println("[ERROR] : Username already exists.");
            return;
        }
        if (Server.waitingGames.isEmpty()) {
            out.println("[ERROR] : No available games.");
            return;
        }
        game = Server.waitingGames.get(0); //gets the first game from the waiting games list
        Server.activeUsers.add(username);
        game.addPlayer(username, this);
        out.println("JOINED_GAME " + game.getToken());
        if (game.isFull()) {
            game.notifyPlayers("[SERVER] : All players are joined."); // sends a message to all players of the game
            Server.waitingGames.remove(game); // it gets removed from the list of waiting games because it's full
        }
    }

    private void handleStartGame(String command) {
        // Example: START_GAME token
        String[] tokens = command.split(" ");
        String token = tokens[1];
        game = Server.activeGames.get(token); // gets the game from server
        if (game != null && game.isCreator(username) && game.isFull()) { // if the game exists and the username of the one that sent the request is the same as Creator and the game is Filled
            game.startGame(); // starts the game
            game.notifyPlayers("[SERVER] : !!! Game Started by " + username + " !!!");

        } else if (!game.isFull()) {
            out.println("[ERROR] : Game is not full. Cannot Start the Game . Waiting for Players ..."); // sends a message to Client
        } else {
            out.println("[ERROR] : You are not the game creator or game not found.");
        }
    }

    static String TokenGenerator(int n) {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public void sendMessage(String message) {
        out.println(message); // sends a message to MainFrame
    }

    public static void addClient(String username, MainFrame mainFrame) {
        ClientHandler.clients.put(username, mainFrame);
    }

    public static MainFrame getClient(String username) {
        return ClientHandler.clients.get(username);
    }

    public static void removeClient(String username) {
        clients.remove(username);
    }

}