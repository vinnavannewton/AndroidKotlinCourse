//higher order function: if a func can accept a func as a parameter or return a function
//lambda is a function without a name

fun main() {
//    val myLambda = {a: Int -> println(a); }
    add(5, 10, ) {a: Int -> println(a); }
}

fun add(a: Int, b: Int, action: (Int) -> Unit) {
    action(a + b)
    //14:06:09
}