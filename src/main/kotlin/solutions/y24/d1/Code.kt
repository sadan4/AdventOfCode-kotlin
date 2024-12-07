package zip.sadan.solutions.y24.d1

import zip.sadan.Solution
import zip.sadan.util.array.toPair
import zip.sadan.util.debug.log
import zip.sadan.util.pair.map
import zip.sadan.util.pair.zip
import kotlin.math.abs

private fun List<String>.numbers(): List<Pair<Int, Int>> = map {
    it
        .split(Regex(" +"))
        .map {
            it.toInt()
        }
        .toPair()
}

class Code : Solution<List<String>>() {
    override val year: Number = 24
    override val day: Number = 1

    override fun part1(input: List<String>): Any = input
        .numbers()
        .unzip()
        .map {
            it.sorted()
        }
        .zip()
        .sumOf {
            abs(it[1] - it[0])
        }

    override fun part2(input: List<String>): Any {
        val (l, r) = input
            .numbers()
            .unzip()
            .map {
                it.sorted()
            }
        return l
            .zip(r)
            .sumOf { (first) ->
                first * r.count { it == first }
            }
    }
}