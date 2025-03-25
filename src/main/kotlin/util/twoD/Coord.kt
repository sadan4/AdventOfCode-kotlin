package zip.sadan.util.twoD

import zip.sadan.util.direction.Diagonal
import zip.sadan.util.direction.Linear
import zip.sadan.util.direction.Quadrant
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.sign
private fun closestButNotZero(ox: Int, oy: Int): Int = if (ox == 0 || oy == 0) abs(ox + oy) else Math.min(abs(ox), abs(oy))

public operator fun Int.times(other: Coord): Coord = other * this;

public operator fun Int.plus(other: Coord): Coord = other + this

public operator fun Int.minus(other: Coord): Coord {
    return Coord(this - other.x, this - other.y);
}

class Coord(val x: Int, val y: Int) {

    constructor(a: Int) : this(a, a)
    constructor(a: Pair<Int, Int>) : this(a.first, a.second)

    companion object {
        val ZERO = Coord(0, 0)
    }

    operator fun component1(): Int = x
    operator fun component2(): Int = y

    fun linearNeighborsWithDir(): List<Pair<Linear, Coord>> {
        return listOf(
            Linear.N to this + Linear.N.toShift(),
            Linear.S to this + Linear.S.toShift(),
            Linear.E to this + Linear.E.toShift(),
            Linear.W to this + Linear.W.toShift()
        )
    }

    fun linearNeighbors(): List<Coord> = this.linearNeighborsWithDir().map {
        it.second
    }
    fun diagonalNeighbors(): List<Coord> {
        return listOf(
            this + Diagonal.NE.toShift(),
            this + Diagonal.NW.toShift(),
            this + Diagonal.SE.toShift(),
            this + Diagonal.SW.toShift()
        )
    }
    fun neighbors(): List<Coord> = linearNeighbors() + diagonalNeighbors()

    fun linearEdges() = this.linearNeighbors()
        .map {
            Edge(this, it)
        };

    fun toPair(): Pair<Int, Int> = Pair(x, y)

    fun getQuadrant(): Quadrant {
        return when {
            x > 0 && y > 0 -> Quadrant.I
            x < 0 && y > 0 -> Quadrant.II
            x < 0 && y < 0 -> Quadrant.III
            x > 0 && y < 0 -> Quadrant.IV
            else -> throw IllegalArgumentException("x or y must be non-zero")
        }
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    operator fun plus(other: Coord): Coord {
        return Coord(x + other.x, y + other.y)
    }

    operator fun minus(other: Coord): Coord {
        return Coord(x - other.x, y - other.y)
    }

    operator fun plus(other: Int): Coord {
        return Coord(x + other, y + other)
    }

    operator fun minus(other: Int): Coord {
        return Coord(x - other, y - other)
    }

    operator fun times(other: Coord): Coord {
        return Coord(x * other.x, y * other.y)
    }

    operator fun times(other: Int): Coord {
        return Coord(x * other, y * other)
    }

    operator fun unaryMinus(): Coord {
        return Coord(-x, -y)
    }

    private fun canGoTo(other: Coord): Boolean {
        val (ox, oy) = other
        if ((ox - x) * (oy - y) == 0) return true
        if (ox == 0 || oy == 0) return true
        return abs(ox / oy) == abs(x / y)
    }

    infix fun lineTo(other: Coord): CIterator {
        if (!canGoTo(other)) {
            throw IllegalArgumentException("with lineTo, the other coord must be reachable with intervals of 1 from this coord")
        }
        val (ox, oy) = this - other
        if (ox == 0 && oy == 0) return CoordIterator.empty
        val t = closestButNotZero(ox, oy)
        val dx = -ox.sign
        val dy = -oy.sign
        return object : CIterator() {
            private var i = 0
            override fun hasNext(): Boolean = i <= t
            override fun next(): Coord = Coord(x + (i * dx), y + (i++ * dy))
        }
    }

    operator fun rangeTo(other: Coord): CIterator {
        if (!canGoTo(other)) {
            throw IllegalArgumentException("with rangeTo, the other coord must be reachable with intervals of 1 from this coord")
        }
        val (ox, oy) = this - other
        if (ox == 0 && oy == 0) return CoordIterator.empty
        val t = closestButNotZero(ox, oy)
        val dx = -ox.sign
        val dy = -oy.sign
        return object : CIterator() {
            private var i = 0
            override fun hasNext(): Boolean = i <= t
            override fun next(): Coord = Coord(i * dx, i++ * dy)
        }
    }

    operator fun rangeUntil(other: Coord): CIterator {
        if (!canGoTo(other)) {
            throw IllegalArgumentException("with rangeUntil, the other coord must be reachable with intervals of 1 from this coord")
        }
        val (ox, oy) = this - other
        if (ox == 0 && oy == 0) return CoordIterator.empty
        val t = closestButNotZero(ox, oy)
        val dx = -ox.sign
        val dy = -oy.sign
        return object : CIterator() {
            private var i = 0
            override fun hasNext(): Boolean = i < t
            override fun next(): Coord = Coord(i * dx, i++ * dy)
        }
    }

    /**
     * returns an iterable of all the coords bewteen this one, assuming they made a 2d window
     */
    infix fun to(bl: Coord): CIterator {
        val (ox, oy) = bl
        val toRet = ArrayList<Coord>((x - ox) * (y - oy))
        for(i in x..ox) {
            for (j in y..oy) {
                toRet.add(Coord(i, j))
            }
        }
        return object : CIterator() {
            private var i = x;
            private var j = y;
            override fun hasNext(): Boolean = i <= ox && j <= oy

            override fun next(): Coord = if (j == oy) {
                j = y
                Coord(i++, oy)
            } else {
                Coord(i, j++)
            }
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

typealias MGrid<T> = MutableList<MutableList<T>>

operator fun <T> List<List<T>>.get(c: Coord): T = this[c.y][c.x]
operator fun <T> MGrid<T>.set(c: Coord, value: T): T = this[c.y].set(c.x, value)