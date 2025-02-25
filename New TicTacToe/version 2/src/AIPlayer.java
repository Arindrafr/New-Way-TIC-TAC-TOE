import java.util.*;

public class AIPlayer implements Player {
    private String name;
    private char symbol;
    private char opponentSymbol;
    private Random random;
    private static final int BASE_DEPTH = 3;
    private Map<String, Integer> transpositionTable;

    public AIPlayer(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.opponentSymbol = (symbol == 'X') ? 'O' : 'X';
        this.random = new Random();
        this.transpositionTable = new HashMap<>();
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
        System.out.println(name + " (" + symbol + ") is thinking...");

        if (forcedBoard != -1) {
            int miniRow = forcedBoard / 3;
            int miniCol = forcedBoard % 3;
            if (board.isMiniBoardFull(miniRow, miniCol)) {
                return board.findAnyAvailableMove();
            }
        }

        return getBestMove(board, forcedBoard);
    }

    private int[] getBestMove(UltimateBoard board, int forcedBoard) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        int currentDepth = getDynamicDepth(board);

        List<int[]> moves = board.getAllPossibleMoves(forcedBoard);
        orderMoves(moves, board);

        for (int[] move : moves) {
            UltimateBoard tempBoard = new UltimateBoard(board);
            tempBoard.makeMove(move[0], move[1], move[2], move[3], symbol);

            int score = minimax(tempBoard, currentDepth, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return (bestMove != null) ? bestMove : board.findAnyAvailableMove();
    }

    private int minimax(UltimateBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        String boardHash = board.getHash();
        if (transpositionTable.containsKey(boardHash)) {
            return transpositionTable.get(boardHash);
        }

        if (board.isGameOver() || depth == 0) {
            int eval = evaluateBoard(board);
            transpositionTable.put(boardHash, eval);
            return eval;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        List<int[]> moves = board.getAllPossibleMoves(-1);
        orderMoves(moves, board);

        for (int[] move : moves) {
            UltimateBoard tempBoard = new UltimateBoard(board);
            tempBoard.makeMove(move[0], move[1], move[2], move[3], isMaximizing ? symbol : opponentSymbol);

            int score = minimax(tempBoard, depth - 1, !isMaximizing, alpha, beta);
            bestScore = isMaximizing ? Math.max(bestScore, score) : Math.min(bestScore, score);

            if (isMaximizing) {
                alpha = Math.max(alpha, score);
            } else {
                beta = Math.min(beta, score);
            }

            if (beta <= alpha) break;
        }

        transpositionTable.put(boardHash, bestScore);
        return bestScore;
    }

    private int evaluateBoard(UltimateBoard board) {
        int score = 0;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board.isMiniBoardWon(r, c, symbol)) {
                    score += 50;
                } else if (board.isMiniBoardWon(r, c, opponentSymbol)) {
                    score -= 50;
                }
            }
        }

        if (board.isMiniBoardWon(1, 1, symbol)) score += 30;
        if (board.isMiniBoardWon(1, 1, opponentSymbol)) score -= 30;

        for (int[] corner : new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}}) {
            if (board.isMiniBoardWon(corner[0], corner[1], symbol)) score += 20;
            if (board.isMiniBoardWon(corner[0], corner[1], opponentSymbol)) score -= 20;
        }

        return score;
    }

    private int getDynamicDepth(UltimateBoard board) {
        int availableMoves = board.getAllPossibleMoves(-1).size();
        if (availableMoves > 20) return 3;
        if (availableMoves > 10) return 4;
        return 5;
    }

    private void orderMoves(List<int[]> moves, UltimateBoard board) {
        moves.sort((move1, move2) -> {
            UltimateBoard temp1 = new UltimateBoard(board);
            temp1.makeMove(move1[0], move1[1], move1[2], move1[3], symbol);
            int score1 = evaluateBoard(temp1);

            UltimateBoard temp2 = new UltimateBoard(board);
            temp2.makeMove(move2[0], move2[1], move2[2], move2[3], symbol);
            int score2 = evaluateBoard(temp2);

            return Integer.compare(score2, score1);
        });
    }
}


/* smart but slow
import java.util.List;
import java.util.Random;

public class AIPlayer implements Player {
    private String name;
    private char symbol;
    private char opponentSymbol;
    private Random random;
    private static final int MAX_DEPTH = 5;

    public AIPlayer(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.opponentSymbol = (symbol == 'X') ? 'O' : 'X';
        this.random = new Random();
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
        System.out.println(name + " (" + symbol + ") is thinking...");

        if (forcedBoard != -1) {
            int miniRow = forcedBoard / 3;
            int miniCol = forcedBoard % 3;
            if (board.isMiniBoardFull(miniRow, miniCol)) {
                return board.findAnyAvailableMove();
            }
        }

        return getBestMove(board, forcedBoard);
    }

    private int[] getBestMove(UltimateBoard board, int forcedBoard) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        List<int[]> moves = board.getAllPossibleMoves(forcedBoard);
        for (int[] move : moves) {

            UltimateBoard tempBoard = new UltimateBoard(board);
            tempBoard.makeMove(move[0], move[1], move[2], move[3], symbol);


            int score = minimax(tempBoard, MAX_DEPTH, false, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return (bestMove != null) ? bestMove : board.findAnyAvailableMove();
    }

    private int minimax(UltimateBoard board, int depth, boolean isMaximizing, int alpha, int beta) {
        if (board.isGameOver() || depth == 0) {
            return evaluateBoard(board);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : board.getAllPossibleMoves(-1)) {
                UltimateBoard tempBoard = new UltimateBoard(board);
                tempBoard.makeMove(move[0], move[1], move[2], move[3], symbol);
                int eval = minimax(tempBoard, depth - 1, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : board.getAllPossibleMoves(-1)) {
                UltimateBoard tempBoard = new UltimateBoard(board);
                tempBoard.makeMove(move[0], move[1], move[2], move[3], opponentSymbol);
                int eval = minimax(tempBoard, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    private int evaluateBoard(UltimateBoard board) {
        int score = 0;


        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board.isMiniBoardWon(r, c, symbol)) {
                    score += 50;
                } else if (board.isMiniBoardWon(r, c, opponentSymbol)) {
                    score -= 50;
                }
            }
        }


        if (board.isMiniBoardWon(1, 1, symbol)) score += 30;
        if (board.isMiniBoardWon(1, 1, opponentSymbol)) score -= 30;


        for (int[] corner : new int[][]{{0, 0}, {0, 2}, {2, 0}, {2, 2}}) {
            if (board.isMiniBoardWon(corner[0], corner[1], symbol)) score += 20;
            if (board.isMiniBoardWon(corner[0], corner[1], opponentSymbol)) score -= 20;
        }

        return score;
    }
}*/


/* stupid AI
import java.util.Random;

public class AIPlayer implements Player {
    private String name;
    private char symbol;
    private Random random;

    public AIPlayer(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.random = new Random();
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
        System.out.println(name + " (" + symbol + ") is thinking...");

        int miniRow = forcedBoard / 3;
        int miniCol = forcedBoard % 3;


        if (board.isMiniBoardFull(miniRow, miniCol)) {
            System.out.println("Mini-board [" + miniRow + "," + miniCol + "] is full! Choosing another board...");
            return board.findAnyAvailableMove();
        }


        return minimax(board, forcedBoard, true);
    }

    private int[] minimax(UltimateBoard board, int forcedBoard, boolean isMaximizing) {
        int[][] availableMoves = board.getAvailableMoves(forcedBoard);

        if (availableMoves.length == 0) {
            int[] backupMove = board.findAnyAvailableMove();
            return new int[]{backupMove[0], backupMove[1], backupMove[2], backupMove[3]};

        }

        return availableMoves[random.nextInt(availableMoves.length)];
    }


}
*/
