import java.util.ArrayList;
import java.util.List;

public class UltimateBoard {
    private MiniBoard[][] miniBoards;

    public UltimateBoard() {
        miniBoards = new MiniBoard[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                miniBoards[i][j] = new MiniBoard();
            }
        }
    }

    public boolean isMoveValid(int mbRow, int mbCol, int row, int col, int forcedBoard) {
        return miniBoards[mbRow][mbCol].makeMove(row, col, '-');
    }

    public int[][] getAvailableMoves(int forcedBoard) {
        return new int[0][0];
    }

    public boolean isMiniBoardFull(int miniRow, int miniCol) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (miniBoards[miniRow][miniCol].getCell(i, j) == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] findAvailableMiniBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!isMiniBoardFull(i, j)) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalStateException("No available mini-board found!");
    }

    public int[] findAnyAvailableMove() {
        for (int miniRow = 0; miniRow < 3; miniRow++) {
            for (int miniCol = 0; miniCol < 3; miniCol++) {
                if (!isMiniBoardFull(miniRow, miniCol)) {
                    for (int row = 0; row < 3; row++) {
                        for (int col = 0; col < 3; col++) {
                            if (miniBoards[miniRow][miniCol].isCellEmpty(row, col)) {
                                return new int[]{miniRow, miniCol, row, col};
                            }
                        }
                    }
                }
            }
        }


        throw new IllegalStateException("No available moves on the entire board! Debugging needed.");
    }

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String WHITE = "\u001B[37m";

    public void displayBoard() {
        System.out.println("\n      0   1   2      3   4   5      6   7   8");
        System.out.println("    -------------------------------------------");

        for (int i = 0; i < 3; i++) {
            for (int miniRow = 0; miniRow < 3; miniRow++) {
                System.out.print((i * 3 + miniRow) + " | ");

                for (int j = 0; j < 3; j++) {
                    for (int miniCol = 0; miniCol < 3; miniCol++) {
                        char symbol = miniBoards[i][j].getCell(miniRow, miniCol);
                        String coloredSymbol = (symbol == 'X') ? RED + "X" + RESET :
                                (symbol == 'O') ? BLUE + "O" + RESET :
                                        WHITE + "-" + RESET;
                        System.out.print(" " + coloredSymbol + " ");
                    }
                    System.out.print(" | ");
                }
                System.out.println();
            }
            System.out.println("    -------------------------------------------");
        }
    }


    private char winner = '-';

    public char getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return winner != '-';
    }

    public boolean makeMove(int miniRow, int miniCol, int row, int col, char symbol) {
        if (miniBoards[miniRow][miniCol].makeMove(row, col, symbol)) {
            updateWinner();
            return true;
        }
        return false;
    }
    private void updateWinner() {
        for (int i = 0; i < 3; i++) {
            if (miniBoards[i][0].getWinner() != '-' &&
                    miniBoards[i][0].getWinner() == miniBoards[i][1].getWinner() &&
                    miniBoards[i][1].getWinner() == miniBoards[i][2].getWinner()) {
                winner = miniBoards[i][0].getWinner();
            }

            if (miniBoards[0][i].getWinner() != '-' &&
                    miniBoards[0][i].getWinner() == miniBoards[1][i].getWinner() &&
                    miniBoards[1][i].getWinner() == miniBoards[2][i].getWinner()) {
                winner = miniBoards[0][i].getWinner();
            }
        }

        if (miniBoards[0][0].getWinner() != '-' &&
                miniBoards[0][0].getWinner() == miniBoards[1][1].getWinner() &&
                miniBoards[1][1].getWinner() == miniBoards[2][2].getWinner()) {
            winner = miniBoards[0][0].getWinner();
        }

        if (miniBoards[0][2].getWinner() != '-' &&
                miniBoards[0][2].getWinner() == miniBoards[1][1].getWinner() &&
                miniBoards[1][1].getWinner() == miniBoards[2][0].getWinner()) {
            winner = miniBoards[0][2].getWinner();
        }
    }


    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!miniBoards[i][j].isFull()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<int[]> getAllPossibleMoves(int forcedBoard) {
        List<int[]> moves = new ArrayList<>();

        if (forcedBoard != -1) {
            int miniRow = forcedBoard / 3;
            int miniCol = forcedBoard % 3;
            if (!isMiniBoardFull(miniRow, miniCol)) {
                addMovesFromMiniBoard(moves, miniRow, miniCol);
                return moves;
            }
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!isMiniBoardFull(i, j)) {
                    addMovesFromMiniBoard(moves, i, j);
                }
            }
        }
        return moves;
    }

    private void addMovesFromMiniBoard(List<int[]> moves, int miniRow, int miniCol) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (miniBoards[miniRow][miniCol].isCellEmpty(row, col)) {
                    moves.add(new int[]{miniRow, miniCol, row, col});
                }
            }
        }
    }


    public boolean isMiniBoardWon(int miniRow, int miniCol, char player) {
        return miniBoards[miniRow][miniCol].getWinner() == player;
    }

    public UltimateBoard(UltimateBoard other) {
        miniBoards = new MiniBoard[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                miniBoards[i][j] = new MiniBoard(other.miniBoards[i][j]); // Deep copy
            }
        }
        this.winner = other.winner;
    }
    public String getHash() {
        StringBuilder hash = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                MiniBoard miniBoard = miniBoards[i][j];

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        hash.append(miniBoard.getCell(row, col));
                    }
                }
                hash.append(miniBoard.getWinner());
            }
        }

        return hash.toString();
    }

}
