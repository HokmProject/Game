import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket socket;
    private List<Game> games;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;

    public ClientHandler(Socket socket, List<Game> games) {
        this.socket = socket;
        this.games = games;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request;
            while ((request = in.readLine()) != null) {
                if (request.startsWith("CREATE ")) {
                    handleCreateGame(request.substring(7));
                } else if (request.startsWith("JOIN ")) {
                    handleJoinGame(request.substring(5));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleCreateGame(String username) {
        try {
            client = new Client(username, socket);
            synchronized (games) {
                for (Game game : games) {
                    if (game.hasPlayer(username)) {
                        out.println("USERNAME_TAKEN");
                        return;
                    }
                }

                Game game = new Game(client);
                games.add(game);
                out.println("GAME_CREATED " + game.getToken());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleJoinGame(String command) {
        try {
            client = new Client(in.readLine(), socket);
            synchronized (games) {
                if (command.equals("RANDOM")) {
                    for (Game game : games) {
                        if (!game.isFull()) {
                            if (game.addPlayer(client)) {
                                out.println("JOINED_GAME " + game.getToken());
                                return;
                            }
                        }
                    }
                    out.println("NO_AVAILABLE_GAMES");
                } else if (command.startsWith("TOKEN ")) {
                    String token = command.substring(6);
                    for (Game game : games) {
                        if (game.getToken().equals(token)) {
                            if (game.addPlayer(client)) {
                                out.println("JOINED_GAME " + token);
                                return;
                            } else {
                                out.println("GAME_FULL");
                                return;
                            }
                        }
                    }
                    out.println("GAME_NOT_FOUND");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
