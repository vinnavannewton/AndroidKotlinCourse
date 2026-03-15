package com.example.classes

class Team<T: Player>(val name: String, val players: MutableList<in T>) {
    fun addPlayers(Player: T) {
        if (players.contains(Player)) {
            println("Player: ${player.name} is already in the team")

        } else {
            players.add(player)
            println("Player: ${player.name} was added to the team")
        }
    }
}

open class Player(val name: String)

class FootballPlayer(name: String) : Player(name)
class BaseballPlayer(name: String) : Player(name)

open class GamesPlayer(name: String) : Player(name)

class CounterStrikePlayer(name: String) : GamesPlayer(name)
