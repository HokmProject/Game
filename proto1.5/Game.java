import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static List<Client> Players;
    private static String Token;
    private HokmGame hokmGame;
    private boolean gameStarted;
    private static Client Creator;

    Game(Client Creator) throws IOException {
        Game.Creator = Creator;
        this.Token = TokenGenerator(20);
        this.Players = new ArrayList<>();
        this.Players.add(Creator);
        this.hokmGame = new HokmGame(Players);
        this.gameStarted = false;

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

//    defines a method hasPlayer that takes a String parameter username and returns a boolean value.
//     It uses Java Streams to check if there is any player in the Players collection whose username matches the input username.
//     If a player with the specified username is found,
//     it returns true; otherwise, it returns false.
    public boolean hasPlayer(String username) {
        return Players.stream().anyMatch(player -> player.getUsername().equals(username));
    }

    public boolean addPlayer(Client client) {
        if (Players.size() < 2) {
            Players.add(client);
            return true;
        }
        return false;
    }

    public String getToken() {
        return Token;
    }

    public List<Client> getPlayers() {
        return Players;
    }
    public Client getCreator(){
        return Creator;
    }

    public boolean isFull() {
        return Players.size() == 2;
    }

    public void startGame() {
        if (isFull()) {
            hokmGame.shuffleAndDistributeCards();
            for (Client player : Players) {
                List<Cards> playerHand = hokmGame.getCardsForPlayer(player);
                StringBuilder cardList = new StringBuilder();
                for (Cards card : playerHand) {
                    cardList.append(card.toString()).append(" ");
                }
//                player.sendMessage("YOUR_CARDS " + cardList.toString().trim());
            }
        } else {
//            Creator.sendMessage("NOT_ENOUGH_PLAYERS ");
        }
    }

    public HokmGame getHokmGame() {
        return hokmGame;
    }

    public void setCreator(Client client) {
        Creator = client;
    }
//    public boolean isGameStarted(){
//        return gameStarted;
//    }
//    public void ShuffleCards(){
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
////                Players.get(i).getCards().get(j).Shuffle();
//            }
//        }
//    }
}

