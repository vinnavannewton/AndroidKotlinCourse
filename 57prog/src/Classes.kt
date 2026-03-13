//internal class Team<T>(val name: String, val players: MutableList<FootballPlayer>) where T: Player, T: Listener{
//    fun addPlayers(player: T) {
//        if (players.contains(player)) {
//            println("Player: ${player.name} is already in the team")
//
//        } else {
//            players.add(player as FootballPlayer)
//            println("Player: ${player.name} was added in the team")
//        }
//    }
//}
//
//open class Player(val name: String)
//
//
//class FootballPlayer(name: String) : Player(name), Listener {
//    override fun listen() {
//
//    }
//}
//class BaseballPlayer(name: String) : Player(name)
//open class GamesPlayer(name: String) : Player(name)
//class CounterStrikePlayer(name: String) : GamesPlayer(name)
//
//inline fun <reified T> getSpecificTypes(list: List<Any>): List<T> {
//    val specificList = mutableListOf<T>()
//
//    for (element in list) {
//        if (element is T) {
//            specificList.add(element)
//        }
//    }
//    return specificList
//}
//
//interface Listener {
//    fun listen()
//}
//
//fun <T> addPlayer(player: T) where T: Player, T: Listener {
//
//}
//
//
////By default, everything is public
//
