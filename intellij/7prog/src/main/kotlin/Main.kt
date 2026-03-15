//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //null
    // variables are non nullable by default to reduce run time errors.cant put var text: String = null
    var text: String? = null
    //question mark after data type will make the var nullable

    println(text)

    text = "Name"
    if(text != null) {
        println(text.length)
    } else
        println("variable is null")
    // u cant just do println(text.length) it will show a warning only after u check using some condition the warning will go go try urself do without if statement
    text = null
    // to automatic check do this
    println(text?.length) // if length exists it outputs else ouputs null

}