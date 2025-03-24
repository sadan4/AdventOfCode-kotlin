package zip.sadan.util.direction

interface IHasInverse<T> {
    /**
     * if `this == N` then returns `E to W`
     *
     * [Linear.N] or [Linear.E] will always be first
     */
    fun inverse(): Pair<T, T>
}