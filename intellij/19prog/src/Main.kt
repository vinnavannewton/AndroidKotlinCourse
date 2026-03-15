fun main() {
    //OOP
    //We made it into a contractor by passing parameters
    val car1 = Car("Toyota", "Gazoo Racing", "Red", 4)


    println("Name = ${car1.name}")
    println("model = ${car1.model}")
    println("color = ${car1.color}")
    println("doors = ${car1.doors}")

    car1.move()
    car1.stop()

    val car2 = Car("Ford", "Mustang", "Blue", 2)

    println("\n")

    println("Name = ${car2.name}")
    println("model = ${car2.model}")
    println("color = ${car2.color}")
    println("doors = ${car2.doors}")

    car2.move()
    car2.stop()
}

