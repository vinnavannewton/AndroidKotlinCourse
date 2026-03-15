//10:14:55
//Flatten functions

fun main() {


    val numbersSets = arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6), arrayOf(7, 8, 9)) //2D Array
    println(numbersSets[2][2])

    val numbersSets1 = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(7, 8, 9))
    for (numbers in numbersSets1) {
        for (number in numbers) {
            println(number)
        }
        println("\n")
    }

    val numbersFlatten = numbersSets1.flatten() //removes nested loop flattens the 2D array into 1D
    println(numbersFlatten[8])
    for (numbers in numbersFlatten) {
        println(numbers)
    }
}
