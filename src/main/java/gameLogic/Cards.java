package gameLogic;

public class Cards {
    private String suit;
    private String number;

    private int value;

    private String name;

    private boolean isHokm = false;

    public String getNumber() {
        return number;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    public Cards(String suit, String number, int value, String name) {
        this.suit = suit;
        this.number = number;
        this.value = value;
        this.name = name;
    }
}
