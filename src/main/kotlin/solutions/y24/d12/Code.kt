package zip.sadan.solutions.y24.d12

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.direction.Linear
import zip.sadan.util.pair.forEach
import zip.sadan.util.pair.map
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid

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
private fun <T> HashSet<Coord>.perimeter(grid: RectangularGrid<T>): Int {
    val possible = HashSet<Pair<Linear, Coord>>()
    var edges = 0
    for (coord in this) {
        for (dir in Linear.values()) {
            val neighbor = coord + dir.toShift()
            if (neighbor !in this) {
                dir.inverse().forEach {
                    possible.add(it to neighbor)
                }
            }
        }
    }
    return edges
}

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 12

    @UseFile("input.txt")
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

    override fun part2(input: TInput): Any? {
    }
}