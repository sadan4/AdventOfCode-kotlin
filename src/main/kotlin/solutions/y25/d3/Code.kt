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

    private fun List<Int>.maxIdx(start: Int = 0, end: Int = size): Int {
        var max = 0;
        var maxIdx = 0;
        for (i in start until end) {
            val cur = this[i];
            if (cur == 0) {
                return i;
            }
            if (cur > max) {
                max = cur;
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    private fun List<Int>.highestCombo1(): Int {
        val firstIdx = maxIdx(end = size - 1);
        val secondIdx = maxIdx(firstIdx + 1);
        return this[firstIdx] * 10 + this[secondIdx]
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

    private fun List<Int>.highestCombo2(): Long {
        var result = 0L;
        var lastIdx = 0;
        for (step in 11 downTo 0) {
            val start = lastIdx;
            val end = size - step;
            if (end - start == 1) {
                return result + (step downTo 0)
                    .sumOf {
                        this[start + step - it] * 10.0.pow(it).toLong()
                    }
            }
            lastIdx = maxIdx(start, end);
            result += this[lastIdx++] * 10.0.pow(step).toLong();
        }
        return result;
    }

    @UseFile("./input.txt")
    @Solved("176582889354075")
    override fun part2(input: TInput): Any? {
        return input
            .parse()
            .sumOf {
                it.highestCombo2()
            }
    }
}