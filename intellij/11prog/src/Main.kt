fun main() {
    sendMessage()
    //to set parameter that is independent of position and dependent on name ie named parameter
    sendMessage2(name = "Alex")

}

//Default set if not passed parameter
fun sendMessage(name: String = "User", message: String = "") {
    println("Name = $name and message = $message")
}

fun sendMessage2(name: String = "User", message: String = sendText()) {
    //named parameter
    println("Name = $name and message = $message")

}

fun sendText() =  "some text"
