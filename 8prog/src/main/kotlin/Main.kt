//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //elvis operator
    var text: String? = null
    text = "smthg"
    println(text!!.length) // if not null and has length it outputs length else throw null point exception error

// it basically says strictly its not null else it will crash

    var text2: String = text ?: "the variable is null"
    // assigns text to text2 is text exists if doesnt ie null then the string is assigned
    println(text2)
}
