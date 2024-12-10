package zip.sadan.solutions.y24.d6

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.Cycle
import zip.sadan.util.direction.Linear
import zip.sadan.util.input.makeLines
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid

typealias TInput = String

private fun makeRotation(): Cycle<Linear> {
    return Cycle(listOf(Linear.S, Linear.E, Linear.N, Linear.W))
}


class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 6
    private fun p1Helper(grid: RectangularGrid<Char>): Set<Coord> {
        val seen = hashSetOf<Coord>()
        var curPos = grid.getPosOf('^')!!
        val r = makeRotation()
        while (true) {
            val dir = r.use()
            val line = grid.coordsUntil(curPos, dir, '#')
            if (line.isEmpty()) break
            seen.addAll(line)
            curPos = line.last()
            if (grid.isOnEdge(curPos))
                break
        }
        return seen
    }

    @UseFile("./minky.txt")
    override fun part1(input: TInput): Any? {
        return p1Helper(RectangularGrid(makeLines(input).map { it.toCharArray().toList() })).size
    }

    private fun p2Helper(grid: RectangularGrid<Char>): Boolean {
        val seen = hashSetOf<Pair<Linear, Coord>>()
        var curPos = grid.getPosOf('^')!!
        val r = makeRotation()
        while (true) {
            val dir = r.use()
            val line = grid.coordsUntil(curPos, dir, '#')
            if (line.isEmpty()) break
            line.forEach {
                if (!seen.add(dir to it)) {
                    return true
                }
            }
            curPos = line.last()
            if (grid.isOnEdge(curPos))
                break
        }
        return false
    }

    @UseFile("./minky.txt")
    override fun part2(input: TInput): Any? {
        val grid = RectangularGrid(makeLines(input).map { it.toCharArray().toList() })
        val startingPos = grid.getPosOf('^')!!
        val workingLoops = mutableListOf<Coord>()
        for (c in p1Helper(grid)) {
            val char = grid[c];
            if (c == startingPos || char == '#') {
                continue
            }
            val modifiedGrid = grid.clone()
            modifiedGrid.replace(c, '#')
            if (p2Helper(modifiedGrid)) {
                workingLoops.add(c)
            }
        }
        return "${workingLoops.size}-${workingLoops.toSet().size}"
    }
}
