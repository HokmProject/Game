
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class login {
    private Client client;
    private JFrame login;
    private JButton createGameButton;
    public login() {
        try{
            client = new Client("localhost", 12345);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        login = new JFrame("login");
        login.setSize(823, 750);
        login.setLayout(null);
        login.setVisible(true);
        login.setResizable(false);
        login.setLocationRelativeTo(null);
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton createGameButton = new JButton("Create Game");
        createGameButton.setBounds(10, 10, 250, 100);
        createGameButton.setFont(new Font("Arial", Font.ITALIC, 20));
        login.add(createGameButton);

        JButton joinButton = new JButton("Join Game");
        joinButton.setBounds(10, 120, 250, 100);
        joinButton.setFont(new Font("Arial", Font.ITALIC , 20));
        login.add(joinButton);


        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame m1 = new MainFrame();
                login.setVisible(false);
                MainFrame.mainFrame.setVisible(true);
                try {
                    client.sendRequest("createGame");
                    Object response = client.receiveResponse();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        login l1 = new login();
    }
}
