fun main() {
    var x = 5 // if one var u keep as float all the var it interacts will result in float
    var y = 3f
    var result = x + y
    println(result)
    println("x + y = ${x + y}")  // ctl + D to dupilcate linesq `
    println("x - y = ${x - y}")
    println("x * y = ${x * y}")
    println("x / y = ${x / y}")
    println("x % y = ${x % y}")
    println("${(3 + 3) * 4}")
    x = 0
    println("x++ ${x++}")
    println("++x ${++x}")
    println("x-- ${x--}")
    println("--x ${--x}")

    val isActive = false
    if (isActive) {
        println("I'm active")
    } else {
        println("I'm inactive")
    }

    val myNumber = 5

    if (myNumber == 5) {
        println("I'm 5")
    }

    val num1 = 3
    val num2 = 5
    val text = if (num1 == 3 || num2 == 5) { // without braces for one line it works as we know
        println("hehehehehe")
        "this is text 1"
    } else
        5

    println(text)


}