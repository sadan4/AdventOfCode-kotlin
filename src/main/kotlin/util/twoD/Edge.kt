package zip.sadan.util.twoD

import zip.sadan.util.direction.Linear

@JvmName("toEdgeCoordLinear")
fun Pair<Coord, Linear>.toEdge() = Edge(this.first, this.second);
@JvmName("toEdgeLinearCoord")
fun Pair<Linear, Coord>.toEdge() = Edge(this.second, this.first);
@JvmName("toEdgeCoordCoord")
fun Pair<Coord, Coord>.toEdge() = Edge(this.first, this.second);

class Edge(val a: Coord, val b: Coord) {
    constructor(a: Coord, b: Linear) : this(a, a + b.toShift());

    override fun hashCode(): Int {
        var result = a.hashCode();
        result = 31 * result + b.hashCode();
        return result;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true;
        if (javaClass != other?.javaClass) return false;

        other as Edge;

        return (a == other.a && b == other.b) || (a == other.b && b == other.a);
    }

    fun countIn(collection: Collection<Coord>): Int = this.countIn {
        it in collection
    }

    fun countIn(grid: RectangularGrid<*>): Int = this.countIn {
        it in grid
    }

    private inline fun countIn(contains: (c: Coord) -> Boolean): Int {
        var count = 0;
        if (contains(this.a)) {
            count++;
        }
        if (contains(this.b)) {
            count++;
        }
        return count;
    }

    operator fun plus(shift: Coord): Edge = Edge(this.a + shift, this.b + shift);

    operator fun minus(shift: Coord): Edge = this + -shift;
}