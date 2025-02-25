public interface Player {
    char getSymbol();
    String getName();
    int[] getMove(UltimateBoard board, int forcedBoard);
}
