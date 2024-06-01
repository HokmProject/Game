package gameLogic;

public class Cards {
    private String suit;
    private String number;

    public String getNumber() {
        return number;
    }

    public String getSuit() {
        return suit;
    }
    public Cards(String suit, String number) {
        this.suit = suit;
        this.number = number;
    }
}
