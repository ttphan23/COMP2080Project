// Thinh Phan 101470541
// Colin Porter 101523487

/**
 * Implements the AI player using basic Minimax algorithm (no alpha-beta pruning).
 */
public class AIPlayer {
    private final char aiSymbol;
    private final char opponentSymbol;
    private final int MAX_DEPTH = 2; // Depth limit for performance

    public AIPlayer(char aiSymbol) {
        this.aiSymbol = aiSymbol;
        this.opponentSymbol = (aiSymbol == 'B') ? 'W' : 'B';
    }

    public void makeMove(Board board) {
        System.out.println("AI is thinking...");
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1, bestCol = -1;
        char[][] grid = board.getGrid();

        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                if (grid[i][j] == Board.getEmptySymbol()) {
                    grid[i][j] = aiSymbol;
                    int score = minimax(board, 0, false);
                    grid[i][j] = Board.getEmptySymbol();

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        board.placeMove(bestRow, bestCol, aiSymbol);
        System.out.println("AI placed " + aiSymbol + " at (" + bestRow + ", " + bestCol + ")");
    }

    // Minimax algorithm
    private int minimax(Board board, int depth, boolean isMaximizing) {
        if (depth >= MAX_DEPTH || board.isFull()) {
            return evaluateBoard(board);
        }

        char[][] grid = board.getGrid();
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                if (grid[i][j] == Board.getEmptySymbol()) {
                    grid[i][j] = isMaximizing ? aiSymbol : opponentSymbol;
                    boolean win = board.checkWin(i, j, grid[i][j]);

                    int score = win
                            ? (isMaximizing ? 100 - depth : -100 + depth)
                            : minimax(board, depth + 1, !isMaximizing);

                    grid[i][j] = Board.getEmptySymbol();

                    if (isMaximizing) {
                        bestScore = Math.max(bestScore, score);
                    } else {
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }

        return bestScore;
    }

    // Evaluate final board state
    private int evaluateBoard(Board board) {
        char[][] grid = board.getGrid();

        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                if (grid[i][j] == aiSymbol && board.checkWin(i, j, aiSymbol)) return 100;
                if (grid[i][j] == opponentSymbol && board.checkWin(i, j, opponentSymbol)) return -100;
            }
        }
        return 0;
    }

    public char getSymbol() {
        return aiSymbol;
    }
}
