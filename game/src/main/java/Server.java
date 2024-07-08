import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Serializable{
    private static final int PORT = 1234;
    static Map<String, Game> activeGames = new HashMap<>(); // A Hashmap with key of Token and Game object value
    static Set<String> activeUsers = new HashSet<>(); // A Set with values of usernames , so there won't be duplicates
    static List<Game> waitingGames = new ArrayList<>(); // A List of Game objects that are waiting for players to join
    static List<ClientHandler> clientHandlers = new ArrayList<>(); // A List of Client objects
    public static void main(String[] args) {
        System.out.println("[SERVER] : Game server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            /*
            The ServerSocket class represents a socket that listens for connection requests
            from clients on a specified port
             */
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start(); // for each client , a new clienthandler is created
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}