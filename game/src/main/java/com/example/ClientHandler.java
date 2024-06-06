package com.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> ClientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String PlayerUserName;

    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            //OutputStreamWriter is a Character stream
            //Bufferedwriter is only here to increase efficiency

            //this is what we are doing to send
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //this is what we are doing to Read
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.PlayerUserName = bufferedReader.readLine();
            //add the clientHandleer to clienthanlder List
            ClientHandlers.add(this);
            broadcastMessage("Server: " + PlayerUserName + " has entered");
        } catch (IOException e) {
            closeEverything(socket , bufferedWriter , bufferedReader);
        }
    }
    @Override
    public void run() {
        String messageFromPlayer;
        while (socket.isConnected()) {
            try {
                messageFromPlayer = bufferedReader.readLine();
                broadcastMessage(PlayerUserName + ": " + messageFromPlayer);
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }
    //this is where the client interacts with the game and it updates the game state for other players
    public void broadcastMessage(String message) {
        for(ClientHandler clientHandler : ClientHandlers){
            try{
                //this or not this can be changed
                if(!clientHandler.PlayerUserName.equals(this.PlayerUserName)){
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                };
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter , bufferedReader);
            }

        }
    }
    public void removeClientHandler(){
        ClientHandlers.remove(this);
        broadcastMessage("SERVER : " + PlayerUserName + " has left the game !");
    }
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedreader){
        removeClientHandler();
        try {
            if(socket != null){
                socket.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(bufferedReader != null){
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




























//package com.example;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class ClientHandler implements Runnable {
//    private Socket clientSocket;
//    private Server server;
//    private PrintWriter out;
//    private BufferedReader in;
//    private int clientId;
//    private static int idCounter = 1;
//
//    public ClientHandler(Socket socket, Server server) {
//        this.clientSocket = socket;
//        this.server = server;
//        this.clientId = idCounter++;
//    }
//
//    @Override
//    public void run() {
//        try {
//            System.out.println("You are connected to the Server");
//            out = new PrintWriter(clientSocket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            String inputLine;
//
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println("Received from client " + clientId + ": " + inputLine);
//                processInput(inputLine);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//                out.close();
//                clientSocket.close();
////                server.removeClient(this);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void processInput(String input) {
//        // Example command: "CREATE_GAME"
//        // Example command: "JOIN_GAME <gameToken>"
//        if (input.startsWith("CREATE_GAME")) {
//            Game game = new Game();
//            server.createNewGame(game);
//            out.println("Game created with token: " + game.getToken());
//
//        } else if (input.startsWith("JOIN_GAME")) {
//            String[] parts = input.split(" ");
//            String gameToken = null;
//            if (parts.length == 2) {
//                gameToken = parts[1];
////                Game game = findGameByToken(gameToken);
////                if (game != null) {
////                    // Add player to the game
////                    Player player = new Player("Player" + clientId);
////                    game.addPlayer(player);
//                out.println("You Are added to the game with id of : " + clientId);
//                out.println("Joined game with token: " + gameToken);
//            } else {
//                out.println("Game not found with token: " + gameToken);
//            }
//        } else {
//                out.println("Invalid JOIN_GAME command");
//            }
//    }

//    private Game findGameByToken(String token) {
//        for (Game game : server.getGames()) {
//            if (game.getToken().equals(token)) {
//                return game;
//            }
//        }
//        return null;
//    }
//}

//____________________________________________________________________________________________________
//
//import com.example.Client;
//import com.example.Server;

//import java.io.*;
//import java.net.Socket;
//
//public class ClientHandler implements Runnable {
//    private Server server;
//    private Socket socket;
//    private Client client;
//    private ObjectInputStream input;
//    private ObjectOutputStream output;
//
//    public ClientHandler(Server server, Socket socket) throws IOException {
//        this.server = server;
//        this.socket = socket;
//        this.client = new Client("localhost", 12345);
//        this.input = new ObjectInputStream(socket.getInputStream());
//        this.output = new ObjectOutputStream(socket.getOutputStream());
//    }
//
//    public Client getClient() {
//        return client;
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                Object request = input.readObject();
//                handleRequest(request);
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void handleRequest(Object request) {
//        // Implement logic to handle different types of requests from the client
//        if (request instanceof String) {
//            String command = (String) request;
//            switch (command) {
//                case "createGame":
//                    // Logic to create a new game
//                    break;
//                case "joinGame":
//                    // Logic to join an existing game
//                    break;
//                // Add more cases as needed
//            }
//        }
//    }
//
//    public void sendResponse(Object response) throws IOException {
//        output.writeObject(response);
//    }
//}

