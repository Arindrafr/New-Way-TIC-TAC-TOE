import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIndex;
    private InputHandler inputHandler;

    public Game(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
        int totalPlayers = inputHandler.getValidNumber("Enter number of players (2 or 3): ", 2, 3);
        int boardSize = inputHandler.getValidNumber("Enter board size (Min 3): ", 3, 10);

        this.board = new Board(boardSize);
        this.players = new Player[totalPlayers];
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int aiCount = 0;

        Set<Character> usedSymbols = new HashSet<>();

        for (int i = 0; i < totalPlayers; i++) {
            boolean useAI = false;

            if (aiCount < 2) {
                useAI = inputHandler.getValidChoice("Should Player " + (i + 1) + " be an AI? (yes/no): ");
            }

            if (useAI) {
                String difficulty = inputHandler.getValidDifficulty();
                char aiSymbol;
                do {
                    aiSymbol = (char) ('A' + random.nextInt(26));
                } while (usedSymbols.contains(aiSymbol));

                usedSymbols.add(aiSymbol);
                players[i] = new AIPlayer(aiSymbol, difficulty);
                aiCount++;
            } else {
                String name = inputHandler.getValidName(i + 1);
                char symbol;
                do {
                    symbol = inputHandler.getValidSymbol(i + 1);
                } while (usedSymbols.contains(symbol));

                usedSymbols.add(symbol);
                players[i] = new HumanPlayer(name, symbol, scanner);
            }
        }

        this.currentPlayerIndex = 0;
    }

    public void start() {
        while (true) {
            board.displayBoard();
            Player currentPlayer = players[currentPlayerIndex];

            int[] move = currentPlayer.getMove(board);
            board.makeMove(move[0], move[1], currentPlayer);

            if (board.checkWin()) {
                board.displayBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            } else if (board.isFull()) {
                board.displayBoard();
                System.out.println("It's a draw!");
                break;
            }
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(scanner);
        Game game = new Game(inputHandler);
        game.start();
    }
}

