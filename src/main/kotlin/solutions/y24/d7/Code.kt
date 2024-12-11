package zip.sadan.solutions.y24.d7

import zip.sadan.Solution
import zip.sadan.util.array.addHead
import zip.sadan.util.array.toPair

typealias TInput = List<String>

private enum class Operation {
    NONE,
    ADD,
    MUL,
}

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 7

    private fun isPossible(_a: Pair<Long, List<Long>>): List<Operation> {
        val (total, nums) = _a;
        val (a, b) = nums
        if (nums.size == 2) {
            if (a * b == total) return listOf(Operation.MUL)
            if (a + b == total) return listOf(Operation.ADD)
            return listOf(Operation.NONE)
        }
        val rest = nums.subList(2, nums.size)
        while (rest.isNotEmpty()) {
            val tryMul = isPossible(total to (a * b addHead rest))
            if (tryMul.none {
                it == Operation.NONE
                }) {
                return tryMul + Operation.MUL
            }
            val tryAdd = isPossible(total to (a + b addHead rest))
            if (tryAdd.none {
                it == Operation.NONE
                }) {
                return tryAdd + Operation.ADD
            }
            return listOf(Operation.NONE)
        }
        error("Should not reach here")
    }

    override fun part1(input: TInput): Any? {
        val exprs = input.map {
            with(it.split(":").toPair()) {
                first.toLong() to second.trim().split(" ").map {
                    it.toLong()
                }
            }
        }
        return exprs.filter {
            isPossible(it).none {
                it == Operation.NONE
            }
        }.sumOf {
            it.first
        }
    }

    override fun part2(input: TInput): Any? {
        return ""
    }
}