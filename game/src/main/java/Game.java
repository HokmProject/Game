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
    ClientHandler[] Team1;
    ClientHandler[] Team2;
    private int Turn = 0;

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

            Team1 = new ClientHandler[2];

            Team1[0] = playerHandlers.get(0);
            Team1[1] = playerHandlers.get(2);

            Team1[0].sendObject("[SERVER] : Your TeamMate is {"+ Team1[1].getUsername()+"}");
            Team1[1].sendObject("[SERVER] : Your TeamMate is {"+ Team1[0].getUsername()+"}");
            Team2 = new ClientHandler[2];

            Team2[0] = playerHandlers.get(1);
            Team2[1] = playerHandlers.get(3);

            Team2[0].sendObject("[SERVER] : Your TeamMate is {"+ Team2[1].getUsername()+"}");
            Team2[1].sendObject("[SERVER] : Your TeamMate is {"+ Team2[0].getUsername()+"}");

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
}

