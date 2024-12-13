package zip.sadan.util.direction

import org.jetbrains.annotations.Contract

interface IHasCompliment<T> {
    /**
     * if `this == N` then returns `S`
     */
    @Contract(pure = true)
    fun compliment(): T
}
