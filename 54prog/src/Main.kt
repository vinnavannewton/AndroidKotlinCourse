//co variance and counter covariance
//both are opposite
class Team<T: Player>(val name: String, val players: MutableList<out T>) { //add out before T for covariance and in before T for counter covariance
    fun addPlayers(player: T) {
        if (players.contains(player)) {
            println("Player: ${player.name} is already in the team") //Without "as" u can access the name in the Player class

        } else {
            players.add(player)
            println("Player: ${player.name} was added in the team")
        }
    }
}

open class Player(val name: String)


class FootballPlayer(name: String) : Player(name)
class BaseballPlayer(name: String) : Player(name)
class GamesPlayer(name: String): Player(name)
class CounterStrikePlayer(name: String): GamesPlayer(name)

fun main() {
    val footballTeam = Team<Player>( //even tho its Player i can pass FootballPlayer as it is a subclass but only when i put that out keyword before T. This is covariance
        "Football Team",
        mutableListOf<FootballPlayer>(FootballPlayer("Player 1"), FootballPlayer("Player 2"))
    )

    val gamesTeam = Team<CounterStrikePlayer>(
        "Games Team",
        mutableListOf<GamesPlayer>(GamesPlayer("Player 1"), GamesPlayer("Player 2"))
    )
}

