import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object request = input.readObject();
                Object response = handleRequest(request);
                sendResponse(response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server.removeClient(this);
        }
    }

    private Object handleRequest(Object request) {
        if (request instanceof String) {
            String command = (String) request;
            if (command.startsWith("joinGame:")) {
                String token = command.split(":")[1];
                // Implement logic to join an existing game using the token
                boolean joined = joinGameWithToken(token);
                if (joined) {
                    return "Joined Game with Token: " + token;
                } else {
                    return "Failed to Join Game with Token: " + token;
                }
            } else if (command.equals("joinRandomGame")) {
                boolean joined = joinRandomGame();
                if (joined) {
                    return "Joined a Random Game With Token : " + Game.getToken();
                } else {
                    return "Failed to Join a Random Game";
                }
            }
            switch (command) {
                case "createGame":
                    server.createNewGame(new Game());
                    return "Game Created Token: " + Game.getToken();
                default:
                    return "Unknown Command";
            }
        }
        return "Invalid Request";
    }

    private boolean joinGameWithToken(String token) {
        // Implement the logic to join a game with the provided token
        for (Game game : server.getGames()) {
            if (game.getToken().equals(token)) {
                // Logic to add player to the game
                return true;
            }
        }
        return false;
    }

    private boolean joinRandomGame() {
        // Implement the logic to join a random game
        if (!server.getGames().isEmpty()) {
            Game game = server.getGames().get(0); // For simplicity, join the first game
            // Logic to add player to the game
            return true;
        }
        return false;
    }

    public void sendResponse(Object response) throws IOException {
        output.writeObject(response);
    }
}


