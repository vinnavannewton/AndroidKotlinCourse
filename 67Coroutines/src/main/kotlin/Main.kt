import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    println("Main program starts: ${Thread.currentThread().name}")

    val parentJob = CoroutineScope(Dispatchers.Default).launch {
        println("Fake work starts: ${Thread.currentThread().name}")
        delay(2000) //pretending to do some work
        println("Fake work ends: ${Thread.currentThread().name}")
    }
    runBlocking {
        parentJob.join() //waits for parentJob's to finish to join it to the process; unlike us giving it
        //a discrete value of milliseconds, it does automatically.
    }



    println("Main program ends: ${Thread.currentThread().name}")
    //15:11:48


}