package gameLogic;

public class Main {

    private String ID;
    public static void main(String[] args) {
        Cards[] pick = {new Cards("P", "A"), new Cards("P", "2"), new Cards("P", "3"),
                new Cards("P", "4"), new Cards("P", "5"), new Cards("P", "6"),
                new Cards("P", "7"),new Cards("P", "8"),new Cards("P", "9"),
                new Cards("P", "10"), new Cards("P", "J"), new Cards("P", "Q"),
                new Cards("P", "K")};

        Cards[] khesht = {new Cards("KH", "A"), new Cards("KH", "2"), new Cards("KH", "3"),
                new Cards("KH", "4"), new Cards("KH", "5"), new Cards("KH", "6"),
                new Cards("KH", "7"),new Cards("KH", "8"),new Cards("KH", "9"),
                new Cards("KH", "10"), new Cards("KH", "J"), new Cards("KH", "Q"),
                new Cards("KH", "K")};

        Cards[] del = {new Cards("D", "A"), new Cards("D", "2"), new Cards("D", "3"),
                new Cards("D", "4"), new Cards("D", "5"), new Cards("D", "6"),
                new Cards("D", "7"),new Cards("D", "8"),new Cards("D", "9"),
                new Cards("D", "10"), new Cards("D", "J"), new Cards("D", "Q"),
                new Cards("D", "K")};

        Cards[] geshniz = {new Cards("G", "A"), new Cards("G", "2"), new Cards("G", "3"),
                new Cards("G", "4"), new Cards("G", "5"), new Cards("G", "6"),
                new Cards("G", "7"),new Cards("G", "8"),new Cards("G", "9"),
                new Cards("G", "10"), new Cards("G", "J"), new Cards("G", "Q"),
                new Cards("G", "K")};

        User[] players = {new User("player1"), new User("player2"), new User("player3"), new User("player4")};

        Game game = new Game(players, khesht, pick, del, geshniz);


    }



}
