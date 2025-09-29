package zip.sadan.util.collections.list

fun <T> MutableList<T>.popHead() = this.removeFirst()


fun <T> List<T>.get(index: String) = this.get(index.toInt());

