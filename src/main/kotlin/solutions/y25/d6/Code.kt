package zip.sadan.solutions.y25.d6

import zip.sadan.Solution
import zip.sadan.solutions.y25.d6.Worksheet.Operation
import zip.sadan.solutions.y25.d6.Worksheet.Operation.ADD
import zip.sadan.solutions.y25.d6.Worksheet.Operation.MULTIPLY
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.RectangularGrid.Companion.forEachWithCoord
import zip.sadan.util.twoD.TGrid

private typealias TInput = List<String>

private val SPLIT_REGEX = Regex("""\s+""")

private fun String.parseOperation(): Operation = when (trim()) {
    "+" -> ADD
    "*" -> MULTIPLY
    else -> error("invalid string: $this")
}

private class Worksheet(items: TGrid<Long>, private val operators: List<Operation>) : RectangularGrid<Long>(items) {
    enum class Operation {
        ADD,
        MULTIPLY;

        fun doOperation(lhs: Long, rhs: Long): Long = when (this) {
            ADD -> lhs + rhs
            MULTIPLY -> lhs * rhs
        }
    }

    fun operatorForCoord(coord: Coord): Operation {
        if (coord !in this) {
            error("coord must be in this")
        }
        return operators[coord.x]
    }
}

private fun TInput.parseInput(): Worksheet {
    val numbersLines = subList(0, size - 1)
        .map {
            it
                .split(SPLIT_REGEX)
                .map(String::toLong)
        }
    val operators = last()
        .trim()
        .split(SPLIT_REGEX)
        .map(String::parseOperation)
    return Worksheet(numbersLines, operators)
}

class Code : Solution<TInput>() {
    override val year: Number = 25
    override val day: Number = 6

    override fun part1(input: TInput): Any? {
        val resultTracker = mutableMapOf<Int, Long>();
        input
            .parseInput()
            .forEachWithCoord { coord, num ->
                resultTracker.compute(coord.x) { _, v ->
                    if (v == null) {
                        num
                    } else {
                        operatorForCoord(coord).doOperation(v, this[coord])
                    }
                }
            }
        return resultTracker.values.sum()
    }

    override fun part2(input: TInput): Any? {
        return ""
    }
}