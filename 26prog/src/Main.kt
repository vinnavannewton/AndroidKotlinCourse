//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
//    //singleton
//    val instance = DataBase.getInstance()
//    val instance1 = DataBase.getInstance()
//    println(instance)
//    println(instance1)
//
//

//}
//
//class DataBase private constructor() {
//    companion object {
//        private var instance: DataBase? = null
//        fun getInstance(): DataBase? {
//            if(instance == null) {
//                instance = DataBase()
//            }
//            return instance
//        }
//    }
//}


fun main() {
    println(DataBase1)
    println(DataBase1)

}}
object DataBase1 {
    init {
        println("Database created")
    }

}