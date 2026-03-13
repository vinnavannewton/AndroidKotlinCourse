class User(var firstName: String, var lastName: String, var age: Int) {
    init {
        println("User: $firstName was created")
    }
}