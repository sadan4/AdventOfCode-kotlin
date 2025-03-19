package zip.sadan.solutions.y24.d12

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
import zip.sadan.util.direction.Linear
import zip.sadan.util.collections.set.containsAny
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

private fun <T> HashSet<Coord>.perimeter2(grid: RectangularGrid<T>): Int {

    val countedEdges: HashSet<TEdge> = HashSet();

    // coords that have a border with an edge
    var edgePieces = this.filter {
        this.containsAny(it.linearNeighbors())
    }
        .toMutableSet()
    while(edgePieces.isNotEmpty()) {

    }

    return -1;
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

//    @Solved("80")
    @UseFile("./test1.txt")
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
            it.area() * it.perimeter2(grid)
        }
    }
}