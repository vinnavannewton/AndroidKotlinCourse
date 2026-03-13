//Ordering & Comparators
fun main() {
    val numbers = mutableListOf<Int>(2, 5, 1, 40, 20, 100, 60)
    numbers.sorted().forEach { println(it) }

    val laptops = mutableListOf(
        Laptop("Dell", 2021, 4, 600),
        Laptop("HP", 2020, 16, 800),
        Laptop("Lenovo", 2022, 8, 1000)
    )
    laptops.sortedWith(compareBy { it.price }).forEach { println(it) }
    println()
    laptops.sortedBy { it.price }.forEach { println(it) } //same effect, better code.

}

data class Laptop(val brand: String, val year: Int, val ram: Int, val price: Int)

