package zip.sadan.util.twoD

import zip.sadan.util.direction.Quadrant
import kotlin.math.abs
import kotlin.math.sign

class Coord(val x: Int, val y: Int) {

    constructor(a: Int) : this(a, a)
    constructor(a: Pair<Int, Int>) : this(a.first, a.second)

    companion object {
        val ZERO = Coord(0, 0)
    }

    operator fun component1(): Int = x
    operator fun component2(): Int = y

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

    operator fun rangeTo(other: Coord): Iterable<Coord> {
        if (!canGoTo(other)) {
            throw IllegalArgumentException("with rangeTo, the other coord must be reachable with intervals of 1 from this coord")
        }
        val (ox, oy) = this - other
        if (ox == 0 && oy == 0) return emptyList()
        val t = if (ox == 0 || oy == 0) abs(ox + oy) else Math.min(abs(ox), abs(oy))
        val dx = -ox.sign
        val dy = -oy.sign
        val toRet = mutableListOf<Coord>()
        for (i in 0..t) {
            toRet.add(Coord(i * dx, i * dy))
        }

        return toRet.asIterable()
    }

    operator fun rangeUntil(other: Coord): Iterable<Coord> {
        if (!canGoTo(other)) {
            throw IllegalArgumentException("with rangeUntil, the other coord must be reachable with intervals of 1 from this coord")
        }
        val (ox, oy) = other
        if (ox == 0 && oy == 0) return emptyList()
        val t = abs(if (x == 0) y else x)
        val dx = -ox.sign
        val dy = -oy.sign
        val toRet = mutableListOf<Coord>()
        for (i in 0..<t) {
            toRet.add(Coord(i * dx, i * dy))
        }
        return toRet.asIterable()
    }

    /**
     * returns an iterable of all the coords bewteen this one, assuming they made a 2d window
     */
    infix fun to(bl: Coord): Iterable<Coord> {
        val (ox, oy) = bl
        val toRet = mutableListOf<Coord>()
        for(i in x..ox) {
            for (j in y..oy) {
                toRet.add(Coord(i, j))
            }
        }
        return toRet.asIterable()
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
