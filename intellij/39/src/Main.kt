//Collections
//mutable and immutable
//list and sets
fun main() {
    //Can have duplicates
    val name = mutableListOf<String>("Name 1", "Name 2", "Name 3")
    name.add("Name 4")
    name.removeAt(0)
    name.remove("Name 2")
    name.forEach { println(it) }

    val name1 = mutableSetOf<String>("Name 1", "Name 2", "Name 1")
    name1.forEach { println(it) }

    val user1 = User("Name 1")
    val user2 = User("Name 2")
    val user3 = User("Name 3")
    val user4 = User("Name 4")
    val user5 = User("Name 5")
    val user6 = User("Alex")
    val user7 = User("Alex")
    //this executes 2 alex's even tho it is a set and shouldn't and that's why we used data classes
    //so we override the equals
    val users = mutableSetOf<User>(user1, user2, user3, user4, user5, user6, user7)
    users.forEach { println(it.name) }
}

data class User(val name: String)