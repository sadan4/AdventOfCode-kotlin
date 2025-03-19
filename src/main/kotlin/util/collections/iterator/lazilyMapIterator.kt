package zip.sadan.util.collections.iterator
interface AllIter<T>: Iterator<T>, Iterable<T> {
}
inline fun <T, R> Iterable<T>.lazilyMap(crossinline fn: (T) -> R): AllIter<R> {
    val old = this.iterator()
    return object : AllIter<R> {
        override fun hasNext(): Boolean = old.hasNext()
        override fun next(): R = fn(old.next())
        override fun iterator(): Iterator<R> {
            return this
        }
    }
}