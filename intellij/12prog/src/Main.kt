//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println(sum(1,2,3,4))
    println(sum1(1,2,3,4,5,6,7,8,9,10))
    println(sum2(1,2,3,4,5,6,7,8,9,10))
}
//fixed arg
fun sum(a: Int, b: Int, c: Int, d: Int): Int {
    return a + b + c + d
}
//variable arg
fun sum1(vararg numbers: Int): Int {
    var result = 0
    for(number in numbers) {
        result  += number
    }
    return result
}

fun sum2(vararg numbers: Int) {
    numbers.forEach { println(it) }
}