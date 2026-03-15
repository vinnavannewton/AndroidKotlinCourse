//Filtering

fun main() {
    val numbers = listOf("one", "two", "three", "four")
    val longerThan3 = numbers.filter { it.length > 3 }
    println(longerThan3)

    val numbersMap = mapOf("key 1" to 1, "key 2" to 2, "key 3" to 3, "key 101" to 101)
    val filteredMap = numbersMap.filter { it.key.endsWith("1") && it.value > 100 }
    println(filteredMap)

    val filteredIndex = numbers.filterIndexed { index: Int, value: String -> (index != 0) && (value.length < 5)  }
    val filteredNot = numbers.filterNot { it.length <= 3 } //filters which doesn't satisfy the condition

    println(filteredIndex)
    println(filteredNot)

    val mixedList = listOf(1, 2, 3, 'A', 'B', 'C', "Hello", "Alex", false)
    mixedList.filterIsInstance<Char>().forEach { println(it) } //filter based on data type

    //Partition filter
    val (match, rest) = numbers.partition { it.length > 3 }
    println(match)
    print(rest)
}