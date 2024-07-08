import java.io.IOException;
import java.io.Serializable;
import java.util.*;

class Game implements Serializable {
    private String token;
    private String creator;
    private String Hokm;
    private List<String> players = new ArrayList<>();
    List<Client> clients = new ArrayList<>();
    private boolean started = false;
    private List<ClientHandler> playerHandlers = new ArrayList<>();
    Team Team1;
    Team Team2;
    private int Turn = 0;
    private List<Cards> cardsOnTable = new ArrayList<>();
    private static int currentPlayerIndex;
    private String currentSuit;
    private int Round;
    public Game(String token, String creator, ClientHandler creatorHandler) {
        this.token = token;
        this.creator = creator;
        players.add(creator); // the username will be added to the list of players
        clients.add(new Client(creator)); // Creates a new Client and is Saved in a List
        playerHandlers.add(creatorHandler); // puts the ClientHandler into the list
    }

    public void addPlayer(String username, ClientHandler handler) {
        if (!isFull() && !started) {
            players.add(username);
            playerHandlers.add(handler);
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

            Team1 = new Team(playerHandlers.get(0) , playerHandlers.get(2));
            Team2 = new Team(playerHandlers.get(1) , playerHandlers.get(3));

            getCreatorHandler().sendObject("DISABLE_START_BUTTON");
        }
    }

    private ClientHandler getCreatorHandler() {
        for(ClientHandler handler : playerHandlers){
            if(handler.getUsername().equals(creator)){
                return handler;
            }
        }
        return null;
    }

    public void notifyPlayers(String message) throws IOException {
        for (ClientHandler clientHandler : playerHandlers) {
            clientHandler.sendObject(message);
        }
    }

    public String getToken() {
        return token;
    }
    public void setHokm(String Hokm) {
        this.Hokm = Hokm;
    }
    public String getHakem() {
        for(ClientHandler handler : playerHandlers) {
            if(handler.getIsHakem()) {
                return handler.getUsername();
            }
        };
        return null;
    }
    public int getTurn() {
        return this.Turn;
    }

    public void incrementTurn() {
        this.Turn++;
    }
//    public int getPlayerPosition(String username){
//        int HakemPosition , playerPosition;
//        for(int i=0; i<playerHandlers.size() ; i++){
//            if(playerHandlers.get(i).getIsHakem()){
//                HakemPosition = i + 1;
//            }
//        }
//        for(int i=0; i<playerHandlers.size(); i++){
//            if(playerHandlers.get(i).getUsername().equals(username)){
////                playerPosition = ;
//            }
//        }
//        return HakemPosition;
//    }

    public ArrayList<ClientHandler> getPlayers() {
        return (ArrayList<ClientHandler>) playerHandlers;
    }

    public ArrayList<ClientHandler> reorderbyHakem(ArrayList<ClientHandler> playerHandlers) throws IOException {
        ArrayList<ClientHandler> reordered = new ArrayList<>();
        // First player is Hakem
        for(ClientHandler handler : playerHandlers){
            if(handler.getUsername().equals(getHakem())){
                reordered.add(handler);
                break;
            }
        }
        //Second player is not TeamMate and it's from the other team
        for(ClientHandler handler : playerHandlers){
            if( (!handler.getUsername().equals(getHakem()))){
                if(handler.getTeam(handler) != this.getHakemhandler().getTeam(getHakemhandler())){
                    reordered.add(handler);
                    break;
                }
            }
        }
        //Third player is Hakem's TeamMate
        for(ClientHandler handler : playerHandlers){
            if(!handler.getUsername().equals(getHakem())){
                if(handler.getTeam(handler) == this.getHakemhandler().getTeam(getHakemhandler())){
                    reordered.add(handler);
                    break;
                }
            }
        }
        //Fourth player is Second's player TeamMate
        for(ClientHandler handler : playerHandlers){
            if(!handler.getUsername().equals(getHakem())){
                if(reordered.get(1).getTeam(reordered.get(1)) == handler.getTeam(handler)){
                    if(reordered.get(1) != handler){
                        reordered.add(handler);
                        break;
                    }
                }
            }
        }
        return reordered;

        //                              1.Hakem (Team1)
        //2.SecondPlayer (Team2)                                 4.Fourth Player (Team2)
        //                              3.ThirdPlayer (Team1)
    }
    public ClientHandler getHakemhandler(){
        for(ClientHandler handler : playerHandlers){
            if(handler.getIsHakem()){
                return handler;
            }
        }
        return null;
    }

    public void playTurn(Cards card , ArrayList<ClientHandler> reorderPlayers) throws IOException{
        ClientHandler currentPlayer = reorderPlayers.get(currentPlayerIndex);
        if(getTurn() % 4 == 0){
            //if it's the first round of the set , it sets the suit to the first card's current suit
            cardsOnTable.add(card);
            currentSuit = card.getSuit();
            currentPlayer.sendObject("REMOVE_LAST_CARD");
            incrementTurn();
            notifyPlayers("[GAME] : " + currentPlayer.getUsername() + " played : " + card.getName());
            if(currentPlayerIndex == 3){
                reorderPlayers.get(0).sendObject("[SERVER] : It's your turn. pick a Card to play.");
            }else{
                reorderPlayers.get(currentPlayerIndex + 1).sendObject("[SERVER] : It's your turn. pick a Card to play.");
            }
        }
        else{
            if(currentSuit.equals((card.getSuit()))){
                cardsOnTable.add(card);
                currentPlayer.sendObject("REMOVE_LAST_CARD");
                incrementTurn();
                notifyPlayers("[GAME] : " + currentPlayer.getUsername() + " played : " + card.getName());
                if(currentPlayerIndex == 3){
                    reorderPlayers.get(0).sendObject("[SERVER] : It's your turn. pick a Card to play.");
                }else{
                    reorderPlayers.get(currentPlayerIndex + 1).sendObject("[SERVER] : It's your turn. pick a Card to play.");
                }
            }
            //if the player plays a card that is not equal to the current suit
            if (!currentSuit.equals(card.getSuit())) {
                //Checks whether the player has any cards from the current suit or not
                if(currentPlayer.isSuitAvailable(currentSuit) == true){
                    currentPlayer.sendObject("[ERROR] : Play the Card with the Current Suit of this Set !!!");
                    currentPlayer.sendObject("ENABLE_CARD_BUTTON " + currentPlayer.getUsername());
                }
                if(currentPlayer.isSuitAvailable(currentSuit) == false){
                    if(card.getSuit().equals(Hokm)){
                        cardsOnTable.add(card);
                        notifyPlayers("[GAME] : "+currentPlayer.getUsername()+" has cut with " + card.getName());
                        currentPlayer.sendObject("REMOVE_LAST_CARD");
                        incrementTurn();
                        notifyPlayers("[GAME] : " + currentPlayer.getUsername() + " played : " + card.getName());
                        if(currentPlayerIndex == 3){
                            reorderPlayers.get(0).sendObject("[SERVER] : It's your turn. pick a Card to play.");
                        }else{
                            reorderPlayers.get(currentPlayerIndex + 1).sendObject("[SERVER] : It's your turn. pick a Card to play.");
                        }
                    }
                    else{
                        //adds a card that is not Hokm and current suit (Worthless)
                        cardsOnTable.add(card);
                        currentPlayer.sendObject("REMOVE_LAST_CARD");
                        incrementTurn();
                        notifyPlayers("[GAME] : " + currentPlayer.getUsername() + " played : " + card.getName());
                        if(currentPlayerIndex == 3){
                            reorderPlayers.get(0).sendObject("[SERVER] : It's your turn. pick a Card to play.");
                        }else{
                            reorderPlayers.get(currentPlayerIndex + 1).sendObject("[SERVER] : It's your turn. pick a Card to play.");
                        }
                    }
                }
            }
        }

//        currentPlayerIndex = (currentPlayerIndex + 1) % reorderPlayers.size();

        if (cardsOnTable.size() == reorderPlayers.size()) {
            if(currentPlayerIndex == 3){
                reorderPlayers.get(0).sendObject("[SERVER] : It's your turn. pick a Card to play.");
            }else{
                reorderPlayers.get(currentPlayerIndex + 1).sendObject("[SERVER] : It's your turn. pick a Card to play.");
            }
            evaluateHand(reorderPlayers);
            cardsOnTable.clear();
        }
    }


    public void evaluateHand(ArrayList<ClientHandler> reorderPlayers) throws IOException {
        Cards winningCard = cardsOnTable.get(0);
        ClientHandler winningPlayer = reorderPlayers.get(0);

        for (int i = 1; i < cardsOnTable.size(); i++) {
            Cards card = cardsOnTable.get(i);
            if (card.getSuit().equals(winningCard.getSuit()) && card.getValue() > winningCard.getValue()) {
                winningCard = card;
                winningPlayer = reorderPlayers.get(i);
            } else if (card.getSuit().equals(Hokm) && !winningCard.getSuit().equals(Hokm)) {
                winningCard = card;
                winningPlayer = reorderPlayers.get(i);
            }
        }

        // The winning player starts the next hand
        currentPlayerIndex = reorderPlayers.indexOf(winningPlayer);
        if(Round == 7){
            //Handle the end of the Game
        }
        upadateTeamScore(winningPlayer);
        // Handle scoring logic here
    }

    private void upadateTeamScore(ClientHandler winningPlayer) throws IOException {
        Team team = winningPlayer.getTeam(winningPlayer);
        Team losingTeam = null;
        for(ClientHandler player : playerHandlers){
            if(player.getTeam(player) != team){
                losingTeam = player.getTeam(player);
                break;
            }
        }
        team.updateScore(losingTeam);
    }

    public static void setCurrentPlayerIndex(int index) throws IOException {
        currentPlayerIndex = index;
    }
}

