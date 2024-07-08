import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HokmFrame extends JFrame {
    private JLabel[] imageLabels;
    private JButton pickButton;
    private JButton kheshtButton;
    private JButton geshnizButton;
    private JButton delButton;

    public HokmFrame(Cards[] cards , ClientHandler clientHandler) {
        setTitle("Choose Hokm : "+ clientHandler.getUsername());
        setSize(600, 400); // Adjust size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panel for labels
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imageLabels = new JLabel[5];
        for (int i = 0; i < imageLabels.length; i++) {
            imageLabels[i] = new JLabel();
            imageLabels[i].setPreferredSize(new Dimension(84, 125));
            imagePanel.add(imageLabels[i]);
        }

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        pickButton = new JButton("Pick");
        kheshtButton = new JButton("Khesht");
        geshnizButton = new JButton("Geshniz");
        delButton = new JButton("Del");

        buttonPanel.add(pickButton);
        buttonPanel.add(kheshtButton);
        buttonPanel.add(geshnizButton);
        buttonPanel.add(delButton);

        // Add panels to frame
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientHandler.getGame().setHokm("P");
                    clientHandler.getGame().notifyPlayers("[HAKEM] : Hokm is Chosen by Hakem #"+ clientHandler.getUsername() +  "#  --> PICK");
                    JOptionPane.showMessageDialog(null , "Hokm is #PICK#");
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        kheshtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientHandler.getGame().setHokm("KH");
                    clientHandler.getGame().notifyPlayers("[HAKEM] : Hokm is Chosen by Hakem #"+ clientHandler.getUsername() +  "#  --> KHESHT");
                    JOptionPane.showMessageDialog(null , "Hokm is #KHESHT#");
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        geshnizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientHandler.getGame().setHokm("G");
                    clientHandler.getGame().notifyPlayers("[HAKEM] : Hokm is Chosen by Hakem #"+ clientHandler.getUsername() +  "#  --> GESHNIZ");
                    JOptionPane.showMessageDialog(null , "Hokm is #GESHNIZ#");
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientHandler.getGame().setHokm("D");
                    clientHandler.getGame().notifyPlayers("[HAKEM] : Hokm is Chosen by Hakem #"+ clientHandler.getUsername() +  "#  --> DEL");
                    JOptionPane.showMessageDialog(null , "Hokm is #DEL#");
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        for (int i = 0; i < imageLabels.length; i++) {
            ImageIcon imageIcon = new ImageIcon(cards[i].getImgPath());
            imageLabels[i].setIcon(imageIcon);
        }

        setVisible(true);
    }

    // Method to update the images in the labels
}
