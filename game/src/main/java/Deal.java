import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deal {
    static ArrayList<Cards> allCards = new ArrayList<>();
    public Client[] players;
    public Cards[] playerCards;

    public Deal(Client[] players, Cards[] c1, Cards[] c2, Cards[] c3, Cards[] c4) {
        this.players = players;
        startGame(players);

        allCards.addAll(Arrays.asList(c1));
        allCards.addAll(Arrays.asList(c2));
        allCards.addAll(Arrays.asList(c3));
        allCards.addAll(Arrays.asList(c4));

        for (Client player : players) {
            //returns a random deck of hands that is sorted for each player
            player.setCards(sortCards(dealCards()));
        }
    }

    // setting a random hakem from the players
    public void startGame(Client[] users) {
        Random rand = new Random();
        int randNumber = rand.nextInt(0, 4);
        users[randNumber].setHakem(true);
    }

    //dealing the random cards to each player
    private Cards[] dealCards() {
        playerCards = new Cards[13];
        Random rand = new Random();
        for (int i = 0; i < 13; i++) {
            int randNum = rand.nextInt(0, allCards.size());
            playerCards[i] = allCards.get(randNum);
            allCards.remove(randNum);
        }
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
