//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //OOP
    //Called as instantiation
    val car1 = Car()
    //Dot notation
    car1.name = "Toyota"
    car1.color = "White"
    car1.model = "Gazoo Racing"
    car1.doors = 4

    println("Name = ${car1.name}")
    println("model = ${car1.model}")
    println("color = ${car1.color}")
    println("doors = ${car1.doors}")

    car1.move()
    car1.stop()

    val car2 = Car()
    car2.name = "Ford"
    car2.model = "MUstang"
    car2.color = "Blue"
    car2.doors = 2

    println("\n")

    println("Name = ${car2.name}")
    println("model = ${car2.model}")
    println("color = ${car2.color}")
    println("doors = ${car2.doors}")

    car2.move()
    car2.stop()
}

//must be in PascalCase
class Car {
    var name = ""
    var model = ""
    var color = ""
    var doors = 0

    fun move() {
        println("The car $name is moving.")
    }

    fun stop() {
        println("The car $name has stopped.")
    }
}