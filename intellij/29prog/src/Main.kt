//inner classes:Used when close relationship between 2 classes
fun main() {
    val listView = ListView(arrayOf("Name 1", "Name 2", "Name 3"));
    listView.ListViewItem().displayItems(2);



}

class ListView(val items: Array<String>) {
    inner class ListViewItem() {
        fun displayItems(position: Int) {
            println(items[position])
        }
    }
}