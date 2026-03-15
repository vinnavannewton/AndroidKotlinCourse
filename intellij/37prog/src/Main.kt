//Object expression
fun main () {
    val loginButton = Button("Login", 2323,object : OnClickListener {
        override fun onClick() {
            //login code

        }
    })

    val signupButton = Button("Signup", 2345, object : OnClickListener {
        override fun onClick() {
            //signup code

        }

    })

}

class Button(val text: String, val id: Int, onClickListener: OnClickListener)

class ClickListener() :OnClickListener {
    override fun onClick() {

    }
}

interface OnClickListener {
    fun onClick() {


    }
}