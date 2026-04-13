package com.stock.backend;

public class Transaction {
    public enum Type {
        BUY, SELL
    }

    private Type type;
    private String symbol;
    private int quantity;
    private double pricePerShare;
    private double totalAmount;
    private long timestamp;

    public Transaction(Type type, String symbol, int quantity, double pricePerShare) {
        this(type, symbol, quantity, pricePerShare, System.currentTimeMillis());
    }

    public Transaction(Type type, String symbol, int quantity, double pricePerShare, long timestamp) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.totalAmount = pricePerShare * quantity;
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
