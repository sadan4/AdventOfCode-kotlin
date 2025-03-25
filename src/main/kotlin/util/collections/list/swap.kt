package zip.sadan.util.collections.list;

fun <T> MutableList<T>.swap(index: Int, other: Int): Unit {
    val tmp = this[other];
    this[other] = this[index]
    this[index] = tmp
}