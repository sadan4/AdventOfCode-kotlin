package zip.sadan.solutions.y24.d12

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.array.lazilyMap
import zip.sadan.util.direction.Linear
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

typealias TEdge = Pair<Linear, Coord>

private fun <T> HashSet<Coord>.perimeter2(grid: RectangularGrid<T>): Int {
    val possible = HashSet<TEdge>()
    val counted = HashSet<TEdge>()
    var edges = 0
    for (coord in this) {
        for (dir in Linear.entries) {
            val neighbor = coord + dir.toShift()
            if (neighbor !in this && neighbor in grid) {
                possible.add(dir.inverse().first to neighbor)
            }
        }
    }
    possible.forEach {
        if (it in counted) return@forEach
        if(possible.size == counted.size) return edges
        val (dir, coord) = it
        val compliment = dir.compliment()
        val line: List<TEdge> = (if (coord !in grid) emptyList() else grid
            .coordsUntil(coord, dir) {
                dir to it !in possible
            }
            .lazilyMap { dir to it }) + if (coord !in grid) emptyList() else (grid
            .coordsUntil(
                coord, compliment
            ) {
                dir to it !in possible
            }
            .lazilyMap { dir to it }
            .drop(1))
        counted.addAll(line)
        edges++
    }
    return edges;

}

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 12

    @UseFile("./test1.txt")
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