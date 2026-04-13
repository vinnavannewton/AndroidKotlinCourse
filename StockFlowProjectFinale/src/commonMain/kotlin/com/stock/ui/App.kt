package com.stock.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stock.model.Market
import com.stock.model.Stock
import com.stock.model.Transaction
import com.stock.model.UiState
import com.stock.model.User
import com.stock.storage.DataStore
import com.stock.util.formatMoney
import com.stock.util.formatPercent
import com.stock.util.formatSignedMoney
import kotlinx.coroutines.flow.MutableStateFlow

private val Background = Color(0xFF0B0F17)
private val Surface1 = Color(0xFF111827)
private val Surface2 = Color(0xFF1F2937)
private val Accent = Color(0xFF60A5FA)
private val Good = Color(0xFF34D399)
private val Bad = Color(0xFFF87171)
private val Text1 = Color(0xFFF9FAFB)
private val Text2 = Color(0xFF9CA3AF)
private val Border = Color(0xFF334155)

private val sectorsColor = mapOf(
    "Technology" to Accent,
    "Automotive" to Color(0xFFF59E0B),
    "Finance" to Good,
    "Healthcare" to Color(0xFFF472B6),
    "Consumer" to Color(0xFFA78BFA),
    "Fintech" to Color(0xFF22D3EE),
)

private enum class Section { Market, Watchlist, History, Settings }

private fun formatTime(epochMillis: Long): String {
    val totalSeconds = epochMillis / 1000
    val hours = (totalSeconds / 3600) % 24
    val minutes = (totalSeconds / 60) % 60
    val seconds = totalSeconds % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}

@Composable
fun StockFlowApp() {
    val market = remember { Market() }
    var user by remember { mutableStateOf(DataStore.load(1_000_000.0)) }
    val uiStateFlow = remember { MutableStateFlow(UiState.from(user, market)) }
    val uiState by uiStateFlow.collectAsState()

    DisposableEffect(Unit) {
        market.setOnUpdateCallback {
            uiStateFlow.value = UiState.from(user, market)
        }
        market.startSimulation()
        onDispose { market.stopSimulation() }
    }

    val sync: () -> Unit = {
        DataStore.save(user)
        uiStateFlow.value = UiState.from(user, market)
    }

    val reset: () -> Unit = {
        DataStore.reset()
        user = User(1_000_000.0)
        DataStore.save(user)
        uiStateFlow.value = UiState.from(user, market)
    }

    MaterialTheme {
        Surface(color = Background, modifier = Modifier.fillMaxSize()) {
            AppContent(
                market = market,
                user = user,
                state = uiState,
                sync = sync,
                onReset = reset,
            )
        }
    }
}

@Composable
fun AppContent(
    market: Market,
    user: User,
    state: UiState,
    sync: () -> Unit,
    onReset: () -> Unit,
) {
    var selectedSymbol by remember { mutableStateOf("") }
    var quantityText by remember { mutableStateOf("1") }
    var message by remember { mutableStateOf("") }
    var messageIsError by remember { mutableStateOf(false) }
    var currentSection by remember { mutableStateOf(Section.Market) }
    var sectorFilter by remember { mutableStateOf("All") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        val compact = maxWidth < 840.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Surface1)
                .padding(14.dp)
        ) {
            HeaderBar(state.balance, state.netWorth, state.totalPnL)
            Spacer(Modifier.height(12.dp))

            if (compact) {
                MobileLayout(
                    market = market,
                    user = user,
                    state = state,
                    sync = sync,
                    onReset = onReset,
                    selectedSymbol = selectedSymbol,
                    onSelectSymbol = { selectedSymbol = it },
                    quantityText = quantityText,
                    onQuantityChange = { quantityText = it },
                    message = message,
                    messageIsError = messageIsError,
                    onMessage = { text, isError -> message = text; messageIsError = isError },
                    currentSection = currentSection,
                    onSectionChange = { currentSection = it },
                    sectorFilter = sectorFilter,
                    onSectorFilterChange = { sectorFilter = it },
                )
            } else {
                DesktopLayout(
                    market = market,
                    user = user,
                    state = state,
                    sync = sync,
                    onReset = onReset,
                    selectedSymbol = selectedSymbol,
                    onSelectSymbol = { selectedSymbol = it },
                    quantityText = quantityText,
                    onQuantityChange = { quantityText = it },
                    message = message,
                    messageIsError = messageIsError,
                    onMessage = { text, isError -> message = text; messageIsError = isError },
                    currentSection = currentSection,
                    onSectionChange = { currentSection = it },
                    sectorFilter = sectorFilter,
                    onSectorFilterChange = { sectorFilter = it },
                )
            }
        }
    }
}

@Composable
private fun DesktopLayout(
    market: Market,
    user: User,
    state: UiState,
    sync: () -> Unit,
    onReset: () -> Unit,
    selectedSymbol: String,
    onSelectSymbol: (String) -> Unit,
    quantityText: String,
    onQuantityChange: (String) -> Unit,
    message: String,
    messageIsError: Boolean,
    onMessage: (String, Boolean) -> Unit,
    currentSection: Section,
    onSectionChange: (Section) -> Unit,
    sectorFilter: String,
    onSectorFilterChange: (String) -> Unit,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1.4f).fillMaxSize()) {
            TopTabs(currentSection, onSectionChange)
            Spacer(Modifier.height(8.dp))

            when (currentSection) {
                Section.Market -> {
                    SectorFilter(market.sectors, sectorFilter, onSectorFilterChange)
                    Spacer(Modifier.height(8.dp))
                    val filtered = if (sectorFilter == "All") market.stocks
                    else market.stocks.filter { it.sector == sectorFilter }
                    MarketList(
                        stocks = filtered,
                        prices = state.prices,
                        selectedSymbol = selectedSymbol,
                        watchlist = state.watchlist,
                        onSelect = onSelectSymbol,
                        onToggleWatch = {
                            user.toggleWatchlist(it)
                            sync()
                        },
                        modifier = Modifier.weight(1f),
                    )
                }

                Section.Watchlist -> WatchlistView(
                    market = market,
                    watchlist = state.watchlist,
                    prices = state.prices,
                    selectedSymbol = selectedSymbol,
                    onSelect = onSelectSymbol,
                    onRemove = {
                        user.toggleWatchlist(it)
                        sync()
                    },
                    modifier = Modifier.weight(1f),
                )

                Section.History -> TransactionHistory(
                    transactions = state.transactions,
                    prices = state.prices,
                    modifier = Modifier.weight(1f),
                )

                Section.Settings -> SettingsView(onReset = onReset, modifier = Modifier.weight(1f))
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f).fillMaxSize()) {
            TradePanel(
                selectedSymbol = selectedSymbol,
                prices = state.prices,
                market = market,
                quantityText = quantityText,
                onQuantityChange = onQuantityChange,
                message = message,
                messageIsError = messageIsError,
                onBuy = { handleTrade(true, user, market, selectedSymbol, quantityText, sync, onMessage) },
                onSell = { handleTrade(false, user, market, selectedSymbol, quantityText, sync, onMessage) },
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.height(12.dp))
            PortfolioPanel(
                portfolio = state.portfolio,
                avgPrices = state.avgPrices,
                prices = state.prices,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MobileLayout(
    market: Market,
    user: User,
    state: UiState,
    sync: () -> Unit,
    onReset: () -> Unit,
    selectedSymbol: String,
    onSelectSymbol: (String) -> Unit,
    quantityText: String,
    onQuantityChange: (String) -> Unit,
    message: String,
    messageIsError: Boolean,
    onMessage: (String, Boolean) -> Unit,
    currentSection: Section,
    onSectionChange: (Section) -> Unit,
    sectorFilter: String,
    onSectorFilterChange: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopTabs(currentSection, onSectionChange)
        Spacer(Modifier.height(8.dp))

        when (currentSection) {
            Section.Market -> {
                SectorFilter(market.sectors, sectorFilter, onSectorFilterChange)
                Spacer(Modifier.height(8.dp))
                val filtered = remember(sectorFilter, market.stocks) {
                    if (sectorFilter == "All") market.stocks else market.stocks.filter { it.sector == sectorFilter }
                }
                MarketList(
                    stocks = filtered,
                    prices = state.prices,
                    selectedSymbol = selectedSymbol,
                    watchlist = state.watchlist,
                    onSelect = onSelectSymbol,
                    onToggleWatch = {
                        user.toggleWatchlist(it)
                        sync()
                    },
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.height(8.dp))
                TradePanel(
                    selectedSymbol = selectedSymbol,
                    prices = state.prices,
                    market = market,
                    quantityText = quantityText,
                    onQuantityChange = onQuantityChange,
                    message = message,
                    messageIsError = messageIsError,
                    onBuy = { handleTrade(true, user, market, selectedSymbol, quantityText, sync, onMessage) },
                    onSell = { handleTrade(false, user, market, selectedSymbol, quantityText, sync, onMessage) },
                    modifier = Modifier.heightIn(min = 250.dp),
                )
                Spacer(Modifier.height(8.dp))
                PortfolioPanel(
                    portfolio = state.portfolio,
                    avgPrices = state.avgPrices,
                    prices = state.prices,
                    modifier = Modifier.heightIn(min = 240.dp),
                )
            }

            Section.Watchlist -> {
                WatchlistView(
                    market = market,
                    watchlist = state.watchlist,
                    prices = state.prices,
                    selectedSymbol = selectedSymbol,
                    onSelect = onSelectSymbol,
                    onRemove = {
                        user.toggleWatchlist(it)
                        sync()
                    },
                    modifier = Modifier.weight(1f),
                )
                Spacer(Modifier.height(8.dp))
                TradePanel(
                    selectedSymbol = selectedSymbol,
                    prices = state.prices,
                    market = market,
                    quantityText = quantityText,
                    onQuantityChange = onQuantityChange,
                    message = message,
                    messageIsError = messageIsError,
                    onBuy = { handleTrade(true, user, market, selectedSymbol, quantityText, sync, onMessage) },
                    onSell = { handleTrade(false, user, market, selectedSymbol, quantityText, sync, onMessage) },
                    modifier = Modifier.heightIn(min = 250.dp),
                )
            }

            Section.History -> TransactionHistory(
                transactions = state.transactions,
                prices = state.prices,
                modifier = Modifier.weight(1f),
            )

            Section.Settings -> SettingsView(onReset = onReset, modifier = Modifier.weight(1f))
        }
    }
}

private fun handleTrade(
    isBuy: Boolean,
    user: User,
    market: Market,
    selectedSymbol: String,
    quantityText: String,
    sync: () -> Unit,
    onMessage: (String, Boolean) -> Unit,
) {
    val qty = quantityText.toIntOrNull()
    if (selectedSymbol.isBlank()) {
        onMessage("Select a stock first.", true)
        return
    }
    if (qty == null || qty <= 0) {
        onMessage("Enter a valid quantity.", true)
        return
    }
    val stock = market.getStock(selectedSymbol)
    if (stock == null) {
        onMessage("Stock not found.", true)
        return
    }

    val ok = if (isBuy) user.buyStock(stock, qty) else user.sellStock(stock, qty)
    if (ok) {
        onMessage(
            if (isBuy) "Bought $qty ${stock.symbol}." else "Sold $qty ${stock.symbol}.",
            false,
        )
        sync()
    } else {
        onMessage(
            if (isBuy) "Not enough cash." else "Not enough shares.",
            true,
        )
    }
}

@Composable
private fun TopTabs(currentSection: Section, onSectionChange: (Section) -> Unit) {
    TabRow(selectedTabIndex = currentSection.ordinal, backgroundColor = Surface2, contentColor = Text1) {
        Section.entries.forEachIndexed { index, section ->
            Tab(
                selected = currentSection == section,
                onClick = { onSectionChange(section) },
                text = { Text(section.name.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
            )
        }
    }
}

@Composable
private fun HeaderBar(balance: Double, netWorth: Double, pnl: Double) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        StatPill("Balance", "\$${formatMoney(balance)}", Accent, Modifier.weight(1f))
        StatPill("Net Worth", "\$${formatMoney(netWorth)}", Good, Modifier.weight(1f))
        StatPill(
            "P&L",
            if (pnl >= 0) "+\$${formatMoney(pnl)}" else "-\$${formatMoney(kotlin.math.abs(pnl))}",
            if (pnl >= 0) Good else Bad,
            Modifier.weight(1f),
        )
    }
}

@Composable
private fun StatPill(label: String, value: String, accent: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Surface2)
            .padding(14.dp),
    ) {
        Text(label, color = Text2, fontSize = 10.sp)
        Spacer(Modifier.height(4.dp))
        Text(value, color = accent, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SectorFilter(sectors: List<String>, selected: String, onChange: (String) -> Unit) {
    val items = remember(sectors) { listOf("All") + sectors }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        items.forEach { sector ->
            val active = selected == sector
            TextButton(
                onClick = { onChange(sector) },
                modifier = Modifier.clip(RoundedCornerShape(999.dp)).background(if (active) Accent.copy(alpha = 0.2f) else Surface2),
            ) {
                Text(sector, color = if (active) Accent else Text1, fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun MarketList(
    stocks: List<Stock>,
    prices: Map<String, Double>,
    selectedSymbol: String,
    watchlist: List<String>,
    onSelect: (String) -> Unit,
    onToggleWatch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Panel(modifier = modifier, title = "Market") {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("STOCK", color = Text2, fontSize = 10.sp, modifier = Modifier.weight(2f))
            Text("PRICE", color = Text2, fontSize = 10.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            Text("CHANGE", color = Text2, fontSize = 10.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
            Spacer(Modifier.width(36.dp))
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(stocks, key = { it.symbol }) { stock ->
                val price = prices[stock.symbol] ?: stock.price
                val up = stock.changePercent >= 0
                val watched = stock.symbol in watchlist
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selectedSymbol == stock.symbol) Surface2 else Color.Transparent)
                        .clickable { onSelect(stock.symbol) }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val chipColor = sectorsColor[stock.sector] ?: Accent
                    Box(
                        modifier = Modifier.size(30.dp).clip(RoundedCornerShape(10.dp)).background(chipColor.copy(alpha = 0.16f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(stock.symbol.take(2), color = chipColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(2f)) {
                        Text(stock.symbol, color = Text1, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(stock.name, color = Text2, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Text("\$${formatMoney(price)}", color = Text1, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
                    Text(
                        formatPercent(stock.changePercent),
                        color = if (up) Good else Bad,
                        fontSize = 11.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                    )
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { onToggleWatch(stock.symbol) }) {
                        Text(if (watched) "★" else "☆", color = if (watched) Accent else Text2)
                    }
                }
                Divider(color = Border, thickness = 0.5.dp)
            }
        }
    }
}

@Composable
private fun WatchlistView(
    market: Market,
    watchlist: List<String>,
    prices: Map<String, Double>,
    selectedSymbol: String,
    onSelect: (String) -> Unit,
    onRemove: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Panel(modifier = modifier, title = "Watchlist") {
        if (watchlist.isEmpty()) {
            EmptyState("No stocks watched", "Tap ★ on any stock to add it.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(watchlist, key = { it }) { symbol ->
                    val stock = market.getStock(symbol) ?: return@items
                    val price = prices[symbol] ?: stock.price
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selectedSymbol == symbol) Surface2 else Color.Transparent)
                            .clickable { onSelect(symbol) }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stock.symbol, color = Text1, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            Text(stock.name, color = Text2, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Text("\$${formatMoney(price)}", color = Text1, fontSize = 12.sp)
                        Spacer(Modifier.width(8.dp))
                        TextButton(onClick = { onRemove(symbol) }) { Text("Remove", color = Bad, fontSize = 10.sp) }
                    }
                    Divider(color = Border, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun TradePanel(
    selectedSymbol: String,
    prices: Map<String, Double>,
    market: Market,
    quantityText: String,
    onQuantityChange: (String) -> Unit,
    message: String,
    messageIsError: Boolean,
    onBuy: () -> Unit,
    onSell: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Panel(modifier = modifier, title = "Trade") {
        if (selectedSymbol.isBlank()) {
            EmptyState("Select a stock to trade", "Choose a symbol from the market or watchlist.")
        } else {
            val stock = market.getStock(selectedSymbol)
            if (stock != null) {
                val price = prices[selectedSymbol] ?: stock.price
                val chipColor = sectorsColor[stock.sector] ?: Accent
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(34.dp).clip(RoundedCornerShape(12.dp)).background(chipColor.copy(alpha = 0.16f)),
                        contentAlignment = Alignment.Center,
                    ) { Text(stock.symbol.take(2), color = chipColor, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(stock.symbol, color = Text1, fontWeight = FontWeight.SemiBold)
                        Text("\$${formatMoney(price)}", color = Good, fontSize = 12.sp)
                    }
                    Spacer(Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text("H: \$${formatMoney(stock.dayHigh)}", color = Good, fontSize = 9.sp)
                        Text("L: \$${formatMoney(stock.dayLow)}", color = Bad, fontSize = 9.sp)
                    }
                }

                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = quantityText,
                    onValueChange = { v ->
                        if (v.isEmpty() || v.all { it.isDigit() }) {
                            onQuantityChange(v)
                        }
                    },
                    label = { Text("Quantity") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Text1,
                        cursorColor = Accent,
                        focusedBorderColor = Accent,
                        unfocusedBorderColor = Border,
                        focusedLabelColor = Accent,
                        unfocusedLabelColor = Text2,
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(Modifier.height(10.dp))
                val qty = quantityText.toIntOrNull() ?: 0
                val estCost = qty * price
                Text("Estimated: \$${formatMoney(estCost)}", color = Text2, fontSize = 10.sp)
                Spacer(Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onBuy,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Good),
                        modifier = Modifier.weight(1f),
                    ) { Text("BUY", color = Color.Black, fontWeight = FontWeight.Bold) }
                    Button(
                        onClick = onSell,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Bad),
                        modifier = Modifier.weight(1f),
                    ) { Text("SELL", color = Color.Black, fontWeight = FontWeight.Bold) }
                }
            }
        }

        if (message.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(message, color = if (messageIsError) Bad else Good, fontSize = 11.sp)
        }
    }
}

@Composable
private fun PortfolioPanel(
    portfolio: Map<String, Int>,
    avgPrices: Map<String, Double>,
    prices: Map<String, Double>,
    modifier: Modifier = Modifier,
) {
    Panel(modifier = modifier, title = "Holdings") {
        if (portfolio.isEmpty()) {
            EmptyState("No holdings yet", "Buy something and it will appear here.")
            return@Panel
        }

        val totalValue = portfolio.entries.sumOf { (symbol, qty) -> (prices[symbol] ?: 0.0) * qty }
        val totalCost = portfolio.entries.sumOf { (symbol, qty) -> (avgPrices[symbol] ?: 0.0) * qty }
        val totalPnl = totalValue - totalCost
        val up = totalPnl >= 0

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background((if (up) Good else Bad).copy(alpha = 0.12f))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text("Total Value", color = Text2, fontSize = 9.sp)
                Text("\$${formatMoney(totalValue)}", color = if (up) Good else Bad, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("Unrealized P&L", color = Text2, fontSize = 9.sp)
                Text(formatSignedMoney(totalPnl), color = if (up) Good else Bad, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            items(portfolio.entries.toList(), key = { it.key }) { (symbol, qty) ->
                val current = prices[symbol] ?: 0.0
                val avg = avgPrices[symbol] ?: 0.0
                val value = current * qty
                val pnl = (current - avg) * qty
                val pnlColor = if (pnl >= 0) Good else Bad
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(symbol, color = Text1, fontWeight = FontWeight.SemiBold)
                        Text("$qty shares · Avg \$${formatMoney(avg)}", color = Text2, fontSize = 9.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("\$${formatMoney(value)}", color = Text1)
                        Text(formatSignedMoney(pnl), color = pnlColor, fontSize = 10.sp)
                    }
                }
                Divider(color = Border, thickness = 0.5.dp)
            }
        }
    }
}

@Composable
private fun TransactionHistory(
    transactions: List<Transaction>,
    prices: Map<String, Double>,
    modifier: Modifier = Modifier,
) {
    Panel(modifier = modifier, title = "History") {
        if (transactions.isEmpty()) {
            EmptyState("No transactions yet", "Your buys and sells will appear here.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(transactions.asReversed(), key = { it.timestamp }) { tx ->
                    val up = tx.type == Transaction.Type.BUY
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text("${tx.type.name} ${tx.symbol} × ${tx.quantity}", color = Text1, fontWeight = FontWeight.SemiBold)
                            Text("@ \$${formatMoney(tx.pricePerShare)}", color = Text2, fontSize = 9.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(if (up) "- \$${formatMoney(tx.totalAmount)}" else "+ \$${formatMoney(tx.totalAmount)}", color = if (up) Bad else Good)
                            Text(formatTime(tx.timestamp), color = Text2, fontSize = 9.sp)
                        }
                    }
                    Divider(color = Border, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
private fun SettingsView(onReset: () -> Unit, modifier: Modifier = Modifier) {
    Panel(modifier = modifier, title = "Settings") {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text("Danger Zone", color = Bad, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(10.dp))
            Text(
                "This clears your portfolio, watchlist, and trade history.\nBalance returns to \$1,000,000.",
                 color = Text2,
                 textAlign = TextAlign.Center,
                 fontSize = 12.sp,
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = onReset, colors = ButtonDefaults.buttonColors(backgroundColor = Bad)) {
                Text("RESET ALL DATA", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun Panel(modifier: Modifier = Modifier, title: String, content: @Composable Column.() -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Surface2)
            .padding(14.dp),
    ) {
        Text(title.uppercase(), color = Text1, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        content()
    }
}

@Composable
private fun EmptyState(title: String, subtitle: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("○", color = Text2, fontSize = 26.sp)
        Spacer(Modifier.height(8.dp))
        Text(title, color = Text1, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        Text(subtitle, color = Text2, fontSize = 10.sp, textAlign = TextAlign.Center)
    }
}
