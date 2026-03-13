//Generic functions, Inline Reification
class Team<T : Player>(
    val name: String,
    val players: MutableList<T>
) { //add out before T for covariance and in before T for counter covariance
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
open class GamesPlayer(name: String) : Player(name)
class CounterStrikePlayer(name: String) : GamesPlayer(name)

fun main() {
    val mixedList = mutableListOf(1, 2, 360, 'a', 'b', 'c', "hello", "world")
    val specificList = getSpecificTypes<Char>(mixedList)
    for (element in specificList) {
        println(element)
    }

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

