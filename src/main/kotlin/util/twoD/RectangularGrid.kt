package zip.sadan.util.twoD

import zip.sadan.util.collections.list.isSquare
import zip.sadan.util.debug.ColoredString
import zip.sadan.util.direction.IHasShift
import zip.sadan.util.collections.iterator.lazilyMap
import java.util.Stack
import kotlin.math.abs

typealias TGrid<T> = List<List<T>>

private fun Int.abs() = abs(this)
open class RectangularGrid<T>(arr: TGrid<T>, val rootCoord: Coord) : Collection<T> {

    constructor(arr: TGrid<T>) : this(arr, Coord(0, 0))

    public val height
        get() = arr.size

    public val width
        get() = arr[0].size

    init {
        require(arr.isNotEmpty()) { "Grid must have at least one row" }
        require(arr[0].isNotEmpty()) { "Grid must have at least one column" }
    }

    private val arr: MutableList<MutableList<T>> = arr
        .map {
            it.toMutableList()
        }
        .toMutableList()
    override val size: Int
        get() = width * height

    override fun isEmpty(): Boolean {
        return false
    }

    override fun iterator(): Iterator<T> = (Coord.ZERO to Coord(width - 1, height - 1)).lazilyMap {
        this[it]
    }

    fun coords(): CIterator = (Coord.ZERO to Coord(width - 1, height - 1))

    fun contigiousLinear(start: Coord) = contigiousLinear(start) {
        it == this[start]
    }

    fun contigiousLinear(start: Coord, eq: (T) -> Boolean): HashSet<Coord> {
        val startingVal = this[start]
        val valid = hashSetOf(start)
        val toVisit = Stack<Coord>()
        toVisit.addAll(start.linearNeighbors())
        while (toVisit.isNotEmpty()) {
            val cur = toVisit.pop()
            if (cur in valid) continue
            if (cur !in this) continue
            if (!eq(this[cur])) continue
            valid.add(cur)
            toVisit.addAll(cur.linearNeighbors())
        }
        return valid
    }

    override fun containsAll(elements: Collection<T>): Boolean = elements.all {
        this.contains(it)
    }

    override fun contains(element: T): Boolean = getPosOf(element) != null

    override fun toString(): String {
        var longest = 0
        return arr
            .map {
                it.map {
                    val s = it.toString()
                    if (s.length > longest) longest = s.length
                    s
                }
            }
            .joinToString("\n") {
                it.joinToString(" ") {
                    it.padEnd(longest)
                }
            }
    }

    fun formatHlPrint(coords: Iterable<Coord>): String {
        var longest = 0
        val toRet = arr
            .map {
                it
                    .map {
                        val s = it.toString()
                        if (s.length > longest) longest = s.length
                        s
                    }
                    .toMutableList()
            }
            .toMutableList()
        coords.forEach { c ->
            toRet[c] = ColoredString(toRet[c])
                .setForegroundColor(255, 0, 124)
                .toString()
        }
        return toRet.joinToString("\n") {
            it.joinToString(" ") {
                it.padEnd(longest)
            }
        }
    }


    public val rootItem: T
        get() = arr[rootCoord.x][rootCoord.y]

    public val rootChord
        get() = rootCoord


    public fun isSquare(): Boolean = arr.isSquare()

    public fun toSquareGrid(): SquareGrid<T> {
        return SquareGrid(arr)
    }

    /**
     * returns the list of coordinates beteen this point and the edge of the grid in the direction specified
     *
     * the returned coords are inclusive
     */
    fun coordsOfLine(at: Coord, direction: IHasShift): Iterable<Coord> = coordsOfLine(at, direction.toShift());

    fun coordsOfLine(at: Coord, direction: Coord): Iterable<Coord> {
        val (x, y) = at
        val (sx, sy) = direction
        val height = this.height - 1.0;
        val width = this.width - 1.0
        return at lineTo (at + Coord(
            (sx * abs(x - ((sx * width) / 2) - (width / 2))).toInt(),
            (sy * abs(y - ((sy * height) / 2) - (height / 2))).toInt()
        ))
    }

    /**
     * includes the one that breaks
     * see: [coordsUntil]
     */
    fun coordsTo(at: Coord, direction: IHasShift, fn: (T) -> Boolean): List<Coord> {
        var cur = at
        for (c in coordsOfLine(at, direction)) {
            cur = c
            if (fn(this[c])) {
                break
            }
        }
        return (at..cur).toList()
    }

    fun coordsUntil(at: Coord, direction: IHasShift, el: T): CIterator = coordsUntil(at, direction) {
        arr[it] == el
    }

    /**
     * doesn't include the one that breaks
     *
     * includes [at]
     *
     * see: [coordsTo]
     */
    inline fun coordsUntil(at: Coord, direction: IHasShift, fn: (Coord) -> Boolean): CIterator {
        var cur: Coord = at
        for (c in coordsOfLine(at, direction)) {
            if (c !in this) break
            if (fn(c)) {
                break
            }
            cur = c
        }
        return at lineTo cur
    }

    fun replace(at: Coord, value: T) = arr.set(at, value)

    fun replaceRadius(at: Coord, radius: Int, value: T) = replace2D(at - Coord(radius), at + Coord(radius), value)

    fun replace2D(TL: Coord, BR: Coord, value: T) {
        for (y in TL.y..BR.y) {
            for (x in TL.x..BR.x) {
                arr[y][x] = value
            }
        }
    }

    fun replaceAll(at: List<Coord>, value: T) = at.map {
        arr.set(it, value)
    }

    fun mapAll(at: List<Coord>, fn: (T) -> T) = at.map {
        arr.set(it, fn(arr[it]))
    }

    fun getPosOf(value: T): Coord? {
        arr.forEachIndexed { y, row ->
            row.forEachIndexed { x, el ->
                if (el == value) return Coord(x, y)
            }
        }
        return null
    }

    fun getAll(value: T): List<Coord> {
        val toRet = mutableListOf<Coord>()
        arr.forEachIndexed { y, row ->
            row.forEachIndexed { x, el ->
                if (el == value) toRet.add(Coord(x, y))
            }
        }
        return toRet
    }

    fun count(value: T): Int = arr
        .toList()
        .sumOf {
            it.count {
                it == value
            }
        }

    fun isOnEdge(at: Coord): Boolean = at.x == 0 || at.y == 0 || at.x == width - 1 || at.y == height - 1

    fun clone(): RectangularGrid<T> {
        return RectangularGrid(arr.map { it.toList() }, rootCoord)
    }

    public operator fun contains(c: Coord): Boolean {
        if (c.x < 0 || c.y < 0) return false
        if (c.x >= width || c.y >= height) return false
        return true
    }

    public operator fun get(index: Int): List<T> = arr[index]

    public operator fun get(c: Coord): T = arr[c.y][c.x]
    public operator fun set(c: Coord, value: T) {
        arr[c.y][c.x] = value
    }

    public open fun swap(a: Coord, b: Coord) {
        val tmp = this[a];
        this[a] = this[b];
        this[b] = tmp;
    }
}