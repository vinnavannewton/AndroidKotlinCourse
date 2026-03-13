//Plus and minus operators
fun main() {
    val numbers = mutableListOf("one", "two", "three", "four")
    val plusList = numbers + "five"
    val minusList = numbers - mutableListOf("three", "four")
    println(plusList)
    println(minusList)



}