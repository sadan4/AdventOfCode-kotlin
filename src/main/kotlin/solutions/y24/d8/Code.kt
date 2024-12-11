package zip.sadan.solutions.y24.d8

import zip.sadan.Solution
import zip.sadan.util.twoD.RectangularGrid
import java.io.File

typealias TInput = List<String>

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 8

    override fun part1(input: TInput): Any? {
        val grid = RectangularGrid<Char>(input.map { it.toCharArray().toList() })
    }

    override fun part2(input: TInput): Any? {
        return ""
    }
}