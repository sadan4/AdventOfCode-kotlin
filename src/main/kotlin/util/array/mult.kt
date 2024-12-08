package zip.sadan.util.array

fun <T> List<T>.mult(n: Int): List<List<T>> = Fill(n, this)