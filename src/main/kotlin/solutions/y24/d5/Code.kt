package zip.sadan.solutions.y24.d5

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.pair.toPair

private fun List<String>.makeRules(): List<Pair<Int, Int>> = this
    .map {
        it
            .split("|")
            .map {
                it
                    .trim()
                    .toInt()
            }
            .toPair()
    }
private fun List<String>.makePages(): List<List<Int>> = this
    .map {
        it
            .split(",")
            .map {
                it
                    .trim()
                    .toInt()
            }
    }
private fun List<Pair<Int, Int>>.makeRulesForAns(ans: List<Int>): List<Int> {
    val toRet = ArrayList<Int>()
    this
        .filter { (a, b) ->
            a in ans && b in ans
        }
        .forEach { (page, before) ->
            if (!toRet.contains(before)) toRet.add(before)
            toRet.add(toRet.indexOf(before), page)
        }
    return toRet.filterIndexed { index, t ->
        index == toRet.indexOf(t)
    };
}

class Code : Solution<String>() {
    override val year: Number = 24
    override val day: Number = 5

    @UseFile("./input.txt")
    override fun part1(input: String): Any? {
        val inp = input
            .split("\n\n")
            .map {
                it
                    .trim()
                    .split("\n")
            }
        val rules = inp[0].makeRules()
        val production = inp[1].makePages()
        val test = production.map(rules::makeRulesForAns)
        val test1 = production.sumOf {
            if (rules
                    .makeRulesForAns(it)
                    .toIntArray()
                    .contentEquals(it.toIntArray())
            ) it[it.size / 2] else 0
        }
        return test1;
    }

    override fun part2(input: String): Any? {
        return ""
    }
}