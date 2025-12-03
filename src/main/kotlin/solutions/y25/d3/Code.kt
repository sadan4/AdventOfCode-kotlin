package zip.sadan.solutions.y25.d3

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
import kotlin.math.pow

private typealias TInput = List<String>

class Code : Solution<TInput>() {
    override val year: Number = 25
    override val day: Number = 3
    private fun List<String>.parse(): List<List<Int>> = map {
        it
            .trim()
            .toCharArray()
            .map {
                when (it) {
                    '0' -> 0
                    '1' -> 1
                    '2' -> 2
                    '3' -> 3
                    '4' -> 4
                    '5' -> 5
                    '6' -> 6
                    '7' -> 7
                    '8' -> 8
                    '9' -> 9
                    else -> error("invalid")
                }
            }
    }

    private fun List<Int>.highestCombo1(): Int {
        var first = 0
        var idx = 0
        var second = 0
        for (i in 0..<size - 1) {
            val e = this[i]
            if (e == 9) {
                first = 9;
                idx = i;
                break
            }
            if (e > first) {
                first = e
                idx = i
            }
        }
        for (i in idx + 1..<size) {
            val e = this[i]
            if (e == 9) {
                return first * 10 + e
            }
            if (e > second) {
                second = e
            }
        }
        return first * 10 + second
    }

    @UseFile("./input.txt")
    @Solved("17766")
    override fun part1(input: TInput): Any? {
        return input
            .parse()
            .sumOf {
                it.highestCombo1()
            }
    }

    // idx to val
    private fun List<Int>.nextHighestNumber(start: Int, last: Int = size - 1): Pair<Int, Int> {
        var cur = -1
        var idx = -1;
        for (i in start..last) {
            val e = this[i]
            if (e == 9) {
                return i to 9
            }
            if (e > cur) {
                cur = e
                idx = i
            }
        }
        return idx to cur
    }

    private fun List<Int>.highestCombo2(): Long {
        var curIdx = 0;
        var ret = 0L;
        for (i in 0..11) {
            val num = nextHighestNumber(curIdx, size - (11 - i) - 1).let {
                curIdx = it.first
                it.second
            }

            ret += num * 10.0
                .pow(11 - i)
                .toLong()
        }
        return ret;
    }

    @UseFile("./test.txt")
    override fun part2(input: TInput): Any? {
        return input
            .parse()
            .sumOf {
                it.highestCombo2()
            }
    }
}