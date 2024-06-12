import java.util.*;

public class HokmGame {
    private List<Client> players;
    private int currentPlayerIndex;
    private String Suit;
    private List<Card> playedCards;
    private Map<Client, List<Card>> playerHands; // what Cards each player has is saved in this List
    private Client dealer;

    public HokmGame(List<Client> players) {
        this.players = players;
        this.playedCards = new ArrayList<>();
        this.playerHands = new HashMap<>();
        this.dealer = players.get(0); // it can be a random number generated so that Hakem gets picked
        this.currentPlayerIndex = 0; // should be the same number as above
        // Initialize hands and game state here
        initializeGame();
    }

    private void initializeGame() {
        // Distribute cards to players, set the dealer, etc.
        // For simplicity, let's assume cards are already shuffled and distributed
    }

    public void playCard(Client player, Card card) {
        if (!playerHands.get(player).contains(card)) {
            throw new IllegalArgumentException("Player does not have the card");
        }

        playedCards.add(card);
        playerHands.get(player).remove(card);

        // Check if the round is over
        if (playedCards.size() == 4) {
            // Determine the winner of the round
            Client roundWinner = determineRoundWinner();
            currentPlayerIndex = players.indexOf(roundWinner);
            playedCards.clear();
            // Notify all players about the round result
            notifyPlayersRoundResult(roundWinner);
        } else {
            // Move to the next player
            //the index does not reset, so we get mod of 4
            currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        }

        // Notify all players about the game state update
        notifyPlayersGameState();
    }

    private Client determineRoundWinner() {
        // Implement logic to determine the round winner based on the played cards and trump suit
        return players.get(0); // Placeholder, replace with actual logic
    }

    private void notifyPlayersGameState() {
        for (Client player : players) {
            player.sendGameState(this);
        }
    }

    private void notifyPlayersRoundResult(Client roundWinner) {
        for (Client player : players) {
            player.sendRoundResult(roundWinner);
        }
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public Map<Client, List<Card>> getPlayerHands() {
        return playerHands;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public String getTrumpSuit() {
        return Suit;
    }
}

