//Linear search

fun main() {
    println(searchElement(27, mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30)))

}

private fun searchElement(searchedElement: Int, numbers: MutableList<Int>): Int {
    var i = 0
    for (number in numbers) {
        i++
        println("Searched number: $i")
        if (number == searchedElement) {
            return number
        }
    }
    return -1
}