fun main() {
    val numbersString = listOf("one", "two", "three", "four", "five", "six")
    println(numbersString.slice(1..3))
    println(numbersString.slice(0..4 step 2))
    println(numbersString.slice(setOf(3, 5, 0)))

    println("\nsection\n")

    println(numbersString.take(3)) //takes the first 3 elements from the list
    println(numbersString.takeLast(3)) //takes the last 3 elements from the list
    println(numbersString.drop(5)) //except the first 5 elements, it prints all
    println(numbersString.dropLast(5)) //except the last 5 elements, it prints all

    println("\nsection\n")

    println(numbersString.takeWhile { !it.startsWith("f") })
    println(numbersString.takeLastWhile { it != "three" })
    //!!!Once "three" breaks the predicate,
    // dropWhile stops evaluating entirely.
    // "six" has length 3, but it's past the stopping point,
    // so it's never checked and is included in the result.
    //If You Want to Filter All Length-3 Strings
    //Use filter instead, which checks every element.
    println(numbersString.dropWhile { it.length == 3 })
    println(numbersString.dropLastWhile { it.contains("i") })

    println("\nsection\n")

    val numbers = (0..13).toList()
    println(numbers.chunked(3))
    println(numbers.chunked(3) {it.sum()}) //summed up of junked elements

    println("\nsection\n")

    println(numbersString.windowed(3))

    //!!NOTE
    //junk ignores last elements of the size of those chunks in last isn't the size u asked i.e., there might be left out elements
    //but a window doesn't ignore those elements, but overlaps occur
    //see the blow for eg
    println("\neg\n")

    println(numbers.chunked(3))
    println(numbers.windowed(3)) // like a window of size 3 moving one element each
}