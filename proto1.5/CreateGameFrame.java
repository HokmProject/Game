import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class CreateGameFrame extends JFrame {
    private JTextField usernameField;
    private JButton createButton;

    public CreateGameFrame() {
        setTitle("Create Game");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameField = new JTextField();
        createButton = new JButton("Create");

        usernameLabel.setBounds(50, 30, 100, 30);
        usernameField.setBounds(150, 30, 100, 30);
        createButton.setBounds(100, 70, 100, 30);

        add(usernameLabel);
        add(usernameField);
        add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    try {
                        Socket socket = new Socket("localhost", 12345);
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                        out.println("CREATE " + username);
                        String response = in.readLine();
                        if (response.startsWith("USERNAME_TAKEN")) {
                            JOptionPane.showMessageDialog(null, "Username already taken. Try another one.");
                        } else if (response.startsWith("GAME_CREATED")) {
                            String token = response.substring(13);
                            new MainFrame(username, token, socket , true).setVisible(true);
                            dispose();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty.");
                }
            }
        });
    }
}
