package zip.sadan.solutions.y25.d6

import zip.sadan.Solution
import zip.sadan.util.collections.list.dropLast
import zip.sadan.util.collections.list.toCharList
import zip.sadan.util.debug.Solved
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.RectangularGrid.Companion.forEachWithCoord
import zip.sadan.util.twoD.TGrid

private typealias TInput = List<String>

private val SPLIT_REGEX = Regex("""\s+""")

private enum class Operation {
    ADD, MULTIPLY;

    fun doOperation(lhs: Long, rhs: Long): Long = when (this) {
        ADD -> lhs + rhs
        MULTIPLY -> lhs * rhs
    }

    fun initialValue(): Long = when (this) {
        ADD -> 0
        MULTIPLY -> 1
    }
}

private fun String.parseOperation(): Operation = parseOperationOrNull() ?: error("Invalid operation: $this")

private fun String.parseOperationOrNull(): Operation? = when (trim()) {
    "+" -> Operation.ADD
    "*" -> Operation.MULTIPLY
    else -> null
}

private class Worksheet(items: TGrid<Long>, private val operators: List<Operation>) : RectangularGrid<Long>(items) {

    fun operatorForCoord(coord: Coord): Operation {
        if (coord !in this) {
            error("coord must be in this")
        }
        return operators[coord.x]
    }
}

private fun parseOperators(operators: String, height: Int): List<Pair<Operation, Set<List<Coord>>>> {
    val ret = mutableListOf<Pair<Operation, Set<List<Coord>>>>()
    var cur: Pair<Operation, MutableSet<List<Coord>>>? = null;
    for ((idx, ch) in operators.withIndex()) {
        val maybeOp = ch
            .toString()
            .parseOperationOrNull();
        if (maybeOp != null) {
            if (cur != null) {
                ret.add(cur)
            }
            cur = maybeOp to mutableSetOf()
        }
        // FIXME: using rangeUntil is broken
        cur!!.second.add((Coord(idx, 0) to Coord(idx, height - 1)).toList())
    }
    if (cur != null) {
        ret.add(cur)
    }
    return ret
}

private fun TInput.parseInput(): Worksheet {
    val numbersLines = subList(0, size - 1).map {
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

    @Solved("5782351442566")
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

    @Solved("10194584711842")
    override fun part2(input: TInput): Any? {
        val grid = RectangularGrid(
            input
                .dropLast()
                .map(String::toCharList)
        )
        return parseOperators(input.last(), input.size - 1).sumOf { (op, numbers) ->
            numbers
                .mapNotNull {
                    it
                        .map(grid::get)
                        .filterNot(Char::isWhitespace)
                        .joinToString("")
                        .toLongOrNull()
                }
                .fold(op.initialValue(), op::doOperation)
        }
    }
}