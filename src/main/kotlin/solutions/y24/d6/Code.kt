package zip.sadan.solutions.y24.d6

import zip.sadan.Solution
import zip.sadan.util.Cycle
import zip.sadan.util.collections.list.toCharList
import zip.sadan.util.debug.Solved
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
            for (c in line) {
                seen.add(c)
                if (!line.hasNext()) {
                    curPos = c
                }
            }
            if (grid.isOnEdge(curPos))
                break
        }
        return seen
    }

    @Solved("4977")
    override fun part1(input: TInput): Any? {
        return p1Helper(RectangularGrid(makeLines(input).map(String::toCharList))).size
    }

    private fun p2Helper(grid: RectangularGrid<Char>, extra: Coord, startingPos: Coord): Boolean {
        val seen = HashSet<Pair<Linear, Coord>>(8192)
        var curPos = startingPos
        val r = makeRotation()
        while (true) {
            val dir = r.use()
            val line = grid.coordsUntil(curPos, dir) {
                if (grid[it] == '#' || it == extra) {
                    return@coordsUntil true
                } else {
                    if(!seen.add(dir to it)) {
                        return true
                    }
                    return@coordsUntil false
                }
            }
            if (!line.hasNext()) {
                continue
            }
            curPos = line.last()
            if (grid.isOnEdge(curPos))
                break
        }
        return false
    }

    @Solved("1729")
    override fun part2(input: TInput): Any? {
        val grid = RectangularGrid(makeLines(input).map(String::toCharList))
        val startingPos = grid.getPosOf('^')!!
        var i = 0
        for (c in p1Helper(grid)) {
            val char = grid[c];
            if (c == startingPos || char == '#') {
                continue
            }

            if (p2Helper(grid, c, startingPos)) {
                i++
            }
        }

        return "${i}"
    }
}
