package zip.sadan.solutions.y24.d6

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.Cycle
import zip.sadan.util.debug.log
import zip.sadan.util.direction.Linear
import zip.sadan.util.input.makeLines
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid

typealias TInput = String

private fun makeRotation(): Cycle<Linear> {
    return Cycle(listOf(Linear.S, Linear.E, Linear.N, Linear.W))
}

private fun Linear.getFlag(): Int {
    return 1 shl this.ordinal
}

private fun Char.fromFlag(): Int = this.digitToIntOrNull(16) ?: 0

private fun Int.toFlag(): Char = this.toString(16)[0]

private fun getDirMask(thidDir: Linear): Int = when (thidDir) {
    Linear.N, Linear.S -> Linear.N.getFlag() or Linear.S.getFlag()
    Linear.E, Linear.W -> Linear.E.getFlag() or Linear.W.getFlag()
}

private fun isOnSameLine(thisDir: Linear, other: Char) = (getDirMask(thisDir) and other.fromFlag()) != 0

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 6

    @UseFile("./minky.txt")
    override fun part1(input: TInput): Any? {
        val grid = RectangularGrid(makeLines(input).map { it.toCharArray().toList() })
        var curpos = grid.getPosOf('^')!!
        val r = makeRotation()
        while (true) {
            val dir = r.use();
            val line = grid.coordsUntil(curpos, dir, '#')
            if (line.isEmpty()) {
                println("empty")
                break
            }
            grid.replaceAll(line, 'X')
            curpos = line.last()
            if (grid.isOnEdge(curpos)) {
                println("is on edge")
                break;
            }
        }
        return grid.count('X')
    }

    @UseFile("./minky.txt")
    override fun part2(input: TInput): Any? {
        val grid = RectangularGrid(makeLines(input).map { it.toCharArray().toList() })
        val startingPos = grid.getPosOf('^')!!
        val workingLoops = mutableListOf<Coord>()
        val r = makeRotation()
        val obstacles = grid.getAll('#').toSet()
        for (c in Coord(0) to Coord(grid.width - 1, grid.height - 1)) {
                val char = grid[c];
                if (c == startingPos || char == '#') {
                    continue
                }
                if (repeats(grid, obstacles,  startingPos, c)) {
                    workingLoops.add(c)
                }
        }
        grid.replaceAll(workingLoops, 'O')
        grid.formatHlPrint(*workingLoops.toTypedArray()).log()
        return "${workingLoops.size}-${workingLoops.toSet().size}-${x}"
    }
}
private var x = 0
private fun repeats(grid: RectangularGrid<Char>, obstacles: Set<Coord>, initPos: Coord, placedObstacle: Coord): Boolean {
    var curPos = initPos
    val r = makeRotation()
    val seen = hashSetOf<Pair<Linear, Coord>>()
    while (true) {
        val dir = r.use();
        val line = grid.coordsUntil(curPos, dir) {
            if(it == placedObstacle) {
                return@coordsUntil true
            }
            it in obstacles
        }
        if (line.isEmpty()) {
            break;
        }
        line.forEach {
            if (!seen.add(dir to it)) {
                x++
                return true
            }
        }
        curPos = line.last()
        if (grid.isOnEdge(curPos)) {
            break
        }
    }
    return false
}