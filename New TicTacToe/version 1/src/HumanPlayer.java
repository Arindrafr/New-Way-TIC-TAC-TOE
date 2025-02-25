import java.util.Scanner;

public class HumanPlayer implements Player {
    private String name;
    private char symbol;
    private Scanner scanner;

    public  HumanPlayer(String name, char symbol, Scanner scanner) {
        this.name = name;
        this.symbol = symbol;
        this.scanner = scanner;
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
        int row, col;
        while (true) {
            System.out.println(name + "'s turn (" + symbol + ") - Enter row and column (1-" + board.getSize() + "): ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    col = scanner.nextInt();
                    row--; col--;

                    if (board.isMoveValid(row, col)) {
                        return new int[]{row, col};
                    }
                }
            } else {
                scanner.next();
            }
            System.out.println("Invalid move! Try again.");
        }
    }
}
