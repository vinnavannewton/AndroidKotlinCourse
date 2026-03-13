fun main() {
    //6:17:55
    val user = User("Alex", )
    val friend = User("Aohn", "Dobinca")

    println("Name = ${user.name}")
    println("LastName = ${user.lastName}")
    println("Age = ${user.age}")

    println("\n")

    println("Name = ${friend.name}")
    println("LastName = ${friend.lastName}")
    println("Age = ${friend.age}")

}
//primary constructor
class User(var name: String, var lastName: String, var age: Int) {
    //secondary constructors
    //constructor is chosen based on which parameter satisfies
    //can be used for if this is called with these parameters do this.
    constructor(name: String): this(name, "LastName", 0) {
        println("2nd")
    }

    constructor(name: String, lastName: String): this(name, lastName, 0) {
        println("3rd")

    }
}

