import java.io.*;
import java.net.Socket;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private List<Game> games;
    private PrintWriter out;
    private BufferedReader in;
    private Client client;
    private List<Client> players;
    private List<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, List<Game> games , List<ClientHandler> clientHandlers) {
        this.socket = socket;
        this.games = games;
        this.clientHandlers = clientHandlers;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request;
            while ((request = in.readLine()) != null) {
                if (request.startsWith("CREATE ")) {
                    String username = request.substring(7);
                    synchronized (games) {
                        for (Game game : games) {
                            if (game.hasPlayer(username)) {
                                out.println("USERNAME_TAKEN");
                                return;
                            }
                        }
                        client = new Client(username , socket , true);
                        Game game = new Game(client);
//                        game.setCreator(client);
                        games.add(game);
                        clientHandlers.add(this);
                        out.println("GAME_CREATED " + game.getToken());
                    }
                }
                else if (request.startsWith("JOIN ")) {
                    String command = request.substring(5);
                    try {
                        synchronized (games) {
                            if (command.equals("RANDOM")) {
                                for (Game game : games) {
                                    if (!game.isFull()) {
                                        client = new Client(in.readLine() , socket , false);
                                        if (game.addPlayer(client)) {
                                            out.println("JOINED_GAME " + game.getToken());
                                            System.out.println(client.getUsername() + " has joined the game.");
                                            clientHandlers.add(this); //adding the client handler to the list for notify
                                            if (game.isFull()) {
                                                // Notify the creator to show the start button
                                                game.getCreator().sendMessage("SHOW_START_BUTTON");
                                            }
                                            return;
                                        }
                                    }
                                }
                                out.println("NO_AVAILABLE_GAMES");
                            } else if (command.startsWith("TOKEN ")) {
                                String token = command.substring(6);
                                for (Game game : games) {
                                    if (game.getToken().equals(token)) {
                                        if (game.addPlayer(client)) {
                                            clientHandlers.add(this);
                                            out.println("JOINED_GAME " + token);
                                            if (game.isFull()) {
                                                // Notify the creator to show the start button
                                                game.getCreator().sendMessage("SHOW_START_BUTTON");
                                            }
                                            return;
                                        } else {
                                            out.println("GAME_FULL");
                                            return;
                                        }
                                    }
                                }
                                out.println("GAME_NOT_FOUND");
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if (request.startsWith("START_GAME ")) {
                    String token = request.substring(11);
                    synchronized (games) {
                        for (Game game : games) {
                            if (game.getToken().equals(token)) {
                                if (game.isFull()) {
                                    for(ClientHandler clienthandler : clientHandlers){
                                        clienthandler.out.println("GAME_START");
                                        // Implement card distribution logic here
                                        sendMessage("GAME_START");

                                        clienthandler.sendMessage("GAME_START");
                                        client.sendMessage("GAME_START");

                                    }
                                    game.startGame();
                                } else {
                                    game.getCreator().sendMessage("NOT_ENOUGH_PLAYERS ");
                                    System.out.println("Sent NOT_ENOUGH_PLAYERS message");
                                }
                                return;
                            }
                        }
                    }
                } else if (request.startsWith("PLAY_CARD ")) {
                    handlePlayCard(request.substring(10));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void handlePlayCard(String cardData) {
        // Extract game token and card info from cardData
        String[] parts = cardData.split(" ", 2);
        String token = parts[0];
//        Cards card = new Cards(parts[1]);

        // Find the game and let the client play the card
        synchronized (games) {
            for (Game game : games) {
                if (game.getToken().equals(token)) {
//                    game.getHokmGame().playCard(client, card);
                    return;
                }
            }
        }
    }
    public void sendMessage(String message) {
        out.println(message);
    }
}

