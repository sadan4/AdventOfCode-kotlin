package zip.sadan.util.collections.pair

fun <T> Pair<T, T>.all(other: T): Boolean {
    return this.first == other && this.second == other
}

/**
 * will short circuit
 */
fun <T> Pair<T, T>.all(fn: (T) -> Boolean): Boolean {
    if(!fn(this.first)) return false
    if(!fn(this.second)) return false
    return true
}