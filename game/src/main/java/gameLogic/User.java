package gameLogic;
import java.util.*;

public class User {
    private String name;
    private boolean isHakem;
    private String partner;
    private ArrayList<Cards> cards;

    public boolean getIsHakem() {
        return this.isHakem;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Cards> getCards() {
        return this.cards;
    }

    public void setHakem(boolean bool) {
        this.isHakem = bool;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

    public User(String name) {
        this.name = name;
        this.isHakem = false;
    }
}
