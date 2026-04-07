import java.util.Random;

public class Stock {
    private String symbol;
    private String name;
    private double price;
    private Random random;

    public Stock(String symbol, String name, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.price = initialPrice;
        this.random = new Random();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Simulate price change
    public void updatePrice() {
        // Change price by -2% to +2%
        double changePercent = (random.nextDouble() * 0.04) - 0.02;
        this.price += this.price * changePercent;
        if (this.price < 0.01) {
            this.price = 0.01; // Minimum price
        }
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-20s | $%.2f", symbol, name, price);
    }
}
