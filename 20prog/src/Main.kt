//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val user = User("Alex", "Dobinca", 23)
    val friend = User("Aohn", "Dobinca", 23)
    println("Name ${friend.name}")
}

class User(name: String, var lastName: String, var age: Int) {
    var name: String
    //initialiser block
    init {
        if (name.lowercase().startsWith("a")){
            //takes the variable instead of the parameter
            this.name = name
        } else {
            this.name = "User"
            println("The name doesn't start with 'A' or 'a")
        }
    }

}