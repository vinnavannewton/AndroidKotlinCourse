//Interfaces

fun main() {


}

interface Engine {
    fun startEngine()
}

class Car(val name: String, val color: String): Engine {
    override fun startEngine() {
        println("The car is starting the engine")
    }

}

class Truck(val name: String, val color: String): Engine {
    override fun startEngine() {
        println("The car is starting the engine")

    }

}

class Plane(val name: String, val color: String): Engine {
    override fun startEngine() {
        println("The car is starting the engine")

    }

}

class Testla(val name: String, val color: String): Engine {
    override fun startEngine() {
        println("Tesla is starting the engine")
    }

}