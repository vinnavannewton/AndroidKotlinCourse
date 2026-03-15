//initial without using inheritance
// inheritance
//using classes
//fun main() {
//
//
//}
//

//class Vehicle(val name: String, val color: String) {
//
//}
//
//class Car(val name: String, val color: String, val engines: Int, val doors: Int) {
//    fun move() {
//        println("$name is moving.")
//
//    }
//
//    fun stop() {
//        println("$name has stopped.")
//
//    }
//}
//
//class Plane(val name: String, val color: String, val engine: Int, val doors: Int) {
//    fun move() {
//        println("$name is moving.")
//
//    }
//
//    fun stop() {
//        println("$name has stopped.")
//
//    }
//
//}

//with using inheritance
fun main() {
    val car = Car("BMW", "Red", 1, 4 );
    val plane = Plane("Boieng", "White", 4, 4);
    car.move(); //works even tho move func isnt in the Car class
    car.stop();
    plane.move();
    plane.stop();
}

//base class in which we are heriting from
open class Vehicle(val name: String, val color: String) {
   open fun move() {
        println("$name is moving.")

    }

   open fun stop() {
        println("$name has stopped.")

    }
}

class Car(name: String, color: String, val engines: Int, val doors: Int): Vehicle(name, color) {
    override fun move() {
        flying()
        super.move() //created with intellij menu<code<generate<override methods<clicking on move in car try for stop in car and click ok
    }

    fun flying() {
        println("The plane is flying.")
    }
}

class Plane(name: String, color: String, val engine: Int, val doors: Int): Vehicle(name, color) {



}
//8:14:00
