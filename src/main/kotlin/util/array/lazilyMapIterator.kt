package zip.sadan.util.array

inline fun <T, R> Iterator<T>.lazilyMap(crossinline fn: (T) -> R): Iterator<R> {
    val old = this
    return object : Iterator<R> {
        override fun hasNext(): Boolean = old.hasNext()
        override fun next(): R = fn(old.next())
    }
}