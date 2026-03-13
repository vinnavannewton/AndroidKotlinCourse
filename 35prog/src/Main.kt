//Data classes

fun main() {
    val name1 = "Alex"
    val name2 = "Alex"

    println(name1 == name2) //structure equality

    val user1 = User("Alex", "Dobbin", 23)
    val user2 = User("Alex", "Dobbin", 23)

    println(user1 == user2) //referential equality we defined what this equals mean by using fun equals func
    //or
    println(user1.equals(user2))

    println(user1)
    println(user2)
}

data class User(var firstName: String, var lastName: String, var age: Int) {

}

//this code produces same results as previous one
