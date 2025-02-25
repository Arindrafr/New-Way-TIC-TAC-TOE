import java.util.Scanner;
import java.util.Random;

    public class UltimateGame {
    private UltimateBoard board;
    private Player[] players;
    private int currentPlayerIndex;

    public UltimateGame() {
        this.board = new UltimateBoard();
        this.players = new Player[2];
        this.currentPlayerIndex = 0;
        setupPlayers();
    }

    private void setupPlayers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Player 1 name: ");
        String name1 = scanner.nextLine();
        char symbol1 = 'X';

        System.out.print("Enter Player 2 name (or type 'AI' for AI player): ");
        String name2 = scanner.nextLine();
        char symbol2 = 'O';

        if (name2.equalsIgnoreCase("AI")) {
            players[0] = new HumanPlayer(name1, symbol1);
            players[1] = new AIPlayer("AIBot", symbol2);
        } else {
            players[0] = new HumanPlayer(name1, symbol1);
            players[1] = new HumanPlayer(name2, symbol2);
        }
    }
    private int forcedBoard = -1;

    public void start() {
        while (true) {
            board.displayBoard();

            Player currentPlayer = players[currentPlayerIndex];
            System.out.println(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + ")'s turn.");

            int[] move = currentPlayer.getMove(board, forcedBoard);
            int miniRow = move[0], miniCol = move[1], row = move[2], col = move[3];

            if (board.makeMove(miniRow, miniCol, row, col, currentPlayer.getSymbol())) {
                if (board.isGameOver()) {
                    board.displayBoard();
                    System.out.println("Game Over! " + currentPlayer.getName() + " wins!");
                    break;
                }
                if (board.isFull()) {
                    board.displayBoard();
                    System.out.println("It's a draw!");
                    break;
                }

                forcedBoard = (row * 3) + col;
                currentPlayerIndex = (currentPlayerIndex + 1) % 2;
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }
    }




    public static void main(String[] args) {
        UltimateGame game = new UltimateGame();
        game.start();
    }
}
