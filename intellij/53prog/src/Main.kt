//Generics: Aiding the compiler
fun main() {
    val footballPlayer = FootballPlayer("Football player 1")
    val footballPlayer2 = FootballPlayer("Football player 2")
    val footballPlayer3 = FootballPlayer("Football player 3")

    val baseballPlayer = BaseballPlayer("Baseball player 1")
    val baseballPlayer2 = BaseballPlayer("Baseball player 2")
    val baseballPlayer3 = BaseballPlayer("Baseball player 3")

    val footballTeam =
        Team<FootballPlayer>("Football team", mutableListOf(footballPlayer))//You can delete the <> it will work

    footballTeam.addPlayers(footballPlayer2)

    val baseballTeam = Team("Baseball team", mutableListOf(baseballPlayer))
    baseballTeam.addPlayers(baseballPlayer2)

    val gamesTeam = Team<GamesPlayer>("Games Team", mutableListOf())
}

class Team<T: Player>(val name: String, val players: MutableList<T>) {
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
