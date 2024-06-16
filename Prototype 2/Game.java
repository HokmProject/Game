import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game {
    private String token;
    private String creator;
    private List<String> players = new ArrayList<>();
    private boolean started = false;
    private Map<String, ClientHandler> playerHandlers = new HashMap<>();

    public Game(String token, String creator, ClientHandler creatorHandler) {
        this.token = token;
        this.creator = creator;
        players.add(creator);
        playerHandlers.put(creator, creatorHandler);
    }

    public void addPlayer(String username, ClientHandler handler) {
        if (!isFull() && !started) {
            players.add(username);
            playerHandlers.put(username, handler);
        }
    }

    public void removePlayer(String username) {
        players.remove(username);
        playerHandlers.remove(username);
    }

    public boolean isFull() {
        return players.size() == 2;
    }

    public boolean isCreator(String username) {
        return creator.equals(username);
    }

    public void startGame() {
        if (players.size() == 2) {
            started = true;
        }
    }

    public void notifyPlayers(String message) {
        for (ClientHandler handler : playerHandlers.values()) {
            handler.sendMessage(message);
        }
    }

    public String getToken() {
        return token;
    }
}

