import kotlin.concurrent.thread

//Threads
fun main() {
    println("Hello World 1")
    println("Hello World 2")
    println("Hello World 3")
    println("Hello World 4")
    println("Hello World 5")
    thread {
        Thread.sleep(5000) //runs on a diff thread
        println("Here after 5 seconds")
    }


    println("Hello World 6")
    println("Hello World 7")
    println("Hello World 8")
    println("Hello World 9")
    println("Hello World 10")
    println("Hello World 11")

}