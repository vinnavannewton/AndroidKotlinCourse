fun main() {
    val user: User? = null
    //run is a combination of with and let functions
    val result = user?.run {
        println(firstName)
        println(lastName)
        println(age)
        23 //last line is returned
    }


// the below one wont work
//    with(user) {
//        println(firstName)
//        println(lastName)
//        println(age)
//    }
}

data class User(val firstName: String, val lastName: String, val age: Int)