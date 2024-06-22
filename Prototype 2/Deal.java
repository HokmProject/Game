import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deal {
    static ArrayList<Cards> allCards = new ArrayList<>();
    public Client[] players;
    public Cards[] playerCards;

    public Deal(List<Client> players ) { //pass a ClientHandler from Game
        this.players = players.toArray(new Client[0]);
        startGame(players);

        allCards.addAll(List.of(Cards.pick));
        allCards.addAll(List.of(Cards.del));
        allCards.addAll(List.of(Cards.geshniz));
        allCards.addAll(List.of(Cards.khesht));
        //Shuffle the cards later
        //hakem should get the cards First*
        for (Client player : players) {
            //returns a random deck of hands that is sorted for each player
            player.setCards(sortCards(dealCards(player)));
            updateGUI(player.getCards().toArray(new Cards[0]), player);
        }
    }

    // setting a random hakem from the players
    public void startGame(List<Client> users) { // The logic will be changed later (Cards showing up)
        Random rand = new Random();
        int randNumber = rand.nextInt(0, 2);
        users.get(randNumber).setHakem(true);
    }

    //dealing the random cards to each player
    public Cards[] dealCards(Client player) {
        playerCards = new Cards[13];
        Random rand = new Random();
        for (int i = 0; i < 13; i++) {
            int randNum = rand.nextInt(0, allCards.size());
            playerCards[i] = allCards.get(randNum);
            allCards.remove(randNum);
        }
        //this happens after assigning cards to each player
        return playerCards;
    }

    public void updateGUI(Cards[] playerCards , Client client) {
        JButton buttons[] =  ClientHandler.getClient(client.getUsername()).getButtons(); // * Error : return a null mainframe
        for (int i = 0; i < playerCards.length ; i++) {
            String ImgPath = playerCards[i].getImgPath();
            ImageIcon image = new ImageIcon(ImgPath);
            buttons[i].setIcon(image);
        }
    }

    //sorting the cards by suit and value
    private ArrayList<Cards> sortCards(Cards[] c) {
        ArrayList<Cards> P = new ArrayList<>();
        ArrayList<Cards> KH = new ArrayList<>();
        ArrayList<Cards> D = new ArrayList<>();
        ArrayList<Cards> G = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            if (c[i].getSuit().equals("P")) {
                P.add(c[i]);
            } else if (c[i].getSuit().equals("KH")) {
                KH.add(c[i]);
            } else if (c[i].getSuit().equals("D")) {
                D.add(c[i]);
            } else if (c[i].getSuit().equals("G")) {
                G.add(c[i]);
            }
        }

        ArrayList<Cards> sort = new ArrayList<>();
        sort.addAll(sortByNum(P));
        sort.addAll(sortByNum(KH));
        sort.addAll(sortByNum(G));
        sort.addAll(sortByNum(D));
        // updateGUI here?
        return sort;
    }

    private ArrayList<Cards> sortByNum(ArrayList<Cards> c) {
        if (!c.isEmpty()) {
            for (int i = 0; i < c.size() - 1; i++) {
                for (int j = i + 1; j < c.size(); j++) {
                    if (c.get(i).getValue() < c.get(j).getValue()) {
                        Cards x = c.get(i);
                        c.set(i, c.get(j));
                        c.set(j, x);
                    }
                }
                System.out.println(c.get(i).getName());
            }
            System.out.println("im done*********");

        }
        return c;
    }

    public void chooseHokm(Cards[] c) {
        for (Client x : players) {
            if (x.getIsHakem()) {

            }
        }
    }
}
