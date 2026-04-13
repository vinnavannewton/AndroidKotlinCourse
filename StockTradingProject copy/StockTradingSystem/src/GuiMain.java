import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GuiMain {
    private Market market;
    private User user;
    private JFrame frame;
    private DefaultTableModel marketModel;
    private JTextArea portfolioArea;
    private JLabel balanceLabel;
    private JComboBox<String> symbolSelector;
    private JTextField quantityField;

    public GuiMain() {
        market = new Market();
        user = new User(10000.00);
        market.startSimulation();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Real-Time Stock Trading System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // Attempt to set system Look and Feel (Generic cross platform or System)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Top Panel: Balance ---
        JPanel topPanel = new JPanel();
        balanceLabel = new JLabel("Balance: $10000.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(balanceLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // --- Center Panel: Market Table ---
        String[] columns = { "Symbol", "Company", "Price ($)" };
        marketModel = new DefaultTableModel(columns, 0);
        JTable marketTable = new JTable(marketModel);
        JScrollPane tableScroll = new JScrollPane(marketTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Live Market"));

        // --- Right Panel: Portfolio ---
        portfolioArea = new JTextArea(20, 20);
        portfolioArea.setEditable(false);
        JScrollPane portfolioScroll = new JScrollPane(portfolioArea);
        portfolioScroll.setBorder(BorderFactory.createTitledBorder("Your Portfolio"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScroll, portfolioScroll);
        splitPane.setResizeWeight(0.6);
        frame.add(splitPane, BorderLayout.CENTER);

        // --- Bottom Panel: Trading Controls ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Trade Actions"));

        symbolSelector = new JComboBox<>();
        for (Stock s : market.getStocks()) {
            symbolSelector.addItem(s.getSymbol());
        }

        quantityField = new JTextField(5);
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");

        bottomPanel.add(new JLabel("Stock:"));
        bottomPanel.add(symbolSelector);
        bottomPanel.add(new JLabel("Quantity:"));
        bottomPanel.add(quantityField);
        bottomPanel.add(buyButton);
        bottomPanel.add(sellButton);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        buyButton.addActionListener(e -> handleTrade(true));
        sellButton.addActionListener(e -> handleTrade(false));

        // --- UI Update Timer (Refresh every 1 second) ---
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMarketTable();
                updatePortfolioDisplay();
            }
        });
        timer.start();

        frame.setVisible(true);
    }

    private void handleTrade(boolean isBuy) {
        String symbol = (String) symbolSelector.getSelectedItem();
        String qtyText = quantityField.getText();

        try {
            int quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a positive quantity.");
                return;
            }

            Stock stock = market.getStock(symbol);
            if (stock == null)
                return;

            boolean success;
            if (isBuy) {
                success = user.buyStock(stock, quantity);
                if (!success) {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds!");
                }
            } else {
                success = user.sellStock(stock, quantity);
                if (!success) {
                    JOptionPane.showMessageDialog(frame, "Not enough shares to sell!");
                }
            }

            if (success) {
                updatePortfolioDisplay(); // Immediate update
                quantityField.setText("");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity number.");
        }
    }

    private void updateMarketTable() {
        // Simple refresh: clear and re-add.
        // In a real app, update usage `setValueAt` for better performance.
        marketModel.setRowCount(0);
        for (Stock s : market.getStocks()) {
            marketModel.addRow(new Object[] { s.getSymbol(), s.getName(), String.format("%.2f", s.getPrice()) });
        }
    }

    private void updatePortfolioDisplay() {
        balanceLabel.setText(String.format("Balance: $%.2f", user.getBalance()));

        StringBuilder sb = new StringBuilder();
        Map<String, Integer> port = user.getPortfolio();
        if (port.isEmpty()) {
            sb.append("No stocks owned.");
        } else {
            for (Map.Entry<String, Integer> entry : port.entrySet()) {
                String symbol = entry.getKey();
                int count = entry.getValue();
                Stock s = market.getStock(symbol);
                double val = s.getPrice() * count;
                sb.append(String.format("%s: %d shares\nVal: $%.2f\n\n", symbol, count, val));
            }
        }
        portfolioArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        // Run GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(GuiMain::new);
    }
}
