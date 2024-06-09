import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    public String ClientName;
    private int id;
    private static int idCounter = 1;

    public Client(String address, int port , String ClientName) throws IOException {
        this.id = idCounter++;
        this.ClientName = ClientName;
        socket = new Socket(address, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void sendRequest(Object request) throws IOException {
        output.writeObject(request);
    }

    public Object receiveResponse() throws IOException, ClassNotFoundException {
        return input.readObject();
    }

    public void close() throws IOException {
        socket.close();
    }
}

