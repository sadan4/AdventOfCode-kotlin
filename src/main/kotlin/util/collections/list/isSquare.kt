package zip.sadan.util.collections.list

fun <T> List<List<T>>.isSquare(): Boolean {
    val size = this.size
    return this.all { it.size == size }
}