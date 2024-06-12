import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 12345;
    private static List<Game> games = new ArrayList<Game>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, games).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized void addGame(Game game) {
        games.add(game);
    }

    public static synchronized List<Game> getGames() {
        return games;
    }
}
