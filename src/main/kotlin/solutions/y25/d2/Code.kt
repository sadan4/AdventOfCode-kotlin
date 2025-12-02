package zip.sadan.solutions.y25.d2

import countDigits
import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.list.allEqual
import zip.sadan.util.collections.pair.map
import zip.sadan.util.collections.pair.toPair
import zip.sadan.util.debug.Solved
import zip.sadan.util.math.isOdd
import kotlin.math.pow

private typealias TInput = String;

class Code : Solution<TInput>() {
    override val year: Number = 25
    override val day: Number = 2

    private fun String.parse(): List<LongRange> =
        split(",")
            .map {
                val (start, end) = it
                    .trim()
                    .split("-")
                    .toPair()
                    .map(String::toLong)
                start..end
            }

    private fun Long.isValidId1(): Boolean {
        val numDigits = countDigits(this)
        if (numDigits.isOdd) {
            return true
        }
        val mask = 10.0
            .pow((numDigits / 2))
            .toLong();
        val topDigits = this / mask
        val bottomDigits = this % mask;

        return topDigits != bottomDigits
    }

    @UseFile("./input.txt")
    @Solved("23701357374")
    override fun part1(input: TInput): Any? {
        return input
            .parse()
            .flatten()
            .filter {
                !it.isValidId1()
            }
            .sum()
    }

    private fun Long.isValidId2(): Boolean {
        if (this < 10) {
            return true;
        }
        val digits = toString()
            .toCharArray()
            .toList()
        val numDigits = digits.size;
        return (1..<numDigits)
            .filter {
                (numDigits / it.toDouble()) % 1 == 0.0
            }
            .none {
                digits
                    .chunked(it)
                    .allEqual()
            }
    }

    @UseFile("./input.txt")
    @Solved("34284458938")
    override fun part2(input: TInput): Any? {
        return input
            .parse()
            .flatten()
            .filter {
                !it.isValidId2()
            }
            .sum()
    }
}