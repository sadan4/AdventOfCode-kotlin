package zip.sadan.solutions.y24.d15

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.pair.map
import zip.sadan.util.debug.Solved
import zip.sadan.util.direction.Linear
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.TGrid

private typealias TInput = String

private enum class GridItem {
    ROBOT, BOX, EDGE, EMPTY;

    override fun toString(): String = when (this) {
        ROBOT -> "@"
        BOX -> "O"
        EDGE -> "#"
        EMPTY -> "."
    }

    companion object {
        fun fromChar(c: Char): GridItem = when (c) {
            '.' -> EMPTY
            '#' -> EDGE
            'O' -> BOX
            '@' -> ROBOT
            else -> throw IllegalArgumentException("invalid robot char")
        }
    }
}


private class RobotGrid(items: TGrid<GridItem>) : RectangularGrid<GridItem>(items) {
    var pos: Coord = getPosOf(GridItem.ROBOT)!!

    private fun _move(dir: Linear) {
        val d = dir.toShift();
        if (this[pos + d] == GridItem.EMPTY) {
            swap(pos, pos + d);
            pos += d;
            return;
        } else if (this[pos + d] == GridItem.EDGE) {
            return;
        }
        for (spot in coordsOfLine(pos, dir)) {
            if (this[spot] == GridItem.EDGE) {
                return;
            } else if (this[spot] == GridItem.EMPTY) {
                // move the box closest to us to the end
                swap(pos + d, spot);
                swap(pos, pos + d);
                pos += d;
                return;
            }
        }
        error("unreachable");
    }

    fun move(dir: Linear) = _move(
        when (dir) {
            Linear.N, Linear.S -> dir.compliment()
            else -> dir
        }
    )
}


private enum class WideGridItem {
    ROBOT, BOX_START, BOX_END, EDGE, EMPTY;

    override fun toString(): String = when (this) {
        ROBOT -> "@"
        BOX_START -> "["
        BOX_END -> "]"
        EDGE -> "#"
        EMPTY -> "."
    }

    companion object {
        fun fromGridItem(item: GridItem): Pair<WideGridItem, WideGridItem> = when (item) {
            GridItem.ROBOT -> ROBOT to EMPTY
            GridItem.BOX -> BOX_START to BOX_END
            GridItem.EDGE -> EDGE to EDGE
            GridItem.EMPTY -> EMPTY to EMPTY
        }
    }
}

private class WideRobotGrid(items: TGrid<WideGridItem>) : RectangularGrid<WideGridItem>(items) {
    private var head = getPosOf(WideGridItem.ROBOT)!!;

    private fun moveH(dir: Linear) {
        val d = dir.toShift();
        if (this[head + d] == WideGridItem.EDGE) {
            return;
        } else if (this[head + d] == WideGridItem.EMPTY) {
            swap(head, head + d);
            head += d;
            return;
        }
        for (spot in coordsOfLine(head, dir)) {
            if (this[spot] == WideGridItem.EDGE) {
                return;
            } else if (this[spot] == WideGridItem.EMPTY) {
                val rd = d * -1;
                for (coord in coordsOfLine(spot, rd)) {
                    swap(coord, coord + rd);
                    if (coord + rd == head) {
                        break;
                    }
                }
                head += d;
                return;
            }
        }
    }

    private fun push(origin: Coord, shift: Coord) {
        val (head, tail) = run {
            if (this[origin] == WideGridItem.BOX_START) {
                Pair(origin, origin + Linear.E)
            } else {
                Pair(origin + Linear.W, origin)
            }
        }
        if (!isBox(head) || !isBox(tail)) {
            error("push not a box");
        }
        val (nextHead, nextTail) = Pair(head, tail)
            .map {
                it + shift
            }
        if (isBox(nextHead)) {
            push(nextHead, shift)
        }
        if (isBox(nextTail)) {
            push(nextTail, shift)
        }
        swap(head, nextHead)
        swap(tail, nextTail)
    }

    private fun moveV(dir: Linear) {
        val d = dir.toShift()
        if (this[head + d] == WideGridItem.EDGE) {
            return;
        } else if (this[head + d] == WideGridItem.EMPTY) {
            swap(head, head + d);
            head += d;
            return;
        }
        if (ensureClear(head + d, d)) {
            push(head + d, d);
            swap(head, head + d);
            head += d;
        }
    }

    private fun ensureClear(box: Coord, inShift: Coord): Boolean {
        if (!isBox(box)) {
            error("input coord is not a box");
        }
        val (nextHead, nextTail) = run {
            if (this[box] == WideGridItem.BOX_START) {
                Pair(box, box + Linear.E)
            } else {
                Pair(box + Linear.W, box)
            }
        }.map {
            it + inShift
        }
        val headClear = if (isBox(nextHead)) {
            ensureClear(nextHead, inShift);
        } else if (this[nextHead] == WideGridItem.EDGE) {
            false;
        } else if (this[nextHead] == WideGridItem.EMPTY) {
            true;
        } else {
            error("unreachable");
        }
        val tailClear = if (isBox(nextTail)) {
            ensureClear(nextTail, inShift);
        } else if (this[nextTail] == WideGridItem.EDGE) {
            false;
        } else if (this[nextTail] == WideGridItem.EMPTY) {
            true;
        } else {
            error("unreachable");
        }

        return headClear && tailClear;
    }

    private fun isBox(coord: Coord): Boolean =
        this[coord] == WideGridItem.BOX_START || this[coord] == WideGridItem.BOX_END

    fun move(dir: Linear) = when (dir) {
        Linear.N, Linear.S -> moveV(dir.compliment())
        else -> moveH(dir)
    }
}

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 15

    private fun String.parseInput(): Pair<TGrid<GridItem>, List<Linear>> {
        val (_grid, _moves) = split("\n\n")
        val grid = _grid
            .split("\n")
            .map {
                it
                    .toCharArray()
                    .map(GridItem::fromChar)
            }
        val moves = _moves
            .toCharArray()
            .filter {
                !it.isWhitespace()
            }
            .map {
                when (it) {
                    '^' -> Linear.N
                    'v' -> Linear.S
                    '>' -> Linear.E
                    '<' -> Linear.W
                    else -> {
                        error("Unexpected char: $it")
                    }
                }
            }
        return grid to moves
    }

    private fun String.parseGrid(): Pair<RobotGrid, List<Linear>> {
        val (_grid, moves) = parseInput();
        return RobotGrid(_grid) to moves
    }

    @Solved("1476771")
    @UseFile("input.txt")
    override fun part1(input: TInput): Any? {
        val (grid, moves) = input.parseGrid()

        moves.forEach(grid::move);

        return grid
            .coords()
            .filter {
                grid[it] == GridItem.BOX
            }
            .sumOf {
                it.x + (100 * it.y)
            }
    }

    private fun String.parseWideGrid(): Pair<WideRobotGrid, List<Linear>> {
        val (items, moves) = parseInput();
        return WideRobotGrid(items.map {
            it
                .map(WideGridItem::fromGridItem)
                .flatMap(Pair<WideGridItem, WideGridItem>::toList)
        }) to moves
    }

    @Solved("1468005")
    @UseFile("input.txt")
    override fun part2(input: TInput): Any? {
        val (grid, moves) = input.parseWideGrid();

        moves.forEach(grid::move);

        return grid
            .coords()
            .filter {
                grid[it] == WideGridItem.BOX_START
            }
            .sumOf {
                it.x + (100 * it.y)
            }
    }
}