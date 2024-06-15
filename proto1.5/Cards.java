import java.util.ArrayList;
import java.util.List;

public class Cards {
    private String suit;
    private String number;

    private int value;

    private String name;
    private String ImgPath;
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
    public Cards(String suit, String number, int value, String name , String ImgPath) {
        this.suit = suit;
        this.number = number;
        this.value = value;
        this.name = name;
        this.ImgPath = ImgPath;
    }

    static Cards[] pick = {
            new Cards("P", "A", 13, "AP" , "deck/AP.gif"),
            new Cards("P", "2", 1, "2P" , "deck/2P.gif"),
            new Cards("P", "3", 2, "3P" , "deck/3P.gif"),
            new Cards("P", "4", 3, "4P" , "deck/4P.gif"),
            new Cards("P", "5", 4, "5P" , "deck/5P.gif"),
            new Cards("P", "6", 5, "6P" , "deck/6P.gif"),
            new Cards("P", "7", 6, "7P" , "deck/7P.gif"),
            new Cards("P", "8", 7, "8P" , "deck/8P.gif"),
            new Cards("P", "9", 8, "9P" , "deck/9P.gif"),
            new Cards("P", "10", 9, "10P" , "deck/10P.gif"),
            new Cards("P", "J", 10, "JP" , "deck/JP.gif"),
            new Cards("P", "Q", 11, "QP" , "deck/QP.gif"),
            new Cards("P", "K", 13, "KP" , "deck/KP.gif"),
    };

    static Cards[] khesht = {
            new Cards("KH", "A", 13, "AK" , "deck/AK.gif"),
            new Cards("KH", "2", 1, "2K" , "deck/2K.gif"),
            new Cards("KH", "3", 2, "3K" , "deck/3K.gif"),
            new Cards("KH", "4", 3, "4K" , "deck/4K.gif"),
            new Cards("KH", "5", 4, "5K" , "deck/5K.gif"),
            new Cards("KH", "6", 5, "6K" , "deck/6K.gif"),
            new Cards("KH", "7", 6, "7K" , "deck/7K.gif"),
            new Cards("KH", "8", 7, "8K" , "deck/8K.gif"),
            new Cards("KH", "9", 8, "9K" , "deck/9K.gif"),
            new Cards("KH", "10", 9, "10K" , "deck/10K.gif"),
            new Cards("KH", "J", 10, "JK" , "deck/10J.gif"),
            new Cards("KH", "Q", 11, "QK" , "deck/11K.gif"),
            new Cards("KH", "K", 13, "KK" , "deck/13K.gif"),
    };

    static Cards[] del = {
            new Cards("D", "A", 13, "AD" , "deck/AD.gif"),
            new Cards("D", "2", 1, "2D" , "deck/2D.gif"),
            new Cards("D", "3", 2, "3D" , "deck/3D.gif"),
            new Cards("D", "4", 3, "4D" , "deck/4D.gif"),
            new Cards("D", "5", 4, "5D" , "deck/5D.gif"),
            new Cards("D", "6", 5, "6D" , "deck/6D.gif"),
            new Cards("D", "7", 6, "7D", "deck/7D.gif"),
            new Cards("D", "8", 7, "8D", "deck/8D.gif"),
            new Cards("D", "9", 8, "9D", "deck/9D.gif"),
            new Cards("D", "10", 9, "10D", "deck/10D.gif"),
            new Cards("D", "J", 10, "JD", "deck/JD.gif"),
            new Cards("D", "Q", 11, "QD", "deck/QD.gif"),
            new Cards("D", "K", 13, "KD", "deck/KD.gif")
    };

    static Cards[] geshniz = {
            new Cards("G", "A", 13, "AG" , "deck/AG.gif"),
            new Cards("G", "2", 1, "2G" , "deck/2G.gif"),
            new Cards("G", "3", 2, "3G" , "deck/3G.gif"),
            new Cards("G", "4", 3, "4G" , "deck/4G.gif"),
            new Cards("G", "5", 4, "5G" , "deck/5G.gif"),
            new Cards("G", "6", 5, "6G" , "deck/6G.gif"),
            new Cards("G", "7", 6, "7G" , "deck/7G.gif"),
            new Cards("G", "8", 7, "8G" , "deck/8G.gif"),
            new Cards("G", "9", 8, "9G" , "deck/9G.gif"),
            new Cards("G", "10", 9, "10G" , "deck/10G.gif"),
            new Cards("G", "J", 10, "JG" , "deck/JG.gif"),
            new Cards("G", "Q", 11, "QG" , "deck/QG.gif"),
            new Cards("G", "K", 13, "KG" , "deck/KG.gif"),
    };

    public static List<Cards> getAllCards() {
        //we get the whole list of cards in here and return it
        List<Cards> allCards = new ArrayList<>();
        allCards.addAll(List.of(pick));
        allCards.addAll(List.of(khesht));
        allCards.addAll(List.of(geshniz));
        allCards.addAll(List.of(del));
        return allCards;
    }

    public static void main(String[] args) {
        // login l1 = new login();
    }
}
