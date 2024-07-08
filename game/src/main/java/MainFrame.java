import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainFrame extends JFrame implements Serializable {
    private String username;
    private String token;
    private boolean isCreator;

    private ArrayList<Cards> PlayerCards;
    private JFrame MainFrameScore;
    private JFrame CardsInterfaceFrame;

    //_____________________________________________________________________________________________
    static String c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13;
    static String points, points2, rounds, rounds2;
    //    static ImageIcon c1Image, c2Image, c3Image, c4Image , c5Image, c6Image, c7Image, c8Image , c9Image, c10Image, c11Image, c12Image , c13Image;
    static ImageIcon ImgIcons;
    static JButton[] buttons;
    private JButton startButton;
    private static JButton LastClicked;
    private boolean isYourTurn;
    private boolean cardsDistributed;
    //___________________________________________________________________________________________

    public MainFrame(String username, String token, boolean isCreator, ObjectInputStream ois) {
        this.username = username;
        this.token = token;
        this.isCreator = isCreator;
        setTitle("Hokm Game - " + username);
        setLayout(new BorderLayout());


        JTextArea messageArea = new JTextArea();
        messageArea.setBounds(20 , 20 , 793 , 500);
        messageArea.setEditable(false);
        messageArea.setFont(messageArea.getFont().deriveFont(15f));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(20, 20, 793, 500);
        add(scrollPane);
//        add(new JScrollPane(messageArea), BorderLayout.CENTER);
//        add(messageArea);

//        MainFrameScore = Score.score(this);
//        CardsInterfaceFrame = CardsInterface.cards(this);
//__________________________________________________________________________________________________________


        points = points2 = rounds2 = rounds = "0";
        JLabel team1score= new JLabel("team 1: "+points+"                                               rounds: "+rounds);
        team1score.setBounds(10, 670, 400, 40);
        team1score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        add(team1score);

        JLabel team2score = new JLabel("team 2: "+points2+"                                               rounds: "+rounds2);
        team2score.setBounds(420 , 670, 400, 40);
        team2score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        add(team2score);




        c1 = c2 = c3 = c4 = c5= c6 = c7 = c8 = c9 = c10 = c11 = c12 = c13 = "";


        JButton b1 = new JButton(c1);
        JButton b2 = new JButton(c2);
        JButton b3 = new JButton(c3);
        JButton b4 = new JButton(c4);
        JButton b5 = new JButton(c5);
        JButton b6 = new JButton(c6);
        JButton b7 = new JButton(c7);
        JButton b8 = new JButton(c8);
        JButton b9 = new JButton(c9);
        JButton b10 = new JButton(c10);
        JButton b11 = new JButton(c11);
        JButton b12 = new JButton(c12);
        JButton b13 = new JButton(c13);


        b1.setBounds(10, 520, 84, 125);
        b2.setBounds(71, 520, 84, 125);
        b3.setBounds(132, 520, 84, 125);
        b4.setBounds(193, 520, 84, 125);
        b5.setBounds(254, 520, 84, 125);
        b6.setBounds(315, 520, 84, 125);
        b7.setBounds(376, 520, 84, 125);
        b8.setBounds(437, 520, 84, 125);
        b9.setBounds(498, 520, 84, 125);
        b10.setBounds(559, 520, 84, 125);
        b11.setBounds(620, 520, 84, 125);
        b12.setBounds(681, 520, 84, 125);
        b13.setBounds(742, 520, 84, 125);

//        ImageIcon[] ImgIcons = {c1Image, c2Image, c3Image, c4Image , c5Image, c6Image, c7Image, c8Image , c9Image, c10Image, c11Image, c12Image , c13Image};
        JButton[] buttons = {b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13};


//        b1.setIcon(c1Image); b2.setIcon(c2Image); b3.setIcon(c3Image);
//        b4.setIcon(c4Image);b5.setIcon(c5Image);b6.setIcon(c6Image);
//        b7.setIcon(c7Image);b8.setIcon(c8Image);b9.setIcon(c9Image);
//        b10.setIcon(c10Image);b11.setIcon(c11Image);b12.setIcon(c12Image);
//        b13.setIcon(c13Image);


        add(b1);add(b2);add(b3);
        add(b4);add(b5);add(b6);
        add(b7);add(b8);add(b9);
        add(b10);add(b11);add(b12);
        add(b13);





//__________________________________________________________________________________________________________

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(845, 825);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel tokenLabel = new JLabel("Game Token: " + token);
        tokenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tokenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(tokenLabel, BorderLayout.NORTH);


        if (isCreator) { // if it's Creator , then the start button will be shown for him
            startButton = new JButton("Start Game");
            startButton.addActionListener(e -> {
                try {
                    Client.sendMessage("START_GAME " + token);
//                    startButton.setEnabled(false);// sends a request to the ClientHandler
                } catch (IOException ex) {

                    throw new RuntimeException(ex);
                }
            });
            add(startButton, BorderLayout.SOUTH);



        }

        setVisible(true);

        // Listen for server messages
        new Thread(() -> {
            try {
                Object object;
                while ((true)){
                    object = ois.readObject();
                    if(object instanceof String) {
                        String response = (String) object;
                        messageArea.append(response + "\n");
                        if (response.equals("Game Started")) {
                            // Handle game start logic here

                        } else if (response.equals("[SERVER] : All players are joined.")) {
                            // Handle all players joined logic here
                            if (isCreator) {
                                messageArea.append("[CREATOR] : You can Start Now !!! \n");
                            }
                        }else if (response.startsWith("DISABLE_START_BUTTON")){
                            if(isCreator) {
                                startButton.setEnabled(false);
                            }
                        }else if (response.equals("ENABLE_CARD_BUTTON " + username)){
                            LastClicked.setEnabled(true);
//                            break;
                        }
//                        }else if (response.startsWith("SENDING_CARDS_PATH ")){
//                            String[] command = response.split(" ");
//                            String Path = command[1];
//                            String[] cardPath = Path.split("_");
//                            for(int i = 0 ; i < cardPath.length ; i++){
//                                ImageIcon imgpath = new ImageIcon(cardPath[i]);
//                                buttons[i].setIcon(imgpath);
//                            }
                            // Handle sending cards logic here
                    // this is where the messages are shown
                        }else if(object instanceof ArrayList){
                            PlayerCards = (ArrayList<Cards>) object;
                            for (int i = 0; i < PlayerCards.size() ; i++) {
                            String ImgPath = PlayerCards.get(i).getImgPath();
                            ImageIcon image = new ImageIcon(ImgPath);
                            buttons[i].setIcon(image);



//                                buttonActionListener(b1 , PlayerCards.get(0));
//                                buttonActionListener(b2 , PlayerCards.get(1));
//                                buttonActionListener(b3 , PlayerCards.get(2));
//                                buttonActionListener(b4 , PlayerCards.get(3));
//                                buttonActionListener(b5 , PlayerCards.get(4));
//                                buttonActionListener(b6 , PlayerCards.get(5));
//                                buttonActionListener(b7 , PlayerCards.get(6));
//                                buttonActionListener(b8 , PlayerCards.get(7));
//                                buttonActionListener(b9 , PlayerCards.get(8));
//                                buttonActionListener(b10 , PlayerCards.get(9));
//                                buttonActionListener(b11 , PlayerCards.get(10));
//                                buttonActionListener(b12 , PlayerCards.get(11));
//                                buttonActionListener(b13 , PlayerCards.get(12));
                            }
                        for (int j = 0; j < buttons.length; j++) {
                            final int index = j;
                            buttons[j].addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    buttons[index].setEnabled(false);
                                    LastClicked = buttons[index];
                                    try {
                                        Client.sendMessage(PlayerCards.get(index));
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                            });
                        }
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public JButton[] getButtons(){
        return buttons;
    }
    public static void buttonActionListener(JButton b, Cards card) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b.setEnabled(false);
                LastClicked = b;
                try {
                    Client.sendMessage(card);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
