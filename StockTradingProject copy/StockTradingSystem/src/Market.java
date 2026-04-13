import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Market {
    private List<Stock> stocks;
    private ScheduledExecutorService scheduler;

    public Market() {
        stocks = new ArrayList<>();
        initializeStocks();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    private void initializeStocks() {
        stocks.add(new Stock("AAPL", "Apple Inc.", 150.00));
        stocks.add(new Stock("GOOG", "Alphabet Inc.", 2800.00));
        stocks.add(new Stock("AMZN", "Amazon.com", 3400.00));
        stocks.add(new Stock("TSLA", "Tesla Inc.", 700.00));
        stocks.add(new Stock("MSFT", "Microsoft Corp.", 290.00));
    }

    public void startSimulation() {
        scheduler.scheduleAtFixedRate(() -> {
            for (Stock stock : stocks) {
                stock.updatePrice();
            }
        }, 0, 2, TimeUnit.SECONDS); // Updates every 2 seconds
    }

    public void stopSimulation() {
        scheduler.shutdown();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }
        return null;
    }
}
