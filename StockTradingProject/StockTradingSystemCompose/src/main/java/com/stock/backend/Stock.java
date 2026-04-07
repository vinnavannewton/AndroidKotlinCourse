package com.stock.backend;

import java.util.Random;

public class Stock {
    private String symbol;
    private String name;
    private String sector;
    private double price;
    private double previousPrice;
    private double openPrice;
    private double dayHigh;
    private double dayLow;
    private Random random;

    public Stock(String symbol, String name, String sector, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.sector = sector;
        this.price = initialPrice;
        this.previousPrice = initialPrice;
        this.openPrice = initialPrice;
        this.dayHigh = initialPrice;
        this.dayLow = initialPrice;
        this.random = new Random();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public double getPrice() {
        return price;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public double getChangePercent() {
        if (openPrice == 0)
            return 0;
        return ((price - openPrice) / openPrice) * 100;
    }

    public double getChange() {
        return price - openPrice;
    }

    public void updatePrice() {
        this.previousPrice = this.price;
        double changePercent = (random.nextDouble() * 0.04) - 0.02;
        this.price += this.price * changePercent;
        if (this.price < 0.01)
            this.price = 0.01;
        if (this.price > this.dayHigh)
            this.dayHigh = this.price;
        if (this.price < this.dayLow)
            this.dayLow = this.price;
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-20s | $%.2f", symbol, name, price);
    }
}
