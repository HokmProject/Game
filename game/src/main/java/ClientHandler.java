import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

class ClientHandler extends Thread implements Serializable {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String username;
    private Game game;
    private boolean isHakem;
    private MainFrame mainframe;
    private ArrayList<Cards> playercards;
    private String flag = null;
    private ArrayList<ClientHandler> reorderedPlayers;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        isHakem = false;
        this.playercards = new ArrayList<>();
    }

    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                Object command = ois.readObject();
                if (command instanceof String && ((String) command).startsWith("CREATE_GAME")) {
                    handleCreateGame((String) command);
                } else if (command instanceof String && ((String) command).startsWith("JOIN_GAME")) {
                    handleJoinGame((String) command);
                } else if (command instanceof String && ((String) command).startsWith("JOIN_RANDOM_GAME")) {
                    handleJoinRandomGame((String) command);
                } else if (command instanceof String && ((String) command).startsWith("START_GAME")) {
                    handleStartGame((String) command);
                } else if (command instanceof MainFrame) {
                    mainframe = (MainFrame) command;
                } else if (command instanceof ArrayList ) {
                    playercards = (ArrayList) command;
                } else if (command instanceof Cards) {
                    handlePlayCard((Cards) command);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (username != null) {
                Server.activeUsers.remove(username);
            }
            if (game != null) {
                game.removePlayer(username);
            }
        }
    }

    @Deprecated
    private void handleAddClient(ClientHandler clienthandler) throws IOException {
        if (Server.clientHandlers.contains(clienthandler)) {
            oos.writeObject("[ERROR] : Client Already exists !!");
            return;
        }
        Server.clientHandlers.add(clienthandler);
    }

    private void handleCreateGame(String command) throws IOException, SQLException {
        // Example: CREATE_GAME username
        String[] tokens = command.split(" ");
        username = tokens[1];
        if (Server.activeUsers.contains(username)) { // if there is already a username
            // send a request(message) to
            oos.writeObject("[ERROR] : Username already exists.");
            return;
        }
        Server.activeUsers.add(username);
        String gameToken = TokenGenerator(10).toString(); // a token is generated for Game
        game = new Game(gameToken, username, this);
        Server.activeGames.put(gameToken, game); // a Game object is added to the Server's List of Games
        Server.waitingGames.add(game); // will be waiting for other players to join
        oos.writeObject("GAME_CREATED " + gameToken);// sends a request to Client's Input
        Server.clientHandlers.add(this);

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hokmgame" ,"root" , "Sadraahmadi83@");
        String sql = "INSERT INTO Players (UserName, GameToken) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, gameToken);
        statement.executeUpdate();
    }

    private void handleJoinGame(String command) throws IOException, SQLException {
        // Example: JOIN_GAME username token
        String[] tokens = command.split(" ");
        username = tokens[1];
        String token = tokens[2];
        if (Server.activeUsers.contains(username)) {
            oos.writeObject("[ERROR] : Username already exists.");
            return;
        }
        game = Server.activeGames.get(token);
        if (game == null || game.isFull()) {
            oos.writeObject("[ERROR] : Game not available.");
            return;
        }
        Server.activeUsers.add(username);
        game.addPlayer(username, this);
        oos.writeObject("JOINED_GAME " + token);
        Server.clientHandlers.add(this);
        if (game.isFull()) {
            game.notifyPlayers("[SERVER] : All players are joined. You can start the game now !");
            Server.waitingGames.remove(game);
        }

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hokmgame" ,"root" , "Sadraahmadi83@");
        String sql = "INSERT INTO Players (UserName, GameToken) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, token);
        statement.executeUpdate();


    }

    private void handleJoinRandomGame(String command) throws IOException, SQLException {
        // Example: JOIN_RANDOM_GAME username
        String[] tokens = command.split(" ");
        username = tokens[1];
        if (Server.activeUsers.contains(username)) {
            oos.writeObject("[ERROR] : Username already exists.");
            return;
        }
        if (Server.waitingGames.isEmpty()) {
            oos.writeObject("[ERROR] : No available games.");
            return;
        }
        game = Server.waitingGames.get(0); //gets the first game from the waiting games list
        Server.activeUsers.add(username);
        game.addPlayer(username, this);
        oos.writeObject("JOINED_GAME " + game.getToken());
        Server.clientHandlers.add(this); // adds the ClientHandler to the Server's list of ClientHandlers

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hokmgame" ,"root" , "Sadraahmadi83@");
        String sql = "INSERT INTO Players (UserName, GameToken) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, game.getToken());
        statement.executeUpdate();

        if (game.isFull()) {
            game.notifyPlayers("[SERVER] : All players are joined."); // sends a message to all players of the game
            Server.waitingGames.remove(game); // it gets removed from the list of waiting games because it's full
        }
    }

    private void handleStartGame(String command) throws IOException, SQLException {
        // Example: START_GAME token
        String[] tokens = command.split(" ");
        String token = tokens[1];
        game = Server.activeGames.get(token); // gets the game from server
        if (game != null && game.isCreator(username) && game.isFull()) { // if the game exists and the username of the one that sent the request is the same as Creator and the game is Filled
            game.startGame(this); // starts the game
            game.notifyPlayers("[SERVER] : !!! Game Started by " + username + " !!!");
//            sendObject("DISABLE_START_BUTTON");
        } else if (!game.isFull()) {

            oos.writeObject("[ERROR] : Game is not full. Cannot Start the Game . Waiting for Players ...");// sends a message to Client
        } else {

            oos.writeObject("[ERROR] : You are not the game creator or game not found.");
        }
    }

    private void handlePlayCard(Cards card) throws IOException, SQLException {
        game.reorderbyHakem(game.getPlayers());
        if (game.getTurn() == 0 && game.getHakem() == username) {
            game.setCurrentPlayerIndex(0);
            game.playTurn(card);
        } else if (game.getTurn() % 4 == 1 && game.getPlayers().get(1).getUsername() == username) {
            game.setCurrentPlayerIndex(1);
            game.playTurn(card);
        } else if (game.getTurn() % 4 == 2 && game.getPlayers().get(2).getUsername() == username) {
            game.setCurrentPlayerIndex(2);
            game.playTurn(card);
        } else if (game.getTurn() % 4 == 3 && game.getPlayers().get(3).getUsername() == username) {
            game.setCurrentPlayerIndex(3);
            game.playTurn(card);
        } else if (game.getTurn() % 4 == 0 && game.getPlayers().get(0).getUsername() == username) {
            game.setCurrentPlayerIndex(0);
            game.playTurn(card);
        }
//        else if(game.getTurn() == 1)
        else {
            oos.writeObject("[ERROR] : Wait for Your Turn ...");
            oos.writeObject("ENABLE_CARD_BUTTON " + getUsername());
        }
    }

    static String TokenGenerator(int n) {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int) (AlphaNumericString.length() * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public boolean getIsHakem() {
        return this.isHakem;
    }

    public void setHakem(boolean bool) {
        this.isHakem = bool;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.playercards = cards;
    }

    public ArrayList<Cards> getCards() {
        return this.playercards;
    }

    public void setMainFrame(MainFrame mainframe) {
        this.mainframe = mainframe;
    }

    public MainFrame getMainFrame() {
        return this.mainframe;
    }

    public void sendObject(Object object) throws IOException {
        oos.writeObject(object);// sends an object to MainFrame
    }

    void sendCards(ArrayList<Cards> cards) throws IOException {
        oos.writeObject(cards);
    }

    Game getGame() throws IOException {
        return this.game;
    }

    public String getUsername() {
        return this.username;
    }

    public Team getTeam(ClientHandler player) {
        if (game.Team1.player1 == player || game.Team1.player2 == player) {
            return game.Team1;
        }
        if (game.Team2.player1 == player || game.Team2.player2 == player) {
            return game.Team2;
        }
        return null;
    }

    public boolean hasHokm(String Hokm) {
        for (Cards card : playercards) {
            if (card.getSuit().equals(Hokm)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSuitAvailable(String suit) {
        for (Cards card : playercards) {
            if (card != null && card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }
    public void removeCard(Cards card) {
        for (Cards CARD : playercards) {
            if (CARD == card) {
               playercards.set(playercards.indexOf(card), null);
            }
        }
    }
}