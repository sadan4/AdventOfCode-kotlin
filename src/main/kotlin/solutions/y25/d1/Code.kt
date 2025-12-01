package zip.sadan.solutions.y25.d1

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor

private typealias TInput = List<String>

data class Instruction(val clicks: Int);

class Code : Solution<TInput>() {
    override val year: Number = 25
    override val day: Number = 1

    private fun List<String>.parseInput(): List<Int> = map {
        val clicks = it
            .substring(1)
            .toInt();
        if (it[0] == 'R') {
            clicks
        } else {
            -clicks
        }
    }

    @UseFile("./input.txt")
    @Solved("1118")
    override fun part1(input: TInput): Any? {
        var cur = 50;
        var zeroCount = 0;
        val parsed = input.parseInput();
        for (i in parsed) {
            cur = (cur + i) % 100;
            if (cur == 0) {
                zeroCount++;
            }
        }
        return zeroCount;
    }

    @UseFile("./test1.txt")
    override fun part2(input: TInput): Any? {
        var cur = 50;
        var zeroCount = 0;
        val parsed = input.parseInput();
        for (i in parsed) {
            if (i == 0) continue;
            val old = cur;
            val new = (cur + i) % 100;
            val right = abs(i) == i;
            if (i >= 100) {
                zeroCount += abs(i) / 100;
            }
            if (new == 0) {
                zeroCount++;
            } else if (right) {
                if (new < old) {
                    zeroCount++;
                }
            } else {
                if (old < new) {
                    zeroCount++;
                }
            }
            cur = new;
        }
        return zeroCount;
    }
}