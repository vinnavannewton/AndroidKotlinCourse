//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val max = getMax(5, 9)
    println(max)
    val otherMax = getMaxOtherExample(5, 9)
    println(otherMax)
}

fun getMax(a: Int, b: Int): Int {
    val max = if (a > b) a else b
    return max // we cannot have 2 returns in one function on same branch only if nested u can have
} // the above code and the getMaxOtherExample code works the same its called a single expression function

fun getMaxOtherExample(a: Int, b: Int): Int = if (a > b) a else b