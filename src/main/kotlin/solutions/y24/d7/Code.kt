package zip.sadan.solutions.y24.d7

import zip.sadan.Solution
import zip.sadan.util.collections.list.addHead
import zip.sadan.util.collections.pair.toPair
import zip.sadan.util.debug.Solved

typealias TInput = List<String>

private enum class Operation {
    NONE,
    ADD,
    CONCAT,
    MUL,
}

private infix fun Long.concat(other: Long): Long = (this.toString() + other.toString()).toLong()
class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 7

    private fun isPossible(_a: Pair<Long, List<Long>>): Boolean {
        val (total, nums) = _a;
        val (a, b) = nums
        if (nums.size == 2) {
            if (a * b == total) return true
            if (a + b == total) return true
            return false
        }
        val rest = nums.subList(2, nums.size)
        val tryMul = isPossible(total to (a * b addHead rest))
        if (tryMul) {
            return true
        }
        val tryAdd = isPossible(total to (a + b addHead rest))
        return tryAdd
        error("Should not reach here")
    }

    @Solved("538191549061")
    override fun part1(input: TInput): Any? {
        val exprs = input.map {
            with(
                it
                    .split(":")
                    .toPair()
            ) {
                first.toLong() to second
                    .trim()
                    .split(" ")
                    .map {
                        it.toLong()
                    }
            }
        }
        return exprs
            .filter {
                isPossible(it)
            }
            .sumOf {
                it.first
            }
    }

    private fun isPossibleP2(_entry: Pair<Long, List<Long>>): Boolean {
        val (total, nums) = _entry;
        if (nums.size == 2) {
            val (a, b) = nums;
            return when (total) {
                a + b,
                a * b,
                a concat b -> true

                else -> false
            }
        } else if (nums.size < 2) {
            error("nums should always have at least 2 elements");
        }
        val (a, b) = nums;
        val rest = nums.subList(2, nums.size);
        val tryAdd = isPossibleP2(total to (a + b addHead rest))
        if (tryAdd)
            return true
        val tryMul = isPossibleP2(total to (a * b addHead rest))
        if (tryMul)
            return true
        val tryConcat = isPossibleP2(total to (a concat b addHead rest))
        return tryConcat
    }

    @Solved("34612812972206")
    override fun part2(input: TInput): Any? {
        val exprs = input.map {
            with(
                it
                    .split(":")
                    .toPair()
            ) {
                first.toLong() to second
                    .trim()
                    .split(" ")
                    .map {
                        it.toLong()
                    }
            }
        }
        return exprs
            .filter {
                isPossibleP2(it)
            }
            .sumOf {
                it.first
            }
    }
}