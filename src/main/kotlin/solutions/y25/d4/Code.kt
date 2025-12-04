package zip.sadan.solutions.y25.d4

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
import zip.sadan.util.debug.log
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.TGrid
import java.io.File

private typealias TInput = List<String>

private enum class Item {
    EMPTY {
        override fun toString(): String = "."
    },
    FULL {
        override fun toString(): String = "@"
    },
}

class Code : Solution<TInput>() {
    override val year: Number = 25
    override val day: Number = 4

    private fun List<String>.parseInput(): RectangularGrid<Item> = RectangularGrid(map {
        it.map {
            when (it) {
                '.' -> Item.EMPTY
                '@' -> Item.FULL
                else -> error("invalid item: $it")
            }
        }
    })

    private fun RectangularGrid<Item>.canBeRemoved(coord: Coord): Boolean = this[coord] == Item.FULL && coord
        .neighbors()
        .count {
            if (it !in this) {
                false
            } else {
                this[it] == Item.FULL
            }
        } < 4

    @UseFile("./input.txt")
    @Solved("1518")
    override fun part1(input: TInput): Any? {
        return input
            .parseInput()
            .countWithCoord { coord, _ ->
                canBeRemoved(coord)
            }
    }

    @Solved("8665")
    override fun part2(input: TInput): Any? {
        var grid = input.parseInput();
        var totalRemoved = 0;
        while (true) {
            val atStart = totalRemoved;
            grid = grid.mapWithCoord { coord, it ->
                if (canBeRemoved(coord)) {
                    totalRemoved++
                    Item.EMPTY
                } else {
                    it
                }
            }
            if (atStart == totalRemoved) {
                break
            }
        }
        return totalRemoved
    }
}