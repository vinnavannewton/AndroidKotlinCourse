import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import kotlin.io.encoding.Base64

fun main() {
    println("Main program starts: ${Thread.currentThread().name}")
    val start = System.currentTimeMillis()

    val parentJob = CoroutineScope(Dispatchers.Default).launch {

        val jobDeferred1: Deferred<String> = async {
            getData1(Thread.currentThread().name)


        }

        val jobDeferred2: Deferred<String> = async {
            getData2(Thread.currentThread().name)

        }
        println(jobDeferred1.await() + "\n${jobDeferred2.await()}")
    }

    runBlocking {
        parentJob.join()
    }

    parentJob.invokeOnCompletion {
        it?.let {
            println("Parent job failed ${it.message}")
        } ?: println("Parent job completed successfully")
    }
    println("Total time: ${System.currentTimeMillis() - start}") //current time minus start time to get total time
    println("Main program ends: ${Thread.currentThread().name}")

}



private suspend fun getData1(threadName: String): String {
    println("Fake work1 starts $threadName")
    delay(2000)
    println("Fake work1 finished $threadName")
    return "Result1"

}

private suspend fun getData2(threadName: String): String {
    println("Fake work2 starts $threadName")
    delay(2000)
    println("Fake work2 finished $threadName")
    return "Result2"

}