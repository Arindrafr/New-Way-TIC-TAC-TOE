import java.util.Random;

public class AIPlayer implements Player {
    private static final String[] AI_NAMES = {"Adam", "Edi", "Robo", "Max", "Luna", "Zeta", "Tobi", "Neo"};
    private static int aiCount = 0;

    private String name;
    private char symbol;
    private Random random;
    private String difficulty;

    public AIPlayer(char symbol, String difficulty) {
        this.symbol = symbol;
        this.random = new Random();
        this.difficulty = difficulty;

        if (aiCount < AI_NAMES.length) {
            this.name = AI_NAMES[aiCount] + "Bot";
            aiCount++;
        } else {
            this.name = "AI" + (aiCount + 1) + "Bot";
            aiCount++;
        }
    }

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int[] getMove(Board board) {
        System.out.println(name + "'s turn (" + symbol + ") - AI (" + difficulty + ") is thinking...");

        if (difficulty.equalsIgnoreCase("Hard")) {
            return getSmartMove(board);
        } else {
            return getRandomMove(board);
        }
    }

    private int[] getRandomMove(Board board) {
        int row, col;
        do {
            row = random.nextInt(board.getSize());
            col = random.nextInt(board.getSize());
        } while (!board.isMoveValid(row, col));
        return new int[]{row, col};
    }

    private int[] getSmartMove(Board board) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.isMoveValid(i, j)) {
                    board.makeMove(i, j, this);
                    if (board.checkWin()) {
                        board.undoMove(i, j);
                        return new int[]{i, j};
                    }
                    board.undoMove(i, j);
                }
            }
        }
        return getRandomMove(board);
    }
}