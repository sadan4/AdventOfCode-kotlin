package zip.sadan.util.array

import java.io.Serializable

class Coord(val x: Int, val y: Int) {
    operator fun component1(): Int = x
    operator fun component2(): Int = y

    override fun toString(): String {
        return "($x, $y)"
    }
}