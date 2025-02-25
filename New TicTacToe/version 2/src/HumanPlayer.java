import java.util.Scanner;

public class HumanPlayer implements Player {
    private String name;
    private char symbol;
    private Scanner scanner;

    public HumanPlayer(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getMove(UltimateBoard board, int forcedBoard) {
        while (true) {
            System.out.println(name + " (" + symbol + "), enter your move (mini-board row, mini-board col, row, col): ");
            int mbRow = scanner.nextInt();
            int mbCol = scanner.nextInt();
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (board.isMoveValid(mbRow, mbCol, row, col, forcedBoard)) {
                return new int[]{mbRow, mbCol, row, col};
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }
    }
}
