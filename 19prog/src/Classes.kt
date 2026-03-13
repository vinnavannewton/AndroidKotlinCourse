// Properties passed to parameter
class Car(var name: String, var model: String, var color: String, var doors: Int) {

    fun move() {
        println("The car $name is moving.")
    }

    fun stop() {
        println("The car $name has stopped.")
    }
}