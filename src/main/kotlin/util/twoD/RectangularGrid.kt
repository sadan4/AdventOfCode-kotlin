package zip.sadan.util.twoD

import zip.sadan.util.array.isSquare
import zip.sadan.util.debug.ColoredString
import zip.sadan.util.direction.IHasShift
import kotlin.math.abs

typealias TGrid<T> = List<List<T>>
private fun Int.abs() = abs(this)
class RectangularGrid<T>(val arr: TGrid<T>, val rootCoord: Coord) {
    constructor(arr: TGrid<T>) : this(arr, Coord(0, 0))

    override fun toString(): String {
        var longest = 0
        return arr.reversed().map {
            it.map {
                val s = it.toString()
                if (s.length > longest) longest = s.length
                s
            }
        }.joinToString("\n") {
            it.joinToString(" ") {
                it.padEnd(longest)
            }
        }
    }

    fun formatHlPrint(vararg coords: Coord): String {
        var longest = 0
        val toRet = arr.map {
            it.map {
                val s = it.toString()
                if (s.length > longest) longest = s.length
                s
            }.toMutableList()
        }.toMutableList()
        coords.forEach { (x, y) ->
            toRet[y][x] = ColoredString(toRet[y][x]).setForegroundColor(255, 0, 124).toString()
        }
        return toRet.reversed().joinToString("\n") {
            it.joinToString(" ") {
                it.padEnd(longest)
            }
        }
    }

    init {
        require(arr.isNotEmpty()) { "Grid must have at least one row" }
        require(arr[0].isNotEmpty()) { "Grid must have at least one column" }
    }

    public val rootItem: T
        get() = arr[rootCoord.x][rootCoord.y]

    public val rootChord
        get() = rootCoord

    public val height
        get() = arr.size

    public val width
        get() = arr[0].size

    public fun isSquare(): Boolean = arr.isSquare()

    public fun toSquareGrid(): SquareGrid<T> {
        return SquareGrid(arr)
    }

    public fun coordsOfLine(from: Coord, direction: IHasShift): List<Coord> {
        val (x, y) = from
        val (sx, sy) = direction.toShift()
        val height = this.height - 1.0;
        val width = this.width - 1.0
        return (from..(from + Coord(
            (sx * abs(x - ((sx * width) / 2) - (width / 2))).toInt(),
            (sy * abs(y - ((sy * height) / 2) - (height / 2))).toInt()
        ))).toList().map {
            it + from
        }
    }

    public operator fun get(index: Int): List<T> = arr[index]
}