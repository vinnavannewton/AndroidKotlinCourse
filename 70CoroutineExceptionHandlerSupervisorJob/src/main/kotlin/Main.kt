import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
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
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.io.encoding.Base64
//Handler handles the error, makes the code concise unlike the previous try and catch block.
//val handler = CoroutineExceptionHandler { _, exception ->
//    println("Error in one of the children: ${exception.message}")
//
//}

fun main() {
    println("Main program starts: ${Thread.currentThread().name}")
    val start = System.currentTimeMillis()

    val parentJob = CoroutineScope(Dispatchers.Default).launch {

            val job1 = launch {
                println(getData1(Thread.currentThread().name))
            }
            val job2 = launch {
                println(getData2(Thread.currentThread().name))
            }
            val job3 = launch {
                println(getData3(Thread.currentThread().name))
            }
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
    throw CancellationException("Error while getting the data in getData1")
    delay(2000)
    println("Fake work1 finished $threadName")
    return "Result1"

}

private suspend fun getData2(threadName: String): String {
    println("Fake work2 starts $threadName")
//    throw Exception("Error while getting the data in getData2")
    delay(2000)
    println("Fake work2 finished $threadName")
    return "Result2"

}

private suspend fun getData3(threadName: String): String {
    println("Fake work3 starts $threadName")
    delay(2000)
    println("Fake work3 finished $threadName")
    return "Result3"

} //16:09:29