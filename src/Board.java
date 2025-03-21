// Thinh Phan 101470541
// Colin Porter 101523487

 /**
 Manage 9x9 board, placing moves, checking win conditions, and display current board state.
 **/
public class Board {
    private static final int SIZE = 9;
    private static final int WIN_CONDITION = 5;
    private static final char EMPTY = '.';
    private final char[][] grid;

    public Board() {
        grid = new char[SIZE][SIZE];
        initialize();
    }

    // Initialize board with empty cells
    public void initialize() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                grid[i][j] = EMPTY;
    }

    // Place symbol if move is valid
    public boolean placeMove(int row, int col, char symbol) {
        if (isValidMove(row, col)) {
            grid[row][col] = symbol;
            return true;
        }
        return false;
    }

    // Check if cell is empty and within bounds
    public boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && grid[row][col] == EMPTY;
    }

    // Return true if board is full
    public boolean isFull() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (grid[i][j] == EMPTY)
                    return false;
        return true;
    }

    // Check if most recent move resulted in a win
    public boolean checkWin(int row, int col, char symbol) {
        return checkDirection(row, col, 1, 0, symbol) || // Horizontal
                checkDirection(row, col, 0, 1, symbol) || // Vertical
                checkDirection(row, col, 1, 1, symbol) || // Diagonal \
                checkDirection(row, col, 1, -1, symbol);  // Diagonal /
    }

    // Count consecutive symbols in a direction
    private boolean checkDirection(int row, int col, int dRow, int dCol, char symbol) {
        int count = 1;

        // Forward direction
        for (int i = 1; i < WIN_CONDITION; i++) {
            int r = row + i * dRow, c = col + i * dCol;
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && grid[r][c] == symbol) count++;
            else break;
        }

        // Backward direction
        for (int i = 1; i < WIN_CONDITION; i++) {
            int r = row - i * dRow, c = col - i * dCol;
            if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && grid[r][c] == symbol) count++;
            else break;
        }

        return count >= WIN_CONDITION;
    }

    // Display board with row and column labels
    public void display() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++)
            System.out.print(i + " ");
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }

    public char[][] getGrid() {
        return grid;
    }

    public static int getSize() {
        return SIZE;
    }

    public static char getEmptySymbol() {
        return EMPTY;
    }
}
