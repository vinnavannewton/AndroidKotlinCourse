//More than one upper bound
//where T inherits from Player and also T implements the interface Listener
//It means T must be a subtype of both Player and Listener
//two upper bounds for classes and functions.
class Team<T>(val name: String, val players: MutableList<GamesPlayer>) where T: Player, T: Listener{
    fun addPlayers(player: T) {
        if (players.contains(player)) {
            println("Player: ${player.name} is already in the team")

        } else {
            players.add(player)
            println("Player: ${player.name} was added in the team")
        }
    }
}

open class Player(val name: String)


class FootballPlayer(name: String) : Player(name), Listener {
    override fun listen() {

    }
}
class BaseballPlayer(name: String) : Player(name)
open class GamesPlayer(name: String) : Player(name)
class CounterStrikePlayer(name: String) : GamesPlayer(name)

fun main() {
    val footballTeam = Team<Player>(
        "Football Team",
        mutableListOf<FootballPlayer>(FootballPlayer("Player"), FootballPlayer("Player 2") )
    )

    val gamesTeam = Team<FootballPlayer>(
        "Games Player",
        mutableListOf<GamesPlayer>((GamesPlayer("Player")), (GamesPlayer("Player 2")))
    )
    addPlayer(FootballPlayer("Player 3"))
}

inline fun <reified T> getSpecificTypes(list: List<Any>): List<T> {
    val specificList = mutableListOf<T>()

    for (element in list) {
        if (element is T) {
            specificList.add(element)
        }
    }
    return specificList
}

interface Listener {
    fun listen()
}

fun <T> addPlayer(player: T) where T: Player, T: Listener {

}

