package com.stock.backend;

import java.io.*;
import java.util.*;

/**
 * Simple file-based persistence for user data.
 * Saves to ~/.stockflow/userdata.txt
 */
public class DataStore {
    private static final String DIR = System.getProperty("user.home") + "/.stockflow";
    private static final String FILE = DIR + "/userdata.txt";

    public static void save(User user) {
        try {
            new File(DIR).mkdirs();
            // Write to temp file, then rename for atomicity (prevents corruption on crash)
            File tmpFile = new File(FILE + ".tmp");
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)))) {
                // Balance
                pw.println("BALANCE=" + user.getBalance());
                pw.println("INITIAL_BALANCE=" + user.getInitialBalance());

                // Portfolio: symbol:qty
                StringBuilder portSb = new StringBuilder(64);
                for (Map.Entry<String, Integer> e : user.getPortfolio().entrySet()) {
                    if (portSb.length() > 0)
                        portSb.append(',');
                    portSb.append(e.getKey()).append(':').append(e.getValue());
                }
                pw.println("PORTFOLIO=" + portSb);

                // Average buy prices: symbol:price
                StringBuilder avgSb = new StringBuilder(64);
                for (Map.Entry<String, Double> e : user.getAvgBuyPrice().entrySet()) {
                    if (avgSb.length() > 0)
                        avgSb.append(',');
                    avgSb.append(e.getKey()).append(':').append(e.getValue());
                }
                pw.println("AVG_PRICES=" + avgSb);

                // Watchlist: symbol,symbol,...
                StringBuilder wlSb = new StringBuilder(32);
                for (String s : user.getWatchlist()) {
                    if (wlSb.length() > 0)
                        wlSb.append(',');
                    wlSb.append(s);
                }
                pw.println("WATCHLIST=" + wlSb);

                // Transactions: type|symbol|qty|price|timestamp
                for (Transaction tx : user.getTransactions()) {
                    pw.println("TX=" + tx.getType().name() + '|' + tx.getSymbol() + '|'
                            + tx.getQuantity() + '|' + tx.getPricePerShare() + '|' + tx.getTimestamp());
                }
            }
            // Atomic rename
            File target = new File(FILE);
            tmpFile.renameTo(target);
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    public static User load(double defaultBalance) {
        File file = new File(FILE);
        if (!file.exists()) {
            return new User(defaultBalance);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file), 4096)) {
            double balance = defaultBalance;
            double initialBalance = defaultBalance;
            Map<String, Integer> portfolio = new HashMap<>(16);
            Map<String, Double> avgPrices = new HashMap<>(16);
            List<String> watchlist = new ArrayList<>(8);
            List<Transaction> transactions = new ArrayList<>(32);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("BALANCE=")) {
                    balance = Double.parseDouble(line.substring(8));
                } else if (line.startsWith("INITIAL_BALANCE=")) {
                    initialBalance = Double.parseDouble(line.substring(16));
                } else if (line.startsWith("PORTFOLIO=")) {
                    String data = line.substring(10);
                    if (!data.isEmpty()) {
                        for (String pair : data.split(",")) {
                            String[] parts = pair.split(":");
                            portfolio.put(parts[0], Integer.parseInt(parts[1]));
                        }
                    }
                } else if (line.startsWith("AVG_PRICES=")) {
                    String data = line.substring(11);
                    if (!data.isEmpty()) {
                        for (String pair : data.split(",")) {
                            String[] parts = pair.split(":");
                            avgPrices.put(parts[0], Double.parseDouble(parts[1]));
                        }
                    }
                } else if (line.startsWith("WATCHLIST=")) {
                    String data = line.substring(10);
                    if (!data.isEmpty()) {
                        Collections.addAll(watchlist, data.split(","));
                    }
                } else if (line.startsWith("TX=")) {
                    String data = line.substring(3);
                    String[] parts = data.split("\\|");
                    Transaction.Type type = Transaction.Type.valueOf(parts[0]);
                    String symbol = parts[1];
                    int qty = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    long timestamp = Long.parseLong(parts[4]);
                    transactions.add(new Transaction(type, symbol, qty, price, timestamp));
                }
            }

            return new User(balance, initialBalance, portfolio, avgPrices, transactions, watchlist);
        } catch (Exception e) {
            System.err.println("Failed to load data: " + e.getMessage());
            return new User(defaultBalance);
        }
    }

    public static void reset() {
        File file = new File(FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
