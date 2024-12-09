package zip.sadan.util.pair

fun <T> Pair<T, T>.any(other: T): Boolean {
    return this.first == other || this.second == other
}

/**
 * will short circuit
 */
fun <T> Pair<T, T>.any(fn: (T) -> Boolean): Boolean {
    if(fn(this.first)) return true
    if(fn(this.second)) return true
    return false
}