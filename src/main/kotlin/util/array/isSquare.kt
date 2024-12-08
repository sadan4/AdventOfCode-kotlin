package zip.sadan.util.array

fun <T> List<List<T>>.isSquare(): Boolean {
    val size = this.size
    return this.all { it.size == size }
}