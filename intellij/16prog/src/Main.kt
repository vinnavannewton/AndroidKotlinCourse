//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
   var number = 1
    var lastNUmber = 20
    var evenNUmberCounter = 0
    while (number <= lastNUmber) {
        number++

        if(!isEvenNumber(number)) {
            continue

        }
        evenNUmberCounter++
        println(number)
    }
    println("total number of even number found = $evenNUmberCounter")
}

fun isEvenNumber(number: Int): Boolean {
    if((number % 2) == 0) {
        return true
    } else {
        return false
    }
}