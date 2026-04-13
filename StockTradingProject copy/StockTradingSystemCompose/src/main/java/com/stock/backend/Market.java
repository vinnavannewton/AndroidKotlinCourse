package com.stock.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Market {
    private final List<Stock> stocks;
    private final Map<String, Stock> stockMap; // O(1) lookup instead of O(n)
    private final List<String> sectors; // Cached, computed once
    private ScheduledExecutorService scheduler;
    private Runnable onUpdateCallback;

    public Market() {
        stocks = new ArrayList<>();
        stockMap = new HashMap<>();
        sectors = new ArrayList<>();
        initializeStocks();
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "market-sim");
            t.setDaemon(true); // Won't block JVM shutdown
            return t;
        });
    }

    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdateCallback = callback;
    }

    private void addStock(Stock stock) {
        stocks.add(stock);
        stockMap.put(stock.getSymbol().toUpperCase(), stock);
        String sector = stock.getSector();
        if (!sectors.contains(sector)) {
            sectors.add(sector);
        }
    }

    private void initializeStocks() {
        // Technology
        addStock(new Stock("AAPL", "Apple Inc.", "Technology", 189.50));
        addStock(new Stock("GOOG", "Alphabet Inc.", "Technology", 2820.00));
        addStock(new Stock("MSFT", "Microsoft Corp.", "Technology", 378.00));
        addStock(new Stock("NVDA", "NVIDIA Corp.", "Technology", 875.00));
        addStock(new Stock("META", "Meta Platforms", "Technology", 485.00));
        addStock(new Stock("AMZN", "Amazon.com", "Technology", 3420.00));

        // Automotive & Energy
        addStock(new Stock("TSLA", "Tesla Inc.", "Automotive", 245.00));
        addStock(new Stock("F", "Ford Motor Co.", "Automotive", 12.50));
        addStock(new Stock("RIVN", "Rivian Automotive", "Automotive", 18.75));

        // Finance
        addStock(new Stock("JPM", "JPMorgan Chase", "Finance", 195.00));
        addStock(new Stock("V", "Visa Inc.", "Finance", 275.00));
        addStock(new Stock("GS", "Goldman Sachs", "Finance", 385.00));

        // Healthcare
        addStock(new Stock("JNJ", "Johnson & Johnson", "Healthcare", 156.00));
        addStock(new Stock("PFE", "Pfizer Inc.", "Healthcare", 28.50));
        addStock(new Stock("UNH", "UnitedHealth Group", "Healthcare", 527.00));

        // Consumer
        addStock(new Stock("KO", "Coca-Cola Co.", "Consumer", 59.00));
        addStock(new Stock("NKE", "Nike Inc.", "Consumer", 108.00));
        addStock(new Stock("SBUX", "Starbucks Corp.", "Consumer", 98.00));

        // Crypto-adjacent / Fintech
        addStock(new Stock("COIN", "Coinbase Global", "Fintech", 185.00));
        addStock(new Stock("SQ", "Block Inc.", "Fintech", 78.00));
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
        return stockMap.get(symbol.toUpperCase()); // O(1) instead of O(n)
    }

    public List<String> getSectors() {
        return Collections.unmodifiableList(sectors); // Return cached, no allocation
    }
}
