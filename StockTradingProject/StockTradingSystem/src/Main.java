import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Market market = new Market();
        User user = new User(10000.00); // Start with $10,000
        Scanner scanner = new Scanner(System.in);

        market.startSimulation();
        System.out.println("Welcome to the Real-Time Stock Trading System!");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Prices ---");
                    for (Stock stock : market.getStocks()) {
                        System.out.println(String.format("%-5s : $%.2f", stock.getSymbol(), stock.getPrice()));
                    }
                    break;
                case 2:
                    System.out.print("Enter symbol to buy: ");
                    String buySymbol = scanner.next().toUpperCase();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        user.buyStock(buyStock, quantity);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter symbol to sell: ");
                    String sellSymbol = scanner.next().toUpperCase(); // Fixed variable name collision if any
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        System.out.print("Enter quantity: ");
                        int qty = scanner.nextInt();
                        user.sellStock(sellStock, qty);
                    } else {
                        System.out.println("Stock not found.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- Your Portfolio ---");
                    System.out.println("Balance: $" + String.format("%.2f", user.getBalance()));
                    if (user.getPortfolio().isEmpty()) {
                        System.out.println("No stocks owned.");
                    } else {
                        for (String symbol : user.getPortfolio().keySet()) {
                            int count = user.getPortfolio().get(symbol);
                            Stock currentStock = market.getStock(symbol);
                            double value = currentStock.getPrice() * count;
                            System.out.println(String.format("%s: %d shares (Value: $%.2f)", symbol, count, value));
                        }
                    }
                    break;
                case 5:
                    running = false;
                    market.stopSimulation();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
        System.exit(0); // Ensure threads stop
    }
}
