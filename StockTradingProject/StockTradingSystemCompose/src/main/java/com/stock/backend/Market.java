package com.stock.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Market {
    private List<Stock> stocks;
    private ScheduledExecutorService scheduler;
    private Runnable onUpdateCallback;

    public Market() {
        stocks = new ArrayList<>();
        initializeStocks();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdateCallback = callback;
    }

    private void initializeStocks() {
        // Technology
        stocks.add(new Stock("AAPL", "Apple Inc.", "Technology", 189.50));
        stocks.add(new Stock("GOOG", "Alphabet Inc.", "Technology", 2820.00));
        stocks.add(new Stock("MSFT", "Microsoft Corp.", "Technology", 378.00));
        stocks.add(new Stock("NVDA", "NVIDIA Corp.", "Technology", 875.00));
        stocks.add(new Stock("META", "Meta Platforms", "Technology", 485.00));
        stocks.add(new Stock("AMZN", "Amazon.com", "Technology", 3420.00));

        // Automotive & Energy
        stocks.add(new Stock("TSLA", "Tesla Inc.", "Automotive", 245.00));
        stocks.add(new Stock("F", "Ford Motor Co.", "Automotive", 12.50));
        stocks.add(new Stock("RIVN", "Rivian Automotive", "Automotive", 18.75));

        // Finance
        stocks.add(new Stock("JPM", "JPMorgan Chase", "Finance", 195.00));
        stocks.add(new Stock("V", "Visa Inc.", "Finance", 275.00));
        stocks.add(new Stock("GS", "Goldman Sachs", "Finance", 385.00));

        // Healthcare
        stocks.add(new Stock("JNJ", "Johnson & Johnson", "Healthcare", 156.00));
        stocks.add(new Stock("PFE", "Pfizer Inc.", "Healthcare", 28.50));
        stocks.add(new Stock("UNH", "UnitedHealth Group", "Healthcare", 527.00));

        // Consumer
        stocks.add(new Stock("KO", "Coca-Cola Co.", "Consumer", 59.00));
        stocks.add(new Stock("NKE", "Nike Inc.", "Consumer", 108.00));
        stocks.add(new Stock("SBUX", "Starbucks Corp.", "Consumer", 98.00));

        // Crypto-adjacent / Fintech
        stocks.add(new Stock("COIN", "Coinbase Global", "Fintech", 185.00));
        stocks.add(new Stock("SQ", "Block Inc.", "Fintech", 78.00));
    }

    public void startSimulation() {
        scheduler.scheduleAtFixedRate(() -> {
            for (Stock stock : stocks) {
                stock.updatePrice();
            }
            if (onUpdateCallback != null) {
                onUpdateCallback.run();
            }
        }, 0, 1, TimeUnit.SECONDS);
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

    public List<String> getSectors() {
        List<String> sectors = new ArrayList<>();
        for (Stock stock : stocks) {
            if (!sectors.contains(stock.getSector())) {
                sectors.add(stock.getSector());
            }
        }
        return sectors;
    }
}
