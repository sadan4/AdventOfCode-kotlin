package zip.sadan.solutions.y24.d12

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.pair.map
import zip.sadan.util.debug.Solved
import zip.sadan.util.direction.Linear
import zip.sadan.util.collections.set.containsAny
import zip.sadan.util.collections.stack.toStack
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.Edge
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.toEdge
import kotlin.collections.HashSet

typealias TInput = List<String>

private fun HashSet<Coord>.area() = this.size
private fun HashSet<Coord>.perimeter(): Int {
    var edges = 0
    for (coord in this) {
        for (neighbor in coord.linearNeighbors()) {
            if (neighbor !in this) {
                edges++
            }
        }
    }
    return edges
}

fun normalizeEdgeDir(dir: Linear): Linear = when (dir) {
    Linear.E, Linear.W -> Linear.E
    Linear.N, Linear.S -> Linear.N
}

private fun <T> HashSet<Coord>.edges(grid: RectangularGrid<T>): Int {
    if (this.size == 1) {
        return 4;
    }
    var edgeCount = 0;

    val countedEdges = HashSet<Edge>();

    val edgePieces = this
        .filter {
            this.containsAny(it.linearNeighbors())
        }
        .toStack();

    while (edgePieces.isNotEmpty()) {
        val cur = edgePieces.pop();

        cur
            .linearNeighborsWithDir()
            .filter {
                val notBeenCounted = Edge(cur, it.second) !in countedEdges;
                val hasAnEdge = it.second !in this;
                notBeenCounted && hasAnEdge
            }
            .forEach {
                val e = Edge(cur, it.second);

                if (e in countedEdges) {
                    error("This should have been filtered out by the filter");
                }

                countedEdges.add(e);

                val (fwdShift, revShift) = it.first
                    .inverse()
                    .map {
                        it.toShift()
                    }
                var cur = e + fwdShift;
                while (true) {
                    if (cur.countIn(this) != 1) {
                        // either both are out, or both are in, either way it's over
                        break;
                    }
                    if (cur.b in this) {
                        // needed for cases when a is now not over anything, but b is
                        break;
                    }
                    // anything where we are on something we've already counted should have been
                    // filtered out by the previous function
                    if (cur in countedEdges) {
                        error("This shouldn't be possible")
                    }
                    countedEdges.add(cur);
                    cur += fwdShift;
                }
                cur = e + revShift;
                while (true) {
                    if (cur.countIn(this) != 1) {
                        // either both are out, or both are in, either way it's over
                        break;
                    }
                    if (cur.b in this) {
                        // needed for cases when a is now not over anything, but b is
                        break;
                    }
                    // anything where we are on something we've already counted should have been
                    // filtered out by the previous function
                    if (cur in countedEdges) {
                        error("This shouldn't be possible")
                    }
                    countedEdges.add(cur);
                    cur += revShift;
                }
                edgeCount++;
            }
    }
    return edgeCount;
}

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 12

    @Solved("1446042")
    @UseFile("./input.txt")
    override fun part1(input: TInput): Any? {
        val plants = input.map {
            it
                .toCharArray()
                .toList()
        }
        val grid = RectangularGrid(plants)
        val plots = HashSet<HashSet<Coord>>()
        val tmpSeen = HashSet<Coord>()
        for (coord in grid.coords()) {
            if (coord in tmpSeen) continue
            val newCoords = grid.contigiousLinear(coord)
            plots.add(newCoords)
            tmpSeen.addAll(newCoords)
        }
        return plots.sumOf {
            it.area() * it.perimeter()
        }
    }

    @Solved("902742")
    @UseFile("input.txt")
    override fun part2(input: TInput): Any? {
        val plants = input.map {
            it
                .toCharArray()
                .toList()
        }
        val grid = RectangularGrid(plants)
        val plots = HashSet<HashSet<Coord>>()
        val tmpSeen = HashSet<Coord>()
        for (coord in grid.coords()) {
            if (coord in tmpSeen) continue
            val newCoords = grid.contigiousLinear(coord)
            plots.add(newCoords)
            tmpSeen.addAll(newCoords)
        }
        return plots.sumOf {
            it.area() * it.edges(grid)
        }
    }
}