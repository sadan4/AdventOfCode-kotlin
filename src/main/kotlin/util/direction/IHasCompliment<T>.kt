package zip.sadan.util.direction

interface IHasCompliment<T> {
    /**
     * if `this == N` then returns `S`
     */
    fun compliment(): T
}