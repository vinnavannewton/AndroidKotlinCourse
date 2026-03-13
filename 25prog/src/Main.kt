//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //access classes without creating a object using companion object
    val result = Calculator.sum(5, 10)
    Calculator.max

}

class Calculator() {
    companion object {
        var max = 100;
        fun sum(a: Int, b: Int): Int {
            return a + b
        }
    }
}
