//Abstract classes:cannot create instances, can only be inherited by other classes

fun main() {
    val vehicle = Vehicle()

}

abstract class Vehicle {
    val text = "some text"
    abstract fun move()

    abstract fun stop()
}

class Car(var name: String, val color: String, var engine: String, var door: String): Vehicle() {
    override fun move() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }
//8:48:00
}