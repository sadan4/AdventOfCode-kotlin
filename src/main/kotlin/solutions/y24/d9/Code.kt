package zip.sadan.solutions.y24.d9

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.list.Fill
import zip.sadan.util.collections.list.swap
import zip.sadan.util.debug.Solved

private typealias TInput = String

/**
 * @param id if -1, free space
 */
private data class Fragment(val id: Int, val len: Int);

/**
 * a single part of a [Fragment]
 *
 * @param id if -1, free space
 */
private data class Block(val id: Int) {
    override fun toString(): String = if (id == -1) "." else id.toString()
};

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 9

    private fun String.parse(): List<Fragment> {
        var id = 0;
        return this
            .toCharArray()
            .mapIndexed { i, len ->
                // convert to string first to get the numerical value, not the char code
                Fragment(if (i % 2 == 1) -1 else id++, len.toString().toInt())
            }
    }

    @Solved("6330095022244")
    @UseFile("./input.txt")
    override fun part1(input: TInput): Any? {
        val blocks = input.parse().flatMapTo(mutableListOf()) {
            Fill(it.len, Block(it.id))
        };

        fun firstBlankSpace() = blocks.indexOfFirst {
            it.id == -1
        }

        fun lastUsedSpace() = blocks.indexOfLast {
            it.id != -1
        }

        while (firstBlankSpace() - 1 != lastUsedSpace()) {
            blocks.swap(firstBlankSpace(), lastUsedSpace())
        }
        return blocks.foldIndexed(0L) { i, cur, block ->
            if(block.id == -1) return@foldIndexed cur;
            return@foldIndexed cur + block.id * i
        }
    }

    override fun part2(input: TInput): Any? {
        return ""
    }
}