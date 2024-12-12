package zip.sadan.util.direction

interface IHasInverse<T> {
    /**
     * if `this == N` then returns `E to W`
     */
    fun inverse(): Pair<T, T>
}