package zip.sadan.solutions.y24.d2

import zip.sadan.Solution
import zip.sadan.util.collections.list.dropOne
import zip.sadan.util.collections.pair.toPair;
import zip.sadan.util.debug.Solved
import zip.sadan.util.input.numbers

private fun List<Int>.isValid(): Boolean {
    val shouldIncrease = if (this.first() < this.last()) 1 else -1
    return !this
        .windowed(2)
        .map {
            it.toPair()
        }
        .any { (a, b) ->
            (b - a) * shouldIncrease !in 1..3
        }
}
class Code : Solution<List<String>>() {
    override val year: Number = 24
    override val day: Number = 2

    @Solved("442")
    override fun part1(input: List<String>): Any? = input
        .numbers()
        .count {
            it.isValid()
        }

    @Solved("493")
    override fun part2(input: List<String>): Any? = input
        .numbers()
        .count {
            for ( i in it.indices) {
                if(it.dropOne(i).isValid()) return@count true
            }
            return@count it.isValid()
        }
}