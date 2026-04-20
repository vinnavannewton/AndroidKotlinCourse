package com.stock.desktop

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.stock.ui.StockFlowApp

fun main() = application {
    val windowState = rememberWindowState(width = 1400.dp, height = 900.dp)
    val icon = painterResource("icon.png")
    
    Window(
        onCloseRequest = ::exitApplication,
        title = "StockFlow",
        state = windowState,
        icon = icon
    ) {
        CompositionLocalProvider(LocalDensity provides Density(1.0f)) {
            StockFlowApp()
        }
    }
}
