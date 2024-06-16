import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private Game game;

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
        if (Server.activeUsers.contains(username)) {
            out.println("ERROR Username already exists.");
            return;
        }
        Server.activeUsers.add(username);
        String gameToken = UUID.randomUUID().toString();
        game = new Game(gameToken, username, this);
        Server.activeGames.put(gameToken, game);
        Server.waitingGames.add(game);
        out.println("GAME_CREATED " + gameToken);
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
        game = Server.waitingGames.get(0);
        Server.activeUsers.add(username);
        game.addPlayer(username, this);
        out.println("JOINED_GAME " + game.getToken());
        if (game.isFull()) {
            game.notifyPlayers("[SERVER] : All players are joined.");
            Server.waitingGames.remove(game);
        }
    }

    private void handleStartGame(String command) {
        // Example: START_GAME token
        String[] tokens = command.split(" ");
        String token = tokens[1];
        game = Server.activeGames.get(token);
        if (game != null && game.isCreator(username) && game.isFull()) {
            game.startGame();
            game.notifyPlayers("[SERVER] : !!! Game Started by " + username + " !!!");
        }
        else if(!game.isFull()){
            out.println("[ERROR] : Game is not full. Cannot Start the Game . Waiting for Players ...");
        }
        else {
            out.println("[ERROR] : You are not the game creator or game not found.");
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

