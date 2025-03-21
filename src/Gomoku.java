// Thinh Phan 101470541
// Colin Porter 101523487

import java.util.Scanner;

 /**
 Main class. Handles game modes, user input,and game loop for both player-vs-player and player-vs-AI.
 **/
public class Gomoku {
    private final Scanner scanner = new Scanner(System.in);
    private final Board board = new Board();
    private Player player1;
    private Player player2;
    private AIPlayer aiPlayer;
    private boolean isVsAI;
    private char currentSymbol = 'B';

    public void start() {
        System.out.print("Select mode (1: Player vs AI, 2: Player vs Player): ");
        isVsAI = scanner.nextInt() == 1;
        board.initialize();
        scanner.nextLine(); // consume newline

        if (isVsAI) {
            setupVsAI();
        } else {
            setupVsPlayer();
        }

        playGame();
    }

    // Player vs AI setup
    private void setupVsAI() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Choose your symbol (B/W): ");
        char playerSymbol = Character.toUpperCase(scanner.next().charAt(0));
        while (playerSymbol != 'B' && playerSymbol != 'W') {
            System.out.print("Invalid symbol. Choose B or W: ");
            playerSymbol = Character.toUpperCase(scanner.next().charAt(0));
        }

        char aiSymbol = (playerSymbol == 'B') ? 'W' : 'B';
        player1 = new Player(name, playerSymbol);
        aiPlayer = new AIPlayer(aiSymbol);

        // AI plays first if black
        if (aiSymbol == 'B') {
            aiPlayer.makeMove(board);
            currentSymbol = 'W';
        } else {
            currentSymbol = 'B';
        }
    }

    // 2-player mode setup
    private void setupVsPlayer() {
        System.out.print("Enter name for Player 1: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter name for Player 2: ");
        String name2 = scanner.nextLine();
        System.out.print(name1 + ", choose your symbol (B/W): ");
        char symbol1 = Character.toUpperCase(scanner.next().charAt(0));
        while (symbol1 != 'B' && symbol1 != 'W') {
            System.out.print("Invalid symbol. Choose B or W: ");
            symbol1 = Character.toUpperCase(scanner.next().charAt(0));
        }

        char symbol2 = (symbol1 == 'B') ? 'W' : 'B';
        player1 = new Player(name1, symbol1);
        player2 = new Player(name2, symbol2);
        System.out.println(name2 + " will be '" + symbol2 + "'");
    }

    // Main game loop
    private void playGame() {
        while (true) {
            board.display();

            if (isVsAI) {
                if (currentSymbol == aiPlayer.getSymbol()) {
                    aiPlayer.makeMove(board);
                } else {
                    makePlayerMove(player1);
                }
            } else {
                Player currentPlayer = (currentSymbol == player1.getSymbol()) ? player1 : player2;
                System.out.println(currentPlayer.getName() + "'s turn (" + currentSymbol + ")");
                makePlayerMove(currentPlayer);
            }

            // Check for win
            if (checkWin(currentSymbol)) {
                board.display();
                String winner = isVsAI
                        ? (currentSymbol == player1.getSymbol() ? player1.getName() : "AI")
                        : ((currentSymbol == player1.getSymbol()) ? player1.getName() : player2.getName());
                System.out.println(winner + " wins!");
                break;
            }

            // Check for draw
            if (board.isFull()) {
                board.display();
                System.out.println("It's a draw!");
                break;
            }

            // Switch turns
            currentSymbol = (currentSymbol == 'B') ? 'W' : 'B';
        }
    }

    // Handle player input
    private void makePlayerMove(Player player) {
        int row, col;
        while (true) {
            System.out.print("Enter row and column (e.g. 4 5): ");
            if (!scanner.hasNextInt()) {
                scanner.next(); // Clear invalid input
                System.out.println("Invalid input. Enter numbers.");
                continue;
            }
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (board.placeMove(row, col, player.getSymbol())) {
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    // Check if current symbol has won
    private boolean checkWin(char symbol) {
        char[][] grid = board.getGrid();
        for (int i = 0; i < Board.getSize(); i++) {
            for (int j = 0; j < Board.getSize(); j++) {
                if (grid[i][j] == symbol && board.checkWin(i, j, symbol)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new Gomoku().start();
    }
}
