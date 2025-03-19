package zip.sadan.util.collections.list

fun <T> Fill(len: Int, func: (index: Int) -> T): List<T> {
    val list = ArrayList<T>()
    for (i in 0 until len) {
        list.add(func(i))
    }
    return list
}
fun <T> Fill(len: Int, value: T): List<T> {
    val list = ArrayList<T>()
    for (i in 0 until len) {
        list.add(value)
    }
    return list
}