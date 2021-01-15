import java.util.Scanner;

/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of
 * rounds. It also keeps track of the number of victories of each player.
 */
public class Competition {

	Player player1, player2;
	boolean displayMessage;
	int wins1, wins2;
	static int DEFAULT_BAD_INPUT_RETURN = -1;

    /** Constructs a Competition object with the given parameters:
     * @param player1 - The Player objects representing the first player.
     * @param player2 - The Player objects representing the second player
     * @param displayMessage - a flag indicating whether game play messages should be printed to the console.
     */
	public Competition(Player player1, Player player2, boolean displayMessage){
	    this.player1 = player1;
	    this.player2 = player2;
	    this.displayMessage = displayMessage;
	    wins1 = 0;
	    wins2 = 0;
    }

    /**
     * If playerPosition = 1, the results of the first player is returned.
     * If playerPosition = 2 the result of the second player is returned.
     * If playerPosition equals neither, -1 is returned.
     * @param playerPosition - The ID representing whether the player in question is the first or the second.
     * @return the number of victories of a player.
     */
    public int getPlayerScore(int playerPosition){
	    if (playerPosition == 1){
	        return wins1;
        }else if (playerPosition == 2){
	        return wins2;
        } else {
            return DEFAULT_BAD_INPUT_RETURN;
        }
    }

    /**
     * Run the game for the given number of rounds.
     * @param numRounds - number of rounds to play.
     */
    public void playMultipleRounds(int numRounds){
        for (int i = 0; i < numRounds; i++){
            Board board = new Board();
            Move thisTurnMove;
            Player[] whosTurn = new Player[2];
            whosTurn[0] = this.player1;
            whosTurn[1] = this.player2;
            int playersTurn = player1.getPlayerId();
            if (this.displayMessage)
            System.out.println("Welcome to the sticks game!");
            while (board.getNumberOfUnmarkedSticks() != 0){
                if (this.displayMessage)
                System.out.println("Player "+playersTurn+", it is now your turn!");
                thisTurnMove = whosTurn[playersTurn - 1].produceMove(board);
                while (board.markStickSequence(thisTurnMove) != 0){
                    if (this.displayMessage)
                    System.out.println("Invalid move. Enter another:");
                    thisTurnMove = whosTurn[playersTurn - 1].produceMove(board);
                    }
                if (this.displayMessage)
                System.out.println("Player "+playersTurn+" made the move: "+thisTurnMove.toString());
                if (playersTurn == player1.getPlayerId()){
                    playersTurn = player2.getPlayerId();
                }else {
                    playersTurn = player1.getPlayerId();
                }

            }
            if (playersTurn == player1.getPlayerId()){
                if (this.displayMessage)
                System.out.println("Player 1 won!");
                wins1++;
            }else {
                if (this.displayMessage)
                System.out.println("Player 2 won!");
                wins2++;
            }
        }
        System.out.println("The results are "+getPlayerScore(1)+":"+getPlayerScore(2));

    }

    /**
     * The method runs a Nim competition between two players according to the three user-specified arguments.
     * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
     *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
     * (2) The type of the second player, which is a positive integer between 1 and 4.
     * (3) The number of rounds to be played in the competition.
     * @param args an array of string representations of the three input arguments, as detailed above.
     */
    public static void main(String[] args) {
        boolean displayMessage = false;
        int p1Type = Integer.parseInt(args[0]);
        int p2Type = Integer.parseInt(args[1]);
        int numGames = Integer.parseInt(args[2]);
        Scanner scanner = new Scanner(System.in);
        Player p1 = new Player(p1Type,1, scanner);
        Player p2 = new Player(p2Type, 2, scanner);
        String stringOfType1 = p1.getTypeName();
        String stringOfType2 = p2.getTypeName();
        if (stringOfType1.equals("Human") || stringOfType2.equals("Human")){
            displayMessage = true;
        }
        Competition competition = new Competition(p1,p2,displayMessage);
        System.out.println("Starting a Nim competition of "+numGames+"rounds between a "+stringOfType1+" player and a "
                +stringOfType2+" player.");
        competition.playMultipleRounds(numGames);
        scanner.close();

    }

}
