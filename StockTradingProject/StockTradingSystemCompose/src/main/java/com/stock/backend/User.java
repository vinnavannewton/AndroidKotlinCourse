package com.stock.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private double balance;
    private double initialBalance;
    private Map<String, Integer> portfolio;
    private Map<String, Double> avgBuyPrice;
    private List<Transaction> transactions;
    private List<String> watchlist;

    // Fresh user
    public User(double initialBalance) {
        this.balance = initialBalance;
        this.initialBalance = initialBalance;
        this.portfolio = new HashMap<>();
        this.avgBuyPrice = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.watchlist = new ArrayList<>();
    }

    // Restore from saved state
    public User(double balance, double initialBalance,
            Map<String, Integer> portfolio, Map<String, Double> avgBuyPrice,
            List<Transaction> transactions, List<String> watchlist) {
        this.balance = balance;
        this.initialBalance = initialBalance;
        this.portfolio = new HashMap<>(portfolio);
        this.avgBuyPrice = new HashMap<>(avgBuyPrice);
        this.transactions = new ArrayList<>(transactions);
        this.watchlist = new ArrayList<>(watchlist);
    }

    public double getBalance() {
        return balance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public Map<String, Integer> getPortfolio() {
        return portfolio;
    }

    public Map<String, Double> getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<String> getWatchlist() {
        return watchlist;
    }

    public boolean buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (cost > balance)
            return false;
        balance -= cost;

        int existingQty = portfolio.getOrDefault(stock.getSymbol(), 0);
        double existingCost = avgBuyPrice.getOrDefault(stock.getSymbol(), 0.0) * existingQty;
        double newTotalCost = existingCost + cost;
        int newTotalQty = existingQty + quantity;
        avgBuyPrice.put(stock.getSymbol(), newTotalCost / newTotalQty);

        portfolio.put(stock.getSymbol(), newTotalQty);
        transactions.add(new Transaction(Transaction.Type.BUY, stock.getSymbol(), quantity, stock.getPrice()));
        return true;
    }

    public boolean sellStock(Stock stock, int quantity) {
        String symbol = stock.getSymbol();
        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) < quantity)
            return false;

        balance += stock.getPrice() * quantity;

        int currentQuantity = portfolio.get(symbol);
        if (currentQuantity == quantity) {
            portfolio.remove(symbol);
            avgBuyPrice.remove(symbol);
        } else {
            portfolio.put(symbol, currentQuantity - quantity);
        }

        transactions.add(new Transaction(Transaction.Type.SELL, symbol, quantity, stock.getPrice()));
        return true;
    }

    public void toggleWatchlist(String symbol) {
        if (watchlist.contains(symbol))
            watchlist.remove(symbol);
        else
            watchlist.add(symbol);
    }

    public boolean isInWatchlist(String symbol) {
        return watchlist.contains(symbol);
    }

    public double getNetWorth(Market market) {
        double worth = balance;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            Stock stock = market.getStock(entry.getKey());
            if (stock != null)
                worth += stock.getPrice() * entry.getValue();
        }
        return worth;
    }

    public double getTotalProfitLoss(Market market) {
        return getNetWorth(market) - initialBalance;
    }
}
