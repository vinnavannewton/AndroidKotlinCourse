////Exception
//fun main() {
//    val a = 5
//    val b = 0
//    try {
//        println(a / b) //Doesn't execute second line if fails
//    } catch (e: ArithmeticException) {
//        println("You can't divide by zero: $e.message")
//    } finally {
//        println("Meow") // so we add this
//    }
//
//}
fun main() {
    val a = 5
    val b = 0

    val result = try {
        println(a / b)
        a / b
    } catch (e: ArithmeticException) {
        println("You can't divide by zero")
    } finally {
        println("Meow")
    }
    println(result)
}