

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String PlayerUserName;
    private int id;
    private static int idCounter = 1;
//    public Client(Socket socket , String PlayerUserName){
//        try {
//            this.id = idCounter++;
//            this.socket = socket;
//            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            this.PlayerUserName = PlayerUserName;
//        } catch (Exception e) {
//            closeEverything(socket , bufferedWriter, bufferedReader);
//        }
//    }

    public Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }
    public void sendMessage(){
        try{
            bufferedWriter.write(PlayerUserName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(PlayerUserName + " : " + messageToSend );
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket , bufferedWriter , bufferedReader);
        }
    }

    public void listenformessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (socket.isConnected()) {
                        String message = bufferedReader.readLine();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    closeEverything(socket, bufferedWriter, bufferedReader);
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if (socket!= null) {
                socket.close();
            }
            if (bufferedWriter!= null) {
                bufferedWriter.close();
            }
            if (bufferedReader!= null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            closeEverything(socket , bufferedWriter, bufferedReader);
        }
    }

    public void sendRequest(Object request) throws IOException {
        output.writeObject(request);
    }

    public Object receiveResponse() throws IOException, ClassNotFoundException {
        return input.readObject();
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Username: ");
        String PlayerUserName = scanner.nextLine();
        Socket socket = new Socket("localhost" , 12345);
        Client client = new Client(socket, PlayerUserName);
        //these two methods are separate threads, so they can run at the same time
        client.listenformessage();
        client.sendMessage();
    }

    public int getId() {
        return id;
    }
}














//import java.io.*;
//import java.net.Socket;
//
//public class Client {
//    private Socket socket;
//    private ObjectOutputStream output;
//    private ObjectInputStream input;
//
//    public Client(String address, int port) throws IOException {
//        socket = new Socket(address, port);
//        output = new ObjectOutputStream(socket.getOutputStream());
//        input = new ObjectInputStream(socket.getInputStream());
//    }
//
//
//    public void sendRequest(Object request) throws IOException {
//        output.writeObject(request);
//    }
//
//    public Object receiveResponse() throws IOException, ClassNotFoundException {
//        return input.readObject();
//    }
//
//    public void close() throws IOException {
//        socket.close();
//    }
//
//    public static void main(String[] args) {
//        try {
//            Client client = new Client("localhost", 12345);
//            // Example interaction: creating a game
//            client.sendRequest("CREATE_GAME");
//            Object response = client.receiveResponse();
//            System.out.println("Response: " + response);
//            client.close();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
//_________________________________________________________________________________________________________________
//import java.io.*;
//import java.net.Socket;
//
//public class Client {
//    private Socket socket;
//    private ObjectOutputStream output;
//    private ObjectInputStream input;
//
//    public Client(String address, int port) throws IOException {
//        socket = new Socket(address, port);
//        output = new ObjectOutputStream(socket.getOutputStream());
//        input = new ObjectInputStream(socket.getInputStream());
//    }
//
//    public void sendRequest(Object request) throws IOException {
//        output.writeObject(request);
//    }
//
//    public Object receiveResponse() throws IOException, ClassNotFoundException {
//        return input.readObject();
//    }
//
//    public void close() throws IOException {
//        socket.close();
//    }
//
//    public static void main(String[] args) {
//        try {
//            Client client = new Client("localhost", 12345);
//            // Example interaction: creating a game
//            client.sendRequest("createGame");
//            Object response = client.receiveResponse();
//            System.out.println("Response: " + response);
//            client.close();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}



