import java.io.IOException;
import java.sql.*;

public class Team {
    ClientHandler player1;
    ClientHandler player2;
    private int Score = 0;
    private int Round = 0;
    private int Teamid = 0;

    public Team(ClientHandler player1, ClientHandler player2) throws IOException {
        this.player1 = player1;
        this.player2 = player2;
        player1.sendObject("[SERVER] : Your TeamMate is {"+ player2.getUsername()+"}");
        player2.sendObject("[SERVER] : Your TeamMate is {"+ player1.getUsername()+"}");
        Teamid++;
    }

    public void updateScore(Team losingTeam , Game game , ClientHandler winningPlayer) throws IOException, SQLException {
        Score++;
        player1.sendObject("[SERVER] : Your Team has won the Hand!");
        player1.sendObject("SCORE " + Score);
        player1.sendObject("OPPONENT_SCORE " + losingTeam.getScore());


        player2.sendObject("[SERVER] : Your Team has won the Hand!");
        player2.sendObject("SCORE " + Score);
        player2.sendObject("OPPONENT_SCORE " + losingTeam.getScore());


        losingTeam.player1.sendObject("OPPONENT_SCORE " + Score);
        losingTeam.player2.sendObject("OPPONENT_SCORE " + Score);

        losingTeam.player1.sendObject("[SERVER] : Your Team has Lost the Hand!");
        losingTeam.player2.sendObject("[SERVER] : Your Team has Lost the Hand!");


        if(Score == 7){
            // if the opponent haven't got a single hand in one round
            if(Integer.valueOf(losingTeam.getScore()) == 0){
                //if the team is the Hakem's team , it gets 2 points and if it's not then 3 points
                if(player1.getIsHakem() || player2.getIsHakem()){
                    Round = Round + 2;
                    Score = 0;
                    losingTeam.setScore(0);
                    player1.sendObject("[SERVER] : Your Team has won the Round by kot --> 2 Points! \n");
                    player1.sendObject("ROUND " + Round);
                    player1.sendObject("OPPONENT_ROUND " + losingTeam.getRound());

                    player2.sendObject("[SERVER] : Your Team has won the Round by kot --> 2 Points! \n");
                    player2.sendObject("ROUND " + Round);
                    player2.sendObject("OPPONENT_ROUND " + losingTeam.getRound());

                    losingTeam.player1.sendObject("[SERVER] : Your team has lost the Round by kot --> 2 Points! \n");
                    losingTeam.player2.sendObject("[SERVER] : Your team has lost the Round by kot --> 2 Points! \n");

                    losingTeam.player1.sendObject("OPPONENT_SCORE " + Score);
                    losingTeam.player2.sendObject("OPPONENT_SCORE " + Score);

                    losingTeam.player1.sendObject("OPPONENT_ROUND " + Round);
                    losingTeam.player2.sendObject("OPPONENT_ROUND " + Round);

                    game.startGame(winningPlayer);

                    if(Round == 7){
                        updateRound(losingTeam);
                    }
                }
                else{
                    Round = Round + 3;
                    Score = 0;
                    losingTeam.setScore(0);
                    player1.sendObject("[SERVER] : Your Team has won the Round by kot --> 3 Points! \n");
                    player1.sendObject("ROUND " + Round);
                    player1.sendObject("OPPONENT_ROUND " + losingTeam.getRound());

                    player2.sendObject("[SERVER] : Your Team has won the Round by kot --> 3 Points! \n");
                    player2.sendObject("ROUND " + Round);
                    player2.sendObject("OPPONENT_ROUND " + losingTeam.getRound());


                    losingTeam.player1.sendObject("[SERVER] : Your team has lost the Round by kot --> 3 Points! \n");
                    losingTeam.player2.sendObject("[SERVER] : Your team has lost the Round by kot --> 3 Points! \n");

                    losingTeam.player1.sendObject("OPPONENT_SCORE " + Score);
                    losingTeam.player2.sendObject("OPPONENT_SCORE " + Score);

                    losingTeam.player1.sendObject("OPPONENT_ROUND " + Round);
                    losingTeam.player2.sendObject("OPPONENT_ROUND " + Round);

                    game.startGame(winningPlayer);

                    if(Round == 7){
                        updateRound(losingTeam);
                    }
                }

            }
            else{
                Round++;
                Score = 0;
                losingTeam.setScore(0);
                player1.sendObject("[SERVER] : Your Team has won the Round --> 1 Point! \n");
                player1.sendObject("ROUND " + Round);
                player1.sendObject("OPPONENT_ROUND " + losingTeam.getRound());

                player2.sendObject("[SERVER] : Your Team has won the Round --> 1 Point! \n");
                player2.sendObject("ROUND " + Round);
                player2.sendObject("OPPONENT_ROUND " + losingTeam.getRound());

                losingTeam.player1.sendObject("[SERVER] : Your team has lost the Round! \n");
                losingTeam.player1.sendObject("[SERVER] : Your team has lost the Round! \n");

                losingTeam.player1.sendObject("OPPONENT_SCORE " + Score);
                losingTeam.player2.sendObject("OPPONENT_SCORE " + Score);

                losingTeam.player1.sendObject("OPPONENT_ROUND " + Round);
                losingTeam.player2.sendObject("OPPONENT_ROUND " + Round);

                game.startGame(winningPlayer);

                if(Round == 7){
                    updateRound(losingTeam);
                }
            }
        }

    }

    private String getRound() {
        return String.valueOf(Round);
    }

    private void setScore(int i) {
        Score = i;
    }

    private String getScore() {
        return String.valueOf(Score);
    }
    String getid() {
        return String.valueOf(Teamid);
    }

    private void updateRound(Team losingTeam) throws IOException, SQLException {
        player1.sendObject("[SERVER] : CONGRATS !!! Your Team Won the Game !!!");
        player2.sendObject("[SERVER] : CONGRATS !!! Your Team Won the Game !!!");
        losingTeam.player1.sendObject("[SERVER] : Your Team Lost !! GAME OVER !!");
        losingTeam.player2.sendObject("[SERVER] : Your Team Lost !! GAME OVER !!");
        player1.sendObject("GAME_OVER");
        player2.sendObject("GAME_OVER");
        losingTeam.player1.sendObject("GAME_OVER");
        losingTeam.player2.sendObject("GAME_OVER");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hokmgame" ,"root" , "Sadraahmadi83@");
        String sql = "INSERT INTO Teams (Player1 , Player2 , RoundsWin) VALUES (?, ? , ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, player1.getUsername());
        statement.setString(2, player2.getUsername());
        statement.setString(3, getRound());
        statement.executeUpdate();

        sql = "INSERT INTO Teams (Player1 , Player2 , RoundsWin) VALUES (?, ? , ?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, losingTeam.player1.getUsername());
        statement.setString(2, losingTeam.player2.getUsername());
        statement.setString(3, losingTeam.getRound());
        statement.executeUpdate();
    }
}
