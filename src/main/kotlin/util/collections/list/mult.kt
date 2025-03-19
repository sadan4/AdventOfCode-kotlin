package zip.sadan.util.collections.list

fun <T> List<T>.mult(n: Int): List<List<T>> = Fill(n, this)