//enums
fun main() {
    for (direction in Direction.values()) {
        println(direction);
    }

    println(Direction.NORTH.direction);
    println(Direction.NORTH.distance);
    println(Direction.NORTH.name); //build in variable provided by enum class

    Direction.WEST.printData();//gonna print WEST values

    val direction = Direction.valueOf("east".uppercase())
    when(direction) {
        Direction.EAST -> println("It is east");
        Direction.WEST -> println("It is west");
        Direction.NORTH -> println("It is north");
        Direction.SOUTH -> println("It is south");
    }

}

//creating enum class and enum values inside it

enum class Direction(var direction: String, var distance: Int) {
    NORTH("north", 10),
    SOUTH("south", 20),
    EAST("east", 30),
    WEST("west", 40);

    fun printData() {
        println("Direction = $direction and Distance = $distance")

    }
}