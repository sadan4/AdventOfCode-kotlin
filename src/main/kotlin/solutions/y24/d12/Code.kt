package zip.sadan.solutions.y24.d12

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.pair.map
import zip.sadan.util.collections.pair.mapFirst
import zip.sadan.util.debug.Solved
import zip.sadan.util.direction.Linear
import zip.sadan.util.collections.set.containsAny
import zip.sadan.util.collections.set.pop
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
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

typealias TEdge = Pair<Linear, Coord>

fun normalizeEdgeDir(dir: Linear): Linear = when (dir) {
    Linear.E, Linear.W -> Linear.E
    Linear.N, Linear.S -> Linear.N
}

private fun <T> HashSet<Coord>.edges(grid: RectangularGrid<T>): Int {
    // cover edge case I cant be bothered to code into the logic
    // It's a bug with how the edge pieces get calculated
    if(this.size == 1) {
        return 4
    }
    var edgeCount = 0;

    val countedEdges: HashSet<TEdge> = HashSet();

    // coords that have a border with an edge
    var edgePieces = this
        .filter {
            this.containsAny(it.linearNeighbors())
        }
        .toMutableSet()
    while (edgePieces.isNotEmpty()) {
        val cur = edgePieces.pop();
        cur
            .linearNeighborsWithDir()
            .filter {
                // filter that it is not part of an edge that has been counted && it is not a piece without any edges
                it.mapFirst {
                    normalizeEdgeDir(it.inverse().first)
                } !in countedEdges && it.second !in this
            }
            .forEach {
                val normDir = normalizeEdgeDir(it.first.inverse().first)
                // sanity checks
                // it.first is the shift, not the direction of this edge itself
                // we need to check using the direction
                if (normDir to it.second in countedEdges) {
                    // we have already seen this starting from a diff edge
                    return@forEach;
                }
                // it must be an edge, add it
                countedEdges.add(normDir to it.second)
                // it.first repersents the shift from the originating piece that caused this to be an edge
                // we need to go the opposite and the inverse of the opposite and check until we fail
                // EG if it.first is Linear.E, we need to go Linear.{N,S}
                val (fwdShift, revShift) = it.first
                    .inverse()
                    .map {
                        it.toShift()
                    }
                var cur = it.second + fwdShift;
                while (true) {
                    // ensure that we're still adajacent to this blob
                    // also covers going off the grid
                    if (cur + it.first.compliment().toShift() !in this) {
                        break;
                    }
                    if (cur in this) {
                        // then cur is a part of this area itself, and we stop in this dir
                        break;
                    }
                    // if we are somehow on something we already counted, shouldnt be possible as it should have been filtered out by the previous check
                    if (normDir to cur in countedEdges) {
                        error("How")
                    }
                    countedEdges.add(normDir to cur);
                    cur += fwdShift;
                }
                cur = it.second + revShift;
                while (true) {
                    // ensure that we're still adajacent to this blob
                    // also covers going out of the grid
                    if (cur + it.first.compliment().toShift() !in this) {
                        break;
                    }
                    if (cur in this) {
                        break;
                    }
                    if (normDir to cur in countedEdges) {
                        error("How")
                    }
                    countedEdges.add(normDir to cur);
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

    @Solved("236")
    @UseFile("./test4.txt")
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
            println(grid.formatHlPrint(it))
            println("Has a Perimeter of ${it.edges(grid)}")
            it.area() * it.edges(grid)
        }
    }
}