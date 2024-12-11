package zip.sadan.solutions.y24.d12

import zip.sadan.Solution
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import java.io.File

typealias TInput = List<String>

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 12

    override fun part1(input: TInput): Any? {
        val plants = input.map { it.toCharArray().toList() }
        val grid = RectangularGrid(plants)
        val plots = HashSet<HashSet<Coord>>()
        val working = grid.clone()
        for(working.)
    }

    override fun part2(input: TInput): Any? {
        return ""
    }
}