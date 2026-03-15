package com.example.main

import com.example.classes.*

fun main() {
    val footballPlayer = Team<com.example.classes.Player>(
        "football",
        mutableListOf<FootballPlayer>(FootballPlayer("Player 1"), FootballPlayer("Player 2"))
    )

    val gamesTeam = Team<CounterStrikePlayer>(
        "Games Team",
        mutableListOf<GamesPlayer>(CounterStrikePlayer("Player 1"), CounterStrikePlayer("Player 2"))
    )
}