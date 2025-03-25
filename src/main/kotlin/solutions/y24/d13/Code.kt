package zip.sadan.solutions.y24.d13

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
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
        val p = object {
            val x = prize.x + 10000000000000
            val y = prize.y + 10000000000000
        }
        val timesA = (p.x*b.y - p.y*b.x) / (a.x*b.y - a.y*b.x).toDouble()
        val timesB = (p.x-(a.x*timesA))/b.x.toDouble();
        if(floor(timesA) != timesA || floor(timesB) != timesB) {
            return 0;
        }
        return (3 * timesA + timesB).toLong();
    }

    @Solved("40069")
    @UseFile("input.txt")
    override fun part1(input: TInput): Any? {


        return input
            .parseInput()
            .sumOf {
                it.solve()
            }
    }

    @UseFile("input.txt")
    override fun part2(input: TInput): Any? {
        return input
            .parseInput()
            .sumOf {
                it.solve2()
            }
    }
}