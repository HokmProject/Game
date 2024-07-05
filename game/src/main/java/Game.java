import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game implements Serializable {
    private String token;
    private String creator;
    private String Hokm;
    private List<String> players = new ArrayList<>();
    List<Client> clients = new ArrayList<>();
    private boolean started = false;
    private Map<String, ClientHandler> playerHandlers = new HashMap<>();
    private ClientHandler[] Team1;
    private ClientHandler[] Team2;

    public Game(String token, String creator, ClientHandler creatorHandler) {
        this.token = token;
        this.creator = creator;
        players.add(creator); // the username will be added to the list of players
        clients.add(new Client(creator)); // Creates a new Client and is Saved in a List
        playerHandlers.put(creator, creatorHandler); // puts the ClientHandler into the list
    }

    public void addPlayer(String username, ClientHandler handler) {
        if (!isFull() && !started) {
            players.add(username);
            playerHandlers.put(username, handler);
            clients.add(new Client(username));
        }
    }

    public void removePlayer(String username) {
        players.remove(username);
        playerHandlers.remove(username);
    }

    public boolean isFull() {
        return players.size() == 4;
    }

    public boolean isCreator(String username) {
        return creator.equals(username);
    }

    public void startGame() throws IOException {
        if (players.size() == 4) {
            started = true;
            new Deal(playerHandlers , clients);

            Team1 = new ClientHandler[2];

            Team1[0] = playerHandlers.get(players.get(0));
            Team1[1] = playerHandlers.get(players.get(2));

            Team1[0].sendObject("[SERVER] : Your TeamMate is {"+ Team1[1].getUsername()+"}");
            Team1[1].sendObject("[SERVER] : Your TeamMate is {"+ Team1[0].getUsername()+"}");
            Team2 = new ClientHandler[2];

            Team2[0] = playerHandlers.get(players.get(1));
            Team2[1] = playerHandlers.get(players.get(3));

            Team2[0].sendObject("[SERVER] : Your TeamMate is {"+ Team2[1].getUsername()+"}");
            Team2[1].sendObject("[SERVER] : Your TeamMate is {"+ Team2[0].getUsername()+"}");
        }
    }

    public void notifyPlayers(String message) throws IOException {
        for (ClientHandler handler : playerHandlers.values()) {
            handler.sendObject(message);
        }
    }

    public String getToken() {
        return token;
    }
    public void setHokm(String Hokm) {
        this.Hokm = Hokm;
    }
}

