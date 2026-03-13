//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    sayHello("Alex", 26)
    val hasInternetConnection = true
    if (hasInternetConnection) {
        getData("some text")
    } else {
        showMessage()
    }
}

fun sayHello(name: String, age: Int) { //always say type
    var number = age
    println("Hello $name ur age is $age")
}

fun getData(data: String) {
    println("your data is $data")
}

fun showMessage() {
    println("no internet connection")
}