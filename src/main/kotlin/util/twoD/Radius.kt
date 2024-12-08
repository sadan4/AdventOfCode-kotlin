package zip.sadan.util.twoD

import zip.sadan.util.array.isSquare

enum class Direction {
    N, NE, E, SE, S, SW, W, NW
}

class Radius<T>(private var arr: List<List<T>>) {
    public override fun toString(): String {
        return arr.joinToString("\n") { it.joinToString("") }
    }

    public val center = (arr.size - 1) / 2
    public val centerItem: T
        get() = arr[center][center]

    init {
        if (arr.isEmpty()) throw IllegalArgumentException("the 2Darray must not be empty")
        if (!arr.isSquare()) throw IllegalArgumentException("the 2Darray must be a square")
    }

    fun getLine(dir: Direction, withCenter: Boolean = false): String {
        var toRet = if (withCenter) "$centerItem" else ""
        when (dir) {
            Direction.E -> {
                for (i in center + 1 until arr.size)
                    toRet += arr[center][i]
            }

            Direction.W -> {
                for (i in center - 1 downTo 0)
                    toRet += arr[center][i]
            }

            Direction.N -> {
                for (i in center - 1 downTo 0)
                    toRet += arr[i][center]
            }

            Direction.S -> {
                for (i in center + 1 until arr.size)
                    toRet += arr[i][center]
            }

            Direction.NW -> {
                for (i in center - 1 downTo 0)
                    toRet += arr[i][i]
            }

            Direction.SE -> {
                for (i in center + 1 until arr.size)
                    toRet += arr[i][i]
            }

            Direction.NE -> {
                for (i in center - 1 downTo 0)
                    toRet += arr[i][arr.size - 1 - i]
            }

            Direction.SW -> {
                for (i in center + 1 until arr.size)
                    toRet += arr[i][arr.size - 1 - i]
            }
        }
        return toRet
    }

}