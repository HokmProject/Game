package gameLogic;

public class Main {

        private String ID;

        static Cards[] pick = {
                        new Cards("P", "A", 13, "AP"),
                        new Cards("P", "2", 1, "2P"),
                        new Cards("P", "3", 2, "3P"),
                        new Cards("P", "4", 3, "4P"),
                        new Cards("P", "5", 4, "5P"),
                        new Cards("P", "6", 5, "6P"),
                        new Cards("P", "7", 6, "7P"),
                        new Cards("P", "8", 7, "8P"),
                        new Cards("P", "9", 8, "9P"),
                        new Cards("P", "10", 9, "10P"),
                        new Cards("P", "J", 10, "JP"),
                        new Cards("P", "Q", 11, "QP"),
                        new Cards("P", "K", 13, "KP")
        };

        static Cards[] khesht = {
                        new Cards("KH", "A", 13, "AK"),
                        new Cards("KH", "2", 1, "2K"),
                        new Cards("KH", "3", 2, "3K"),
                        new Cards("KH", "4", 3, "4K"),
                        new Cards("KH", "5", 4, "5K"),
                        new Cards("KH", "6", 5, "6K"),
                        new Cards("KH", "7", 6, "7K"),
                        new Cards("KH", "8", 7, "8K"),
                        new Cards("KH", "9", 8, "9K"),
                        new Cards("KH", "10", 9, "10K"),
                        new Cards("KH", "J", 10, "JK"),
                        new Cards("KH", "Q", 11, "QK"),
                        new Cards("KH", "K", 13, "KK")
        };

        static Cards[] del = {
                        new Cards("D", "A", 13, "AD"),
                        new Cards("D", "2", 1, "2D"),
                        new Cards("D", "3", 2, "3D"),
                        new Cards("D", "4", 3, "4D"),
                        new Cards("D", "5", 4, "5D"),
                        new Cards("D", "6", 5, "6D"),
                        new Cards("D", "7", 6, "7D"),
                        new Cards("D", "8", 7, "8D"),
                        new Cards("D", "9", 8, "9D"),
                        new Cards("D", "10", 9, "9D"),
                        new Cards("D", "J", 10, "JD"),
                        new Cards("D", "Q", 11, "QD"),
                        new Cards("D", "K", 13, "KD")
        };

        static Cards[] geshniz = {
                        new Cards("G", "A", 13, "AG"),
                        new Cards("G", "2", 1, "2G"),
                        new Cards("G", "3", 2, "3G"),
                        new Cards("G", "4", 3, "4G"),
                        new Cards("G", "5", 4, "5G"),
                        new Cards("G", "6", 5, "6G"),
                        new Cards("G", "7", 6, "7G"),
                        new Cards("G", "8", 7, "8G"),
                        new Cards("G", "9", 8, "9G"),
                        new Cards("G", "10", 9, "10G"),
                        new Cards("G", "J", 10, "JG"),
                        new Cards("G", "Q", 11, "QG"),
                        new Cards("G", "K", 13, "KG")
        };

        static public User[] players = {
                        new User("player1"),
                        new User("player2"),
                        new User("player3"),
                        new User("player4")
        };

        public static void main(String[] args) {
                // login l1 = new login();
        }
}
