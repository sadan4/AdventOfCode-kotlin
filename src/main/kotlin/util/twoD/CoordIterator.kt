package zip.sadan.util.twoD


public typealias CIterator = CoordIterator<Coord>

abstract class CoordIterator<in T>(): Iterator<@UnsafeVariance T>, Iterable<@UnsafeVariance T> {
    companion object {
        val empty: CoordIterator<Any> = object: CoordIterator<Any>() {
            override fun hasNext() = false
            override fun next() = throw NoSuchElementException()
        }
    }

    fun isEmpty(): Boolean = !this.hasNext()
    fun isNotEmpty(): Boolean = !isEmpty()
    abstract override fun hasNext(): Boolean

    abstract override fun next(): @UnsafeVariance T

    override fun iterator(): Iterator<@UnsafeVariance T> = this
}