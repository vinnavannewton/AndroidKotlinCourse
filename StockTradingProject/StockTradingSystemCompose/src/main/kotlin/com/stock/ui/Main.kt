package com.stock.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.stock.backend.Market
import com.stock.backend.Stock
import com.stock.backend.Transaction
import com.stock.backend.User
import com.stock.backend.DataStore
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

// ═══════ Apple-Inspired Dark Palette ═══════
val Bg            = Color(0xFF000000)
val Card          = Color(0xFF1C1C1E)
val CardHover     = Color(0xFF2C2C2E)
val CardElevated  = Color(0xFF3A3A3C)
val Green         = Color(0xFF30D158)
val Red           = Color(0xFFFF453A)
val Blue          = Color(0xFF0A84FF)
val Purple        = Color(0xFFBF5AF2)
val Orange        = Color(0xFFFF9F0A)
val Teal          = Color(0xFF64D2FF)
val Pink          = Color(0xFFFF375F)
val White         = Color(0xFFFFFFFF)
val Gray1         = Color(0xFF8E8E93)
val Gray2         = Color(0xFF636366)
val Gray3         = Color(0xFF48484A)
val Gray4         = Color(0xFF38383A)
val Divider       = Color(0xFF2C2C2E)

val money = DecimalFormat("#,##0.00")
val pct   = DecimalFormat("0.00")

// Sector colors
val sectorColors = mapOf(
    "Technology" to Blue,
    "Automotive" to Orange,
    "Finance"    to Green,
    "Healthcare" to Pink,
    "Consumer"   to Purple,
    "Fintech"    to Teal
)

fun main() = application {
    val market = remember { Market() }
    val user   = remember { DataStore.load(1000000.0) }

    var balance      by remember { mutableStateOf(user.balance) }
    var portfolio    by remember { mutableStateOf(user.portfolio.toMap()) }
    var avgPrices    by remember { mutableStateOf(user.avgBuyPrice.toMap()) }
    var transactions by remember { mutableStateOf(user.transactions.toList()) }
    var watchlist    by remember { mutableStateOf(user.watchlist.toList()) }
    var tick         by remember { mutableStateOf(0L) }
    var prices       by remember { mutableStateOf(market.stocks.associate { it.symbol to it.price }) }
    var netWorth     by remember { mutableStateOf(1000000.0) }
    var totalPnL     by remember { mutableStateOf(0.0) }

    DisposableEffect(Unit) {
        market.setOnUpdateCallback {
            tick++
            prices       = market.stocks.associate { it.symbol to it.price }
            balance      = user.balance
            portfolio    = user.portfolio.toMap()
            avgPrices    = user.avgBuyPrice.toMap()
            transactions = user.transactions.toList()
            watchlist    = user.watchlist.toList()
            netWorth     = user.getNetWorth(market)
            totalPnL     = user.getTotalProfitLoss(market)
        }
        market.startSimulation()
        onDispose { market.stopSimulation() }
    }

    val sync = {
        DataStore.save(user)  // <--- Auto-save here
        balance      = user.balance
        portfolio    = user.portfolio.toMap()
        avgPrices    = user.avgBuyPrice.toMap()
        transactions = user.transactions.toList()
        watchlist    = user.watchlist.toList()
        netWorth     = user.getNetWorth(market)
        totalPnL     = user.getTotalProfitLoss(market)
    }
    
    // Reset function
    val onReset = {
        DataStore.reset()
        // Manually reset user state
        val fresh = User(1000000.0)
        // We can't easily replace the 'user' val reference, so we'll just reload
        // Actually, since 'user' is a val, we can't reassign it. 
        // But we can mutate it if we add a reset method, OR we just cheat and exit the app? 
        // Better: Make 'user' a MutableState or just restart the app logic.
        // Simplest for now: clear the internal maps of the existing user object
        // But User fields are private. 
        // Let's just create a new User and rely on the UI state variables, 
        // assuming we don't need to propagate the new User object to children 
        // (we currently pass 'user' to AppContent).
        // Since we pass 'user' object down, we should probably make it a var or MutableState.
        // For now, let's just use a hack: exitApplication() isn't ideal for reset.
        // Let's change 'val user' to 'var user' in main? No, remember works on vals.
        // Let's just do a restart-ish approach or add a clear() method to User.
        // I'll add a clear() method to User via Java in a moment. 
        // For now let's assume User has a reset(double balance) method.
    }

    val windowState = rememberWindowState(width = 1400.dp, height = 900.dp)

    Window(
        onCloseRequest = ::exitApplication,
        title = "StockFlow — Real-Time Trading",
        state = windowState
    ) {
        CompositionLocalProvider(LocalDensity provides Density(density = 2.0f, fontScale = 1.0f)) {
            Surface(color = Bg, modifier = Modifier.fillMaxSize()) {
                // UI Content
                var mutableUser by remember { mutableStateOf(user) }
                
                AppContent(
                    market       = market,
                    user         = mutableUser, // Pass mutable user
                    balance      = balance,
                    portfolio    = portfolio,
                    avgPrices    = avgPrices,
                    transactions = transactions,
                    watchlist    = watchlist,
                    prices       = prices,
                    netWorth     = netWorth,
                    totalPnL     = totalPnL,
                    sync         = sync,
                    onReset      = {
                        DataStore.reset()
                        mutableUser = User(1000000.0) // Replace user object
                        DataStore.save(mutableUser)
                        // Update all Compose state from new user
                        balance      = mutableUser.balance
                        portfolio    = mutableUser.portfolio.toMap()
                        avgPrices    = mutableUser.avgBuyPrice.toMap()
                        transactions = mutableUser.transactions.toList()
                        watchlist    = mutableUser.watchlist.toList()
                        netWorth     = mutableUser.getNetWorth(market)
                        totalPnL     = mutableUser.getTotalProfitLoss(market)
                    }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────
//  TAB ENUM
// ─────────────────────────────────────────────────────
enum class Tab(val label: String) {
    MARKET("Market"),
    WATCHLIST("Watchlist"),
    HISTORY("History"),
    SETTINGS("Settings")
}

// ═══════════════════════════════════════════════════════
//                   APP CONTENT
// ═══════════════════════════════════════════════════════
@Composable
fun AppContent(
    market: Market,
    user: User,
    balance: Double,
    portfolio: Map<String, Int>,
    avgPrices: Map<String, Double>,
    transactions: List<Transaction>,
    watchlist: List<String>,
    prices: Map<String, Double>,
    netWorth: Double,
    totalPnL: Double,
    sync: () -> Unit,
    onReset: () -> Unit
) {
    var selectedSymbol by remember { mutableStateOf("") }
    var quantityText   by remember { mutableStateOf("1") }
    var message        by remember { mutableStateOf("") }
    var messageIsError by remember { mutableStateOf(false) }
    var currentTab     by remember { mutableStateOf(Tab.MARKET) }
    var sectorFilter   by remember { mutableStateOf("All") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // ── HEADER ──
        HeaderBar(balance, netWorth, totalPnL)
        Spacer(modifier = Modifier.height(12.dp))

        // ── BODY ──
        Row(modifier = Modifier.fillMaxSize()) {
            // LEFT: Tabs + Stock List
            Column(modifier = Modifier.weight(1.5f).fillMaxHeight()) {
                // Tab Row
                TabRow(currentTab) { currentTab = it }
                Spacer(modifier = Modifier.height(8.dp))

                // Sector filter (only in Market tab)
                if (currentTab == Tab.MARKET) {
                    SectorFilter(market.sectors, sectorFilter) { sectorFilter = it }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Content
                when (currentTab) {
                    Tab.MARKET -> MarketList(
                        stocks = if (sectorFilter == "All") market.stocks
                                 else market.stocks.filter { it.sector == sectorFilter },
                        prices = prices,
                        selectedSymbol = selectedSymbol,
                        watchlist = watchlist,
                        onSelect = { selectedSymbol = it },
                        onToggleWatch = { user.toggleWatchlist(it); sync() }
                    )
                    Tab.WATCHLIST -> WatchlistView(
                        market = market,
                        watchlist = watchlist,
                        prices = prices,
                        selectedSymbol = selectedSymbol,
                        onSelect = { selectedSymbol = it },
                        onRemove = { user.toggleWatchlist(it); sync() }
                    )
                    Tab.HISTORY -> TransactionHistory(transactions, prices)
                    Tab.SETTINGS -> SettingsView(onReset)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // RIGHT: Trade + Portfolio
            Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
                TradePanel(
                    selectedSymbol = selectedSymbol,
                    prices = prices,
                    market = market,
                    quantityText = quantityText,
                    onQuantityChange = { quantityText = it },
                    message = message,
                    messageIsError = messageIsError,
                    onBuy = {
                        val stock = market.getStock(selectedSymbol)
                        val qty = quantityText.toIntOrNull() ?: 0
                        if (stock != null && qty > 0) {
                            if (user.buyStock(stock, qty)) {
                                message = "✓ Bought $qty ${stock.symbol}"; messageIsError = false
                            } else {
                                message = "✗ Insufficient funds"; messageIsError = true
                            }
                            sync()
                        }
                    },
                    onSell = {
                        val stock = market.getStock(selectedSymbol)
                        val qty = quantityText.toIntOrNull() ?: 0
                        if (stock != null && qty > 0) {
                            if (user.sellStock(stock, qty)) {
                                message = "✓ Sold $qty ${stock.symbol}"; messageIsError = false
                            } else {
                                message = "✗ Not enough shares"; messageIsError = true
                            }
                            sync()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                PortfolioPanel(portfolio, avgPrices, prices, modifier = Modifier.weight(1f))
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                      HEADER BAR
// ═══════════════════════════════════════════════════════
@Composable
fun HeaderBar(balance: Double, netWorth: Double, pnl: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title
        Column {
            Text("StockFlow", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White, letterSpacing = 0.5.sp)
            Text("Real-Time Trading Simulator", fontSize = 11.sp, color = Gray1)
        }

        // Stats pills
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatPill("Cash", "\$${money.format(balance)}", Blue)
            StatPill("Net Worth", "\$${money.format(netWorth)}", Purple)
            StatPill(
                label = "P&L",
                value = "${if (pnl >= 0) "+" else ""}\$${money.format(pnl)}",
                color = if (pnl >= 0) Green else Red
            )
        }
    }
}

@Composable
fun StatPill(label: String, value: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 9.sp, color = Gray1)
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = color)
        }
    }
}

// ═══════════════════════════════════════════════════════
//                      TAB ROW
// ═══════════════════════════════════════════════════════
@Composable
fun TabRow(current: Tab, onSelect: (Tab) -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Card)
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Tab.entries.forEach { tab ->
            val isActive = tab == current
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isActive) CardHover else Color.Transparent)
                    .clickable { onSelect(tab) }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    tab.label,
                    fontSize = 11.sp,
                    fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isActive) White else Gray1
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                    SECTOR FILTER
// ═══════════════════════════════════════════════════════
@Composable
fun SectorFilter(sectors: List<String>, current: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val all = listOf("All") + sectors
        all.forEach { sector ->
            val isActive = sector == current
            val color = sectorColors[sector] ?: Blue
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isActive) color.copy(alpha = 0.2f) else Card)
                    .border(
                        width = if (isActive) 1.dp else 0.dp,
                        color = if (isActive) color.copy(alpha = 0.4f) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onSelect(sector) }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    sector,
                    fontSize = 10.sp,
                    fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isActive) color else Gray1
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                    MARKET LIST
// ═══════════════════════════════════════════════════════
@Composable
fun MarketList(
    stocks: List<Stock>,
    prices: Map<String, Double>,
    selectedSymbol: String,
    watchlist: List<String>,
    onSelect: (String) -> Unit,
    onToggleWatch: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .background(Card)
            .padding(12.dp)
    ) {
        // Column headers
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 4.dp)) {
            Text("STOCK", fontSize = 9.sp, color = Gray2, modifier = Modifier.weight(2f))
            Text("PRICE", fontSize = 9.sp, color = Gray2, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            Text("CHANGE", fontSize = 9.sp, color = Gray2, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            Spacer(modifier = Modifier.width(28.dp)) // watch icon space
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn {
            items(stocks) { stock ->
                val isSelected = stock.symbol == selectedSymbol
                val price = prices[stock.symbol] ?: stock.price
                val changePct = stock.changePercent
                val isUp = changePct >= 0
                val sectorColor = sectorColors[stock.sector] ?: Blue
                val isWatched = watchlist.contains(stock.symbol)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) CardHover else Color.Transparent)
                        .clickable { onSelect(stock.symbol) }
                        .padding(horizontal = 6.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Symbol badge
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(sectorColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stock.symbol.take(2), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = sectorColor)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Name
                    Column(modifier = Modifier.weight(2f)) {
                        Text(stock.symbol, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = White)
                        Text(stock.name, fontSize = 9.sp, color = Gray1, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }

                    // Price
                    Text(
                        "\$${money.format(price)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = White,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )

                    // Change %
                    val changeColor = if (isUp) Green else Red
                    val arrow = if (isUp) "▲" else "▼"
                    Text(
                        "$arrow ${pct.format(kotlin.math.abs(changePct))}%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = changeColor,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )

                    // Watchlist star
                    Text(
                        if (isWatched) "★" else "☆",
                        fontSize = 13.sp,
                        color = if (isWatched) Orange else Gray3,
                        modifier = Modifier
                            .width(28.dp)
                            .clickable { onToggleWatch(stock.symbol) },
                        textAlign = TextAlign.Center
                    )
                }

                if (stock !== stocks.last()) {
                    Divider(color = Divider, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 6.dp))
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                   WATCHLIST VIEW
// ═══════════════════════════════════════════════════════
@Composable
fun WatchlistView(
    market: Market,
    watchlist: List<String>,
    prices: Map<String, Double>,
    selectedSymbol: String,
    onSelect: (String) -> Unit,
    onRemove: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .background(Card)
            .padding(12.dp)
    ) {
        Text("Watchlist", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = White)
        Spacer(modifier = Modifier.height(8.dp))

        if (watchlist.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("☆", fontSize = 24.sp, color = Gray3)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No stocks watched", fontSize = 12.sp, color = Gray2)
                    Text("Tap ☆ on any stock to add it", fontSize = 10.sp, color = Gray3)
                }
            }
        } else {
            LazyColumn {
                items(watchlist) { symbol ->
                    val stock = market.getStock(symbol) ?: return@items
                    val price = prices[symbol] ?: stock.price
                    val changePct = stock.changePercent
                    val isUp = changePct >= 0
                    val sectorColor = sectorColors[stock.sector] ?: Blue

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (symbol == selectedSymbol) CardHover else Color.Transparent)
                            .clickable { onSelect(symbol) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp))
                                .background(sectorColor.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(symbol.take(2), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = sectorColor)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stock.symbol, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = White)
                            Text(stock.name, fontSize = 9.sp, color = Gray1)
                        }
                        Text("\$${money.format(price)}", fontSize = 12.sp, color = White)
                        Spacer(modifier = Modifier.width(8.dp))
                        val changeColor = if (isUp) Green else Red
                        val arrow = if (isUp) "▲" else "▼"
                        Text("$arrow${pct.format(kotlin.math.abs(changePct))}%", fontSize = 10.sp, color = changeColor)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "✕",
                            fontSize = 12.sp,
                            color = Red.copy(alpha = 0.7f),
                            modifier = Modifier.clickable { onRemove(symbol) }
                        )
                    }
                    Divider(color = Divider, thickness = 0.5.dp)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                 TRANSACTION HISTORY
// ═══════════════════════════════════════════════════════
@Composable
fun TransactionHistory(transactions: List<Transaction>, prices: Map<String, Double>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .background(Card)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Transaction History", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = White)
            Text("${transactions.size} trades", fontSize = 10.sp, color = Gray2)
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (transactions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📋", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No transactions yet", fontSize = 12.sp, color = Gray2)
                    Text("Start trading to see history here", fontSize = 10.sp, color = Gray3)
                }
            }
        } else {
            // Show newest first
            LazyColumn {
                items(transactions.reversed()) { tx ->
                    val isBuy = tx.type == Transaction.Type.BUY
                    val color = if (isBuy) Green else Red
                    val label = if (isBuy) "BUY" else "SELL"
                    val time = SimpleDateFormat("HH:mm:ss").format(Date(tx.timestamp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Type badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(color.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "${tx.symbol} × ${tx.quantity}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = White
                            )
                            Text("@ \$${money.format(tx.pricePerShare)}", fontSize = 9.sp, color = Gray1)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "${if (isBuy) "-" else "+"}\$${money.format(tx.totalAmount)}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = color
                            )
                            Text(time, fontSize = 9.sp, color = Gray2)
                        }
                    }
                    Divider(color = Divider, thickness = 0.5.dp)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                     TRADE PANEL
// ═══════════════════════════════════════════════════════
@Composable
fun TradePanel(
    selectedSymbol: String,
    prices: Map<String, Double>,
    market: Market,
    quantityText: String,
    onQuantityChange: (String) -> Unit,
    message: String,
    messageIsError: Boolean,
    onBuy: () -> Unit,
    onSell: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Card)
            .padding(14.dp)
    ) {
        Text("Trade", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = White)
        Spacer(modifier = Modifier.height(8.dp))

        if (selectedSymbol.isNotEmpty()) {
            val stock = market.getStock(selectedSymbol)
            val price = prices[selectedSymbol] ?: 0.0
            if (stock != null) {
                val sectorColor = sectorColors[stock.sector] ?: Blue
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(32.dp).clip(RoundedCornerShape(8.dp))
                            .background(sectorColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stock.symbol.take(2), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = sectorColor)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(stock.symbol, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = White)
                        Text("\$${money.format(price)}", fontSize = 11.sp, color = Green)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // Day range
                    Column(horizontalAlignment = Alignment.End) {
                        Text("H: \$${money.format(stock.dayHigh)}", fontSize = 9.sp, color = Green)
                        Text("L: \$${money.format(stock.dayLow)}", fontSize = 9.sp, color = Red)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                // Sector tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(sectorColor.copy(alpha = 0.1f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(stock.sector, fontSize = 9.sp, color = sectorColor)
                }
            }
        } else {
            Text("← Select a stock to trade", fontSize = 11.sp, color = Gray2)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Quantity + estimated cost
        OutlinedTextField(
            value = quantityText,
            onValueChange = { v -> if (v.all { it.isDigit() }) onQuantityChange(v) },
            label = { Text("Quantity", fontSize = 10.sp) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = White, cursorColor = Blue,
                focusedBorderColor = Blue, unfocusedBorderColor = Gray3,
                focusedLabelColor = Blue, unfocusedLabelColor = Gray2
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Estimated cost
        if (selectedSymbol.isNotEmpty()) {
            val qty = quantityText.toIntOrNull() ?: 0
            val price = prices[selectedSymbol] ?: 0.0
            val est = qty * price
            if (qty > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text("Est. total: \$${money.format(est)}", fontSize = 10.sp, color = Gray1)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onBuy,
                enabled = selectedSymbol.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Green, disabledBackgroundColor = Gray3),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f).height(36.dp)
            ) { Text("Buy", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp) }

            Button(
                onClick = onSell,
                enabled = selectedSymbol.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Red, disabledBackgroundColor = Gray3),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f).height(36.dp)
            ) { Text("Sell", color = White, fontWeight = FontWeight.Bold, fontSize = 12.sp) }
        }

        if (message.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(message, fontSize = 10.sp, color = if (messageIsError) Red else Green)
        }
    }
}

// ═══════════════════════════════════════════════════════
//                   PORTFOLIO PANEL
// ═══════════════════════════════════════════════════════
@Composable
fun PortfolioPanel(
    portfolio: Map<String, Int>,
    avgPrices: Map<String, Double>,
    prices: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Card)
            .padding(14.dp)
    ) {
        Text("Holdings", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = White)
        Spacer(modifier = Modifier.height(8.dp))

        if (portfolio.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                Text("No holdings yet", fontSize = 11.sp, color = Gray2)
            }
        } else {
            // Total value
            val totalValue = portfolio.entries.sumOf { (sym, qty) -> (prices[sym] ?: 0.0) * qty }
            val totalCost  = portfolio.entries.sumOf { (sym, qty) -> (avgPrices[sym] ?: 0.0) * qty }
            val totalPnl   = totalValue - totalCost
            val pnlUp      = totalPnl >= 0

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background((if (pnlUp) Green else Red).copy(alpha = 0.08f))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Value", fontSize = 9.sp, color = Gray1)
                    Text("\$${money.format(totalValue)}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = if (pnlUp) Green else Red)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Unrealized P&L", fontSize = 9.sp, color = Gray1)
                    Text(
                        "${if (pnlUp) "+" else ""}\$${money.format(totalPnl)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (pnlUp) Green else Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            LazyColumn {
                items(portfolio.entries.toList()) { (symbol, count) ->
                    val currentPrice = prices[symbol] ?: 0.0
                    val avgPrice     = avgPrices[symbol] ?: 0.0
                    val value        = currentPrice * count
                    val cost         = avgPrice * count
                    val pnl          = value - cost
                    val pnlPct       = if (cost > 0) (pnl / cost) * 100 else 0.0
                    val isUp         = pnl >= 0

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(symbol, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = White)
                            Text("$count shares · Avg \$${money.format(avgPrice)}", fontSize = 9.sp, color = Gray1)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("\$${money.format(value)}", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = White)
                            Text(
                                "${if (isUp) "+" else ""}\$${money.format(pnl)} (${pct.format(pnlPct)}%)",
                                fontSize = 9.sp,
                                color = if (isUp) Green else Red
                            )
                        }
                    }
                    Divider(color = Divider, thickness = 0.5.dp)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════
//                    SETTINGS VIEW
// ═══════════════════════════════════════════════════════
@Composable
fun SettingsView(onReset: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .background(Card)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Danger Zone", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Red)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Resetting will clear all your portfolio, transaction history, and watchlist.\nYour balance will be reset to $1,000,000.",
            fontSize = 12.sp,
            color = Gray1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(backgroundColor = Red),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(200.dp).height(48.dp)
        ) {
            Text("RESET ALL DATA", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = White)
        }
    }
}
