package zip.sadan.solutions.y25.d1

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.sign

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

    @UseFile("./input.txt")
    @Solved("6289")
    override fun part2(input: TInput): Any? {
        var cur = 50;
        var zeroCount = 0;
        val parsed = input.parseInput();
        for (_i in parsed) {
            var i = _i;
            if (abs(i) >= 100) {
                zeroCount += abs(i) / 100;
                i %= 100
            }
            val old = cur;
            cur += i;
            if (old % 100 == 0) {
                continue
            }
            val new = cur;
            when {
                new % 100 == 0
                || new / 100 != old / 100
                || new / 100 == 0 && new.sign != old.sign -> {
                    zeroCount++;
                }
            }
        }
        return zeroCount;
    }
}