public class Board {
    private char[][] grid;
    private int size;

    public Board(int size) {
        this.size = size;
        grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '-';
            }
        }
    }

    public void displayBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    //v2
    public void undoMove(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            grid[row][col] = '-';
        }
    }


    public int getSize() {
        return this.size;
    }

    public boolean isMoveValid(int row, int col) {
        return row >= 0 && col >= 0 && row < size && col < size && grid[row][col] == '-';
    }

    public void makeMove(int row, int col, Player player) {
        if (isMoveValid(row, col)) {
            grid[row][col] = player.getSymbol();
        }
    }

    public boolean checkWin() {
        for (int i = 0; i < size; i++) {
            if (grid[i][0] != '-' && checkLine(grid[i])) return true;
            char[] column = new char[size];
            for (int j = 0; j < size; j++) {
                column[j] = grid[j][i];
            }
            if (column[0] != '-' && checkLine(column)) return true;
        }

        char[] diag1 = new char[size];
        char[] diag2 = new char[size];
        for (int i = 0; i < size; i++) {
            diag1[i] = grid[i][i];
            diag2[i] = grid[i][size - 1 - i];
        }

        if (diag1[0] != '-' && checkLine(diag1)) return true;
        if (diag2[0] != '-' && checkLine(diag2)) return true;

        return false;
    }

    private boolean checkLine(char[] line) {
        for (int i = 1; i < line.length; i++) {
            if (line[i] != line[0]) return false;
        }
        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}
