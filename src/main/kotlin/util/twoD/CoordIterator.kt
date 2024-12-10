package zip.sadan.util.twoD

abstract class CoordIterator(): Iterator<Coord>, Iterable<Coord> {
    companion object {
        val empty = object: CoordIterator() {
            override fun hasNext(): Boolean = false
            override fun next(): Coord = throw NoSuchElementException()
        }
    }

    fun isEmpty(): Boolean = !this.hasNext()
    fun isNotEmpty(): Boolean = !isEmpty()
    abstract override fun hasNext(): Boolean

    abstract override fun next(): Coord

    override fun iterator(): Iterator<Coord> = this
}