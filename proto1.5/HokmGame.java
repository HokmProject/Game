import java.util.*;

public class HokmGame {
    private List<Client> players;
    private List<Cards> deck;
    private int currentPlayerIndex;
    private String Suit;
    private List<Cards> playedCards;
    private Map<Client, List<Cards>> playerHands; // what Cards each player has is saved in this List
    private Client dealer;

    public HokmGame(List<Client> players) {
        this.players = players;
        this.playerHands = new HashMap<>();
        this.deck = Cards.getAllCards(); // we pass the whole list of cards here
        // Initialize player hands
        for (Client player : players) {
            playerHands.put(player, new ArrayList<>());
        }
    }

    private void initializeGame() {
        // Distribute cards to players, set the dealer, etc.
        // For simplicity, let's assume cards are already shuffled and distributed
    }

//    public void playCard(Client player, Cards card) {
//        if (!playerHands.get(player).contains(card)) {
//            throw new IllegalArgumentException("Player does not have the card");
//        }
//
//        playedCards.add(card);
//        playerHands.get(player).remove(card);
//
//        // Check if the round is over
//        if (playedCards.size() == 4) {
//            // Determine the winner of the round
//            Client roundWinner = determineRoundWinner();
//            currentPlayerIndex = players.indexOf(roundWinner);
//            playedCards.clear();
//            // Notify all players about the round result
//            notifyPlayersRoundResult(roundWinner);
//        } else {
//            // Move to the next player
//            //the index does not reset, so we get mod of 4
//            currentPlayerIndex = (currentPlayerIndex + 1) % 4;
//        }
//
//        // Notify all players about the game state update
//        notifyPlayersGameState();
//    }

    private Client determineRoundWinner() {
        // Implement logic to determine the round winner based on the played cards and trump suit
        return players.get(0); // Placeholder, replace with actual logic
    }

//    private void notifyPlayersGameState() {
//        for (Client player : players) {
//            player.sendGameState(this);
//        }
//    }

    public void notifyPlayersGameStart() {
        for (Client player : players) {
//            player.sendCards(playerHands.get(player));
        }
    }
    public void shuffleAndDistributeCards() {
        Collections.shuffle(deck);
        int playerIndex = 0;
        for (Cards card : deck) {
            playerHands.get(players.get(playerIndex)).add(card);
            playerIndex = (playerIndex + 1) % 4;
        }
    }

    public List<Cards> getPlayedCards() {
        return playedCards;
    }

    public Map<Client, List<Cards>> getPlayerHands() {
        return playerHands;
    }
    public List<Cards> getCardsForPlayer(Client player) {
        if (playerHands.containsKey(player)) {
            return playerHands.get(player);
        } else {
            return new ArrayList<>();
        }
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public String getTrumpSuit() {
        return Suit;
    }
}

