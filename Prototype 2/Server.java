import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    static Map<String, Game> activeGames = new HashMap<>();
    static Set<String> activeUsers = new HashSet<>();
    static List<Game> waitingGames = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Game server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}