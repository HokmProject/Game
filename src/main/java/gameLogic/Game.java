package gameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game{
    static ArrayList<Cards> cards = new ArrayList<>();
    User[] players;
    Cards[] playerCards;

    public Game(User[] players, Cards[] c1, Cards[] c2, Cards[] c3, Cards[] c4) {
        this.players = players;
        startGame(players);
        cards.addAll(Arrays.asList(c1));
        cards.addAll(Arrays.asList(c2));
        cards.addAll(Arrays.asList(c3));
        cards.addAll(Arrays.asList(c4));

        for(User x: players) {
            x.setCards(dealTheCards());
        }
    }

    public void startGame(User[] users) {
        Random rand = new Random();
        int randNumber = rand.nextInt(0,4);
        users[randNumber].setHakem(true);
    }

    public Cards[] dealTheCards(){
        playerCards = new Cards[13];
        Random rand = new Random();
        for(int i = 0; i < 12; i++){
            int randNum = rand.nextInt(0, cards.size());
            playerCards[i] = cards.get(randNum);
            cards.remove(randNum);
        }
        return playerCards;
    }
}
