// Thinh Phan 101470541
// Colin Porter 101523487

 /**
 Represent player name and symbol ('B' or 'W').
 **/
public class Player {
    private final String name;
    private final char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}
