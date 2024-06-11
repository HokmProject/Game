import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static List<Client> Players;
    private static String Token;

    Game(Client Creator) throws IOException {

        this.Token = TokenGenerator(20);
        this.Players = new ArrayList<>();
        this.Players.add(Creator);

    }

    static String TokenGenerator(int n)
    {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
    public boolean hasPlayer(String username) {
        return Players.stream().anyMatch(player -> player.getUsername().equals(username));
    }

    public void addPlayer(Client client) {
        Players.add(client);
    }

    public String getToken() {
        return Token;
    }

    public List<Client> getPlayers() {
        return Players;
    }

    public boolean isFull() {
        return Players.size() == 4;
    }
}

