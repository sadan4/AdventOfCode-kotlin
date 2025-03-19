package zip.sadan.solutions.y24.d11

import zip.sadan.Solution
import zip.sadan.util.debug.Solved

typealias TInput = String
typealias TCache25 = HashMap<Long, List<Long>>
typealias TCache50 = HashMap<Long, Long>

private val cache25: TCache25 = HashMap()
private val cache50: TCache50 = HashMap()

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 11

    private fun run25(input: Long): List<Long> {
        if (cache25.containsKey(input)) {
            return cache25[input]!!
        }
        var cur: List<Long> = listOf(input)
        for (i in 1..25) {
            cur = cur.flatMap {
                if (it == 0L)
                    return@flatMap listOf(1L)
                val t = it.toString()
                if (t.length % 2 == 0) {
                    return@flatMap listOf(
                        t
                            .take(t.length / 2)
                            .toLong(),
                        t
                            .drop(t.length / 2)
                            .toLong()
                    )
                }
                return@flatMap listOf(it * 2024L)
            }
        }
        cache25[input] = cur
        return cur
    }

    @Solved("193607")
    override fun part1(input: TInput): Any? {
        val nums = input
            .removeSuffix("\n")
            .split(" ")
            .map {
                it.toLong()
            }
        val res = nums.sumOf {
            run25(it).size
        }
        return res
    }

    private fun run50(input: Long): Long {
        if (cache50.containsKey(input)) {
            return cache50[input]!!
        }
        val cur = run25(input)
        var sum = 0L;
        for (i in cur) {
            sum += run25(i).size
        }
        cache50[input] = sum
        return sum
    }

    private fun run75(input: Long): Long {

        val cur = run25(input);
        var res = 0L
        for (i in cur) {
            res += run50(i)
        }
        return res
    }

    @Solved("229557103025807")
    override fun part2(input: TInput): Any? {
        val nums = input
            .removeSuffix("\n")
            .split(" ")
            .map {
                it.toLong()
            }
        val res = nums.sumOf {
            run75(it)
        }
        return res
    }
}
