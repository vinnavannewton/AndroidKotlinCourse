import java.util.HashMap;
import java.util.Map;

public class User {
    private double balance;
    private Map<String, Integer> portfolio; // Symbol -> Quantity

    public User(double initialBalance) {
        this.balance = initialBalance;
        this.portfolio = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public boolean buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > balance) {
            System.out.println("Insufficient funds!");
            return false;
        }
        balance -= cost;
        portfolio.put(stock.getSymbol(), portfolio.getOrDefault(stock.getSymbol(), 0) + quantity);
        System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        return true;
    }

    public boolean sellStock(Stock stock, int quantity) {
        String symbol = stock.getSymbol();
        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity) {
            System.out.println("Not enough shares to sell!");
            return false;
        }

        double revenue = stock.getPrice() * quantity;
        balance += revenue;

        int currentQuantity = portfolio.get(symbol);
        if (currentQuantity == quantity) {
            portfolio.remove(symbol);
        } else {
            portfolio.put(symbol, currentQuantity - quantity);
        }
        System.out.println("Sold " + quantity + " shares of " + symbol);
        return true;
    }
}
