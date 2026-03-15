//Data classes
//diff between structure equality, referential equality

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

class User(var firstName: String, var lastName: String, var age: Int) {
    override fun equals(other: Any?): Boolean {
        //referential equality
        if (this === other) {
            return true

        }

        if (other is User) {
            return this.firstName == other.firstName
                    && this.lastName == other.lastName
                    && this.age == other.age
        }
        return false
        } //when equals is overridden then hashcode must also be overridden bcz of performance reasons

    override fun hashCode(): Int {
        return 0
    }

    override fun toString(): String {
        //makes it print the println(user1) and user 2
        return "User(firstName = '$firstName', lastName = '$lastName', age = $age"
    }

    }
//Data classes in next prog


