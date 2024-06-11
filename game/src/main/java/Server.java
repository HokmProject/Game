
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server{
    private static ArrayList<Game> games;
    private ServerSocket serverSocket;

    public Server(ServerSocket socket){
        this.serverSocket = socket;

    }

    public void startServer(){
        System.out.println("Server started...");
        try{
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new Client Connection is established");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createGame(){
        Game game = new Game();
        games.add(game);
    }
}





















//package com.example;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class Server {
//    private ArrayList<Game> games;
//    private ArrayList<ClientHandler> clients;
//    private ServerSocket serverSocket;
//    private ExecutorService pool;
//
//    public Server() throws IOException {
//        games = new ArrayList<>();
//        clients = new ArrayList<>();
//        serverSocket = new ServerSocket(12345);
//        pool = Executors.newFixedThreadPool(4); // Adjust the pool size as needed
//    }
//
//    public void start() {
//        System.out.println("Server started...");
//        while (true) {
//            try {
//                Socket clientSocket = serverSocket.accept();
//                ClientHandler clientHandler = new ClientHandler(this, clientSocket);
//                clients.add(clientHandler);
//                pool.execute(clientHandler);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public synchronized void createNewGame(Game game) {
//
//        games.add(game);
//    }
////
//    public synchronized ArrayList<Game> getGames() {
//        return games;
//    }
////
////    public synchronized void removeClient(ClientHandler clientHandler) {
////        clients.remove(clientHandler);
////    }
//
//    public static void main(String[] args) {
//        try {
//            Server server = new Server();
//            server.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
