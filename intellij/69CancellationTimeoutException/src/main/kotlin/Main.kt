import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.io.encoding.Base64

fun main() {
    println("Main program starts: ${Thread.currentThread().name}")
    val start = System.currentTimeMillis()

    val parentJob = CoroutineScope(Dispatchers.Default).launch {

        val job1 = launch {
            try {
                println(getData1(Thread.currentThread().name))
            }
            catch (ex: Exception) {
                println("Exception caught safely: ${ex.message}")
            } finally {
                println("Resources closed safely")
            }
        }
        val job2 = launch {
            try {
                println(getData2(Thread.currentThread().name))
            }
            catch (ex: Exception) {
                println("Exception caught safely: ${ex.message}")
            } finally {
                println("Resources closed safely")
            }

        }
        val job3 = launch {
            println(getData3(Thread.currentThread().name))
        }
//        println(jobDeferred1.await() + "\n${jobDeferred2.await()}")
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
    throw Exception("Error while getting the data in getData1")
    delay(2000)
    println("Fake work1 finished $threadName")
    return "Result1"

}

private suspend fun getData2(threadName: String): String {
    println("Fake work2 starts $threadName")
    throw Exception("Error while getting the data in getData2")
    delay(2000)
    println("Fake work2 finished $threadName")
    return "Result2"

}
private suspend fun getData3(threadName: String): String {
    println("Fake work3 starts $threadName")
    delay(2000)
    println("Fake work3 finished $threadName")
    return "Result3"

}