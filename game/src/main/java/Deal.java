import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Deal implements Serializable {
    static ArrayList<Cards> allCards = new ArrayList<>();
    public List<ClientHandler> playerHandlers;
    private List<Client> clients;
    public Cards[] playerCards;

    public Deal(List<ClientHandler> playerHandlers) {
        this.playerHandlers = playerHandlers;
        //pass a ClientHandler from Game
        startGame(playerHandlers);

        allCards.addAll(List.of(Cards.pick));
        allCards.addAll(List.of(Cards.del));
        allCards.addAll(List.of(Cards.geshniz));
        allCards.addAll(List.of(Cards.khesht));
        //Shuffle the cards later
        //hakem should get the cards First*
        playerHandlers.forEach(playerHandler -> {
                    try {
                        playerHandler.setCards(sortCards(dealCards(playerHandler)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        playerHandler.sendCards(playerHandler.getCards());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                }
        );
    }

    public Deal(List<ClientHandler> playerHandlers, boolean started) {
        this.playerHandlers = playerHandlers;

        allCards.clear();
        allCards.addAll(List.of(Cards.pick));
        allCards.addAll(List.of(Cards.del));
        allCards.addAll(List.of(Cards.geshniz));
        allCards.addAll(List.of(Cards.khesht));

        playerHandlers.forEach(playerHandler -> {
                    try {
                        playerHandler.setCards(sortCards(dealCards(playerHandler)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        playerHandler.sendCards(playerHandler.getCards());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ;
                }
        );
    }

    // setting a random hakem from the players
    public void startGame(List<ClientHandler> playerHandlers) { // The logic will be changed later (Cards showing up)
        Random rand = new Random();
        int randNumber = rand.nextInt(0, 4);
//        playerHandlers.get(randNumber).setHakem(true);
        List<ClientHandler> valuesList = new ArrayList<>(playerHandlers);
        valuesList.get(randNumber).setHakem(true);
    }

    //dealing the random cards to each player
    public Cards[] dealCards(ClientHandler player) throws IOException {
        playerCards = new Cards[13];
        Random rand = new Random();
        for (int i = 0; i < 13; i++) {
            int randNum = rand.nextInt(0, allCards.size());
            playerCards[i] = allCards.get(randNum);
            allCards.remove(randNum);
            if (i == 5 && player.getIsHakem()) {
                chooseHokm(playerCards);
            }
        }
        //this happens after assigning cards to each player
        return playerCards;
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

    public void chooseHokm(Cards[] c) throws IOException {
        for (ClientHandler playerHandler : playerHandlers) {
            if (playerHandler.getIsHakem()) {
                new HokmFrame(c, playerHandler);
                playerHandler.sendObject("[SERVER] : It's your turn. pick a Card to play.");
            }
        }
    }
}

