public class MiniBoard {
    private char[][] grid;
    private char winner;

    public MiniBoard() {
        grid = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = '-';
            }
        }
        winner = '-';
    }

    public boolean makeMove(int row, int col, char symbol) {
        if (grid[row][col] == '-') {
            grid[row][col] = symbol;
            updateWinner();
            return true;
        }
        return false;
    }

    public void updateWinner() {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] != '-' && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                winner = grid[i][0];
            }
            if (grid[0][i] != '-' && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {
                winner = grid[0][i];
            }
        }
        if (grid[0][0] != '-' && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
            winner = grid[0][0];
        }
        if (grid[0][2] != '-' && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0]) {
            winner = grid[0][2];
        }
    }

    public boolean isFull() {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '-') return false;
            }
        }
        return true;
    }

    public char getWinner() {
        return winner;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }
    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == '-';
    }

    public MiniBoard(MiniBoard other) {
        this.grid = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.grid[i][j] = other.grid[i][j];
            }
        }
        this.winner = other.winner; 
    }

}
