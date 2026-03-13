//access modifiers

fun main() {
    val user = User()
//    user.firstName = "Alex"
    user.lastName = "Dobbin"
    println(user.updateName("Newton"))


}

open class User {
    protected var firstName: String = "" //inherited classes can use it if protected is given
    var lastName: String = ""

    fun updateName(newName: String) {
        firstName = newName
        println(firstName)
    }

}

class VipUser : User() {
    fun printInfo() {
        println(firstName)
    }

}