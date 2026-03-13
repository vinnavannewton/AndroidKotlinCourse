//it identifier
fun main() {
    upperCase("Hello", { it.uppercase() })
}

fun upperCase(str: String, myFunction: (String) -> String) {
    val upperCaseWord = myFunction(str)
    println(upperCaseWord)
}