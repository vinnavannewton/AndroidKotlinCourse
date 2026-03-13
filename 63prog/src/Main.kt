//scope functions
fun main() {
//    val user = User().apply {
//        firstName = "Alex"
//        lastName = "Martin"
//        age = 20 //return value is the object applied which is User() here which is returned
//
//    }

//    val user2 = User2("Alex", "Dobinca", 23).also {
//        println(it)
//    }

    var text: String? = null
    val result = text?.let {
        println(it)
        "Hi" //last line of code here is the return
    }



//    with(user) {
//        firstName = "Alex"
//        lastName = "Martin"
//        age = 20
//        "HI" //last line of code here is the return
//    }

}

class User() {
    var firstName: String = ""
    var lastName: String = ""
    var age : Int = -1
}

data class User2(val firstName: String, val lastName: String, val age: Int) {

}

