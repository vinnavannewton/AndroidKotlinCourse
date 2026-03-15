//Map collection
fun main() {
    val users = mutableMapOf<Int, String>(1 to "Maria", 2 to "Alex", 3 to "John")
    println(users[2])

    users[5] = "Vlad"
    users.remove(2)

    users.forEach { k, v ->
        println("$k and $v")
    }


}