import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import kotlin.io.encoding.Base64

fun main() {
    println("Main program starts: ${Thread.currentThread().name}")

    val parentJob = CoroutineScope(Dispatchers.Default).launch {
        val job1 = launch {
            val result1 = getData1(Thread.currentThread().name)
            println("result1: $result1")
        }
        job1.join() //to execute in sequence
        val job2 = launch {
            val result2 = getData2(Thread.currentThread().name)
            println("Result2: $result2")
        }
    }

    runBlocking {
        parentJob.join()
    }
    println("Main program ends: ${Thread.currentThread().name}")

}



private suspend fun getData1(threadName: String): String {
    println("Fake work1 starts $threadName")
    delay(2000)
    println("Fake work1 finished $threadName")
    return "Result1"

}

private suspend fun getData2(threadName: String): String {
    println("Fake work1 starts $threadName")
    delay(2000)
    println("Fake work1 finished $threadName")
    return "Result1"

}