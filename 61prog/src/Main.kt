fun main() {
    val loginButton = Button("Login", 1) {
        //login user
    }

    val signupButton = Button("Login", 1) {
        //signup user
    }

}

class Button(val text: String, val id: Int, val onClickListener: () -> Unit)

