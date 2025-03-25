package zip.sadan.solutions.y24.d13

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.pair.map
import zip.sadan.util.regex.capture
import zip.sadan.util.twoD.Coord
import kotlin.math.*

typealias TInput = String

private typealias Button = Coord;

private data class Machine(val prize: Coord, val a: Button, val b: Button);

val BUTTON_REGEX = Regex(""".+X\+(\d+), Y\+(\d+)$""");
val PRIZE_REGEX = Regex(""".+X=(\d+), Y=(\d+)$""");

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 13

    private fun String.parseInput(): List<Machine> {

        return this
            .split("\n\n")
            .map {
                val (A, B, prize) = it.split("\n");
                val (aX, aY) = A
                    .capture(BUTTON_REGEX)!!
                    .map {
                        it!!.value.toInt()
                    };
                val (bX, bY) = B
                    .capture(BUTTON_REGEX)!!
                    .map {
                        it!!.value.toInt()
                    }
                val (prizeX, prizeY) = prize
                    .capture(PRIZE_REGEX)!!
                    .map {
                        it!!.value.toInt()
                    }
                Machine(Coord(prizeX, prizeY), Coord(aX, aY), Coord(bX, bY));
            }
    }

    /**
     * easy pythagorean formula given other point at 0, 0
     */
    private fun Coord.hyp(): Double {
        return sqrt(
            this.x
                .toDouble()
                .pow(2) + this.y
                .toDouble()
                .pow(2)
        )
    }

    private fun Machine.solve(): Int {
        var aCount = 0;
        var bCount = min(100, floor(max(prize.x / b.x.toDouble(), prize.y / b.y.toDouble())).toInt());
        fun diff() = a * aCount + b * bCount;
        while (diff() != prize) {
            if (aCount > 100 || bCount > 100) {
                return 0;
            }
            // At least one is overshot the prize, we need to lessen b until we have room
            if (diff().x > prize.x || diff().y > prize.y) {
                bCount--;
                continue;
            }
            aCount++;
        }
        return aCount * 3 + bCount;
    }

    private fun Machine.solve2(): Long {
        operator fun Pair<Long, Long>.times(num: Long): Pair<Long, Long> = this.map {
            it * num;
        }

        operator fun Pair<Long, Long>.plus(other: Pair<Long, Long>): Pair<Long, Long> =
            this.first + other.first to this.second + other.second;
        // shadowed for operator overloading
        val a = (a.x to a.y).map {
            it.toLong()
        };
        val b = (b.x to b.y).map {
            it.toLong()
        };
        val prize = (prize.x to prize.y).map {
            (it + 1e13).toLong()
        };
        var bCount =
            min(100L, floor(max(prize.first / b.first.toDouble(), prize.first / b.second.toDouble())).toLong());
        // really big number, but not big enough to where it will be slow
        var aCount = bCount - 1e5.toLong();
        fun diff(): Pair<Long, Long> = a * aCount + b * bCount;
        while (diff() != prize) {
            // At least one is overshot the prize, we need to lessen b until we have room
            if (diff().first > prize.first || diff().second > prize.second) {
                bCount--;
                continue;
            }
            aCount++;
        }
        println("done")
        return aCount * 3 + bCount;
    }

    @UseFile("test1.txt")
    override fun part1(input: TInput): Any? {


        return input
            .parseInput()
            .sumOf {
                it.solve()
            }
    }

    @UseFile("test1.txt")
    override fun part2(input: TInput): Any? {
        return input
            .parseInput()
            .filter {
                it.solve() == 0;
            }
            .sumOf {
                it.solve2()
            }
    }
}