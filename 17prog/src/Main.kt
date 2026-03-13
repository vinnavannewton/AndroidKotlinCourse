//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//fun main() {
    //Arrays, mutable by default, arrays can be of any types not only int, char but both.
//    val names: Array<String> = arrayOf("John", "Stephen")
//    println(names[0])
//    names[0] = "Alex"
//    println(names[0])
//
//    for (name: String in names) {
//        println(name)
//    }

//    val max = findMax(arrayOf(4, 6, 7, 4, 3, 6))
//    val min = findMin(arrayOf(4, 7, 9, 20, 7, 100))
//    println("max is $max")
//    println("min is $min")

//    fun findMax(numbers: Array<Int>): Int {
//        var max = numbers[0]
//
//        for (number in numbers) {
//            if (number > max) {
//                max = number
//            }
//        }
//        return max
//    }
//    fun findMin(numbers: Array<Int>): Int {
//        var min = numbers[0]
//
//        for (number in numbers) {
//            if (number < min) {
//                min = number
//            }
//        }
//        return min
//    }

fun main() {
    val max = findMinAndMax(arrayOf(20, 40, 60, 100), true)
    val min = findMinAndMax(arrayOf(20, 40, 60, 100), false)
    println("the max is = $max")
    println("min value is $min")

}

fun findMinAndMax(numbers: Array<Int>, searchMax: Boolean): Int {
    var max = numbers[0]
    var min = numbers[0]
    if (searchMax) {
        for (number in numbers) {
            if (number > max) {
                max = number
            }
        }
        return max

    } else {
        for (number in numbers) {
            if (number < min) {
                min = number
            }
        }
        return min
    }
}





