//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //named loops
    var number = 0
    //naming the loop, any name
    outer@ while (number < 5) {
        number++
        println(number)

        var i = 0
        while (i < 5) {
            if (i == 0) {
                break@outer //declaring which loop to break
            }
            i++
            println("**$i")
        }
    }
}