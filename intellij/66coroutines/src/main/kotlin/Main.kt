import com.sun.tools.javac.Main
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.random.Random

fun main() { //executed on the main thread

    println("Main program starts: ${Thread.currentThread().name}")

    CoroutineScope(Dispatchers.Default).launch {           //used to group a bunch of coroutines, Default used for heavy work
        println("Fake work starts: ${Thread.currentThread().name}")
        Thread.sleep(2000) //pretending to do some work
        println("Fake work ends: ${Thread.currentThread().name}")
    }
    Thread.sleep(2500) //fake work ends in 2000ms but the program won't wait, so it ends before the coroutine is finished
    //so include 2500ms on the main so it waits for coroutines to finish before ending the program.


    println("Main program ends: ${Thread.currentThread().name}")


}