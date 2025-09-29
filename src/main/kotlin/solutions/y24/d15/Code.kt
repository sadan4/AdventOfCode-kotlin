package zip.sadan.solutions.y24.d15

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.direction.Direction
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.TGrid
import java.io.File

private typealias TInput = String

private enum class GRID_ITEM {
    ROBOT,
    BOX,
    EDGE,
    EMPTY;

    override fun toString(): String = when (this) {
        ROBOT -> "@"
        BOX -> "O"
        EDGE -> "#"
        EMPTY -> "."
    }
}

private class RobotGrid(items: TGrid<GRID_ITEM>) : RectangularGrid<GRID_ITEM>(items) {
    var robotPos: Coord = getPosOf(GRID_ITEM.ROBOT)!!

    fun moveUp() {

    }
    fun moveDown() {

    }
    fun moveLeft() {

    }
    fun moveRight() {

    }
}

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 15

    private fun String.parseInput(): Pair<RobotGrid, List<Direction>> {
        val (_grid, _moves) = split("\n\n")
        val grid = RobotGrid(
            _grid
                .split("\n")
                .map {
                    it
                        .toCharArray()
                        .map {
                            when (it) {
                                '@' -> GRID_ITEM.ROBOT
                                '#' -> GRID_ITEM.EDGE
                                'O' -> GRID_ITEM.BOX
                                '.' -> GRID_ITEM.EMPTY
                                else -> {
                                    error("Unexpected char: $it")
                                }
                            }
                        }
                })
        val moves = _moves
            .toCharArray()
            .filter {
                !it.isWhitespace()
            }
            .map {
                when (it) {
                    '^' -> Direction.N
                    'v' -> Direction.S
                    '>' -> Direction.E
                    '<' -> Direction.W
                    else -> {
                        error("Unexpected char: $it")
                    }
                }
            }
        return grid to moves
    }

    @UseFile("./test.txt")
    override fun part1(input: TInput): Any? {
        val (grid, moves) = input.parseInput()
        return null
    }

    override fun part2(input: TInput): Any? {
        return ""
    }
}