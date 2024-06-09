import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<Game> games;
    private ArrayList<ClientHandler> clients;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        games = new ArrayList<>();
        clients = new ArrayList<>();
        serverSocket = new ServerSocket(port);
    }

    public synchronized void createNewGame(Game game) {
        games.add(game);
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public synchronized ArrayList<Game> getGames() {
        return games;
    }

    public void start() {
        System.out.println("Server started");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, clientSocket);
                addClient(clientHandler);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(12345);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
