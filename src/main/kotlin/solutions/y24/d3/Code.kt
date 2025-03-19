package zip.sadan.solutions.y24.d3

import zip.sadan.Solution
import zip.sadan.util.collections.pair.toPair
import zip.sadan.util.debug.Solved
import zip.sadan.util.collections.pair.map
import zip.sadan.util.collections.pair.mult
import zip.sadan.util.regex.Regex
import zip.sadan.util.regex.captureAll

class Code : Solution<String>() {
    override val year: Number = 24
    override val day: Number = 3

    @Solved("189600467")
    override fun part1(input: String): Any? = input
        .captureAll(Regex("mul\\((\\d+),(\\d+)\\)"))
        .sumOf {
            it
                .toPair()
                .map { it!!.value.toInt() }
                .mult()
        }


    @Solved("107069718")
    override fun part2(input: String): Any? = this.part1(input.replace(Regex("don't\\(\\).*?(?:do\\(\\)|$)", "s"), ""))
}