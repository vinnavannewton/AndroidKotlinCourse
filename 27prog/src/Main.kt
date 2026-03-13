//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //lazy initialisation
    val user1 = User("Alex", "Dobbin", 22)
    //this calls init twice
//    val user2 = User("User", "lastName", 0)
    //this only does once
    val user2 by lazy {
        User("User1", "lastName", 22)
    }
    //only possible with lazy initialization
    println(user2.firstName)

}

class User(var firstName: String, var lastName: String, var age: Int) {
    init {
        println("User: $firstName was created")
    }
}
//7:19:00