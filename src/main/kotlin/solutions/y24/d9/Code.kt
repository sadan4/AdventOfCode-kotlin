package zip.sadan.solutions.y24.d9

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.list.Fill
import zip.sadan.util.collections.list.replaceRange
import zip.sadan.util.collections.list.swap
import zip.sadan.util.debug.Solved
import zip.sadan.util.debug.debug
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull

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
            .trim()
            .toCharArray()
            .mapIndexed { i, len ->
                // convert to string first to get the numerical value, not the char code
                Fragment(
                    if (i % 2 == 1) -1 else id++,
                    len
                        .toString()
                        .toInt()
                )
            }
    }

    private fun List<Fragment>.toBlocks() = this.flatMap {
        Fill(it.len, Block(it.id))
    }

    private fun List<Block>.checksum() = this.foldIndexed(0L) { i, cur, block ->
        cur + if (block.id == -1) {
            0
        } else {
            block.id * i
        }
    }

    @Solved("6330095022244")
    @UseFile("./input.txt")
    override fun part1(input: TInput): Any? {
        val blocks = input
            .parse()
            .toBlocks()
            .toMutableList();

        fun firstBlankSpace() = blocks.indexOfFirst {
            it.id == -1
        }

        fun lastUsedSpace() = blocks.indexOfLast {
            it.id != -1
        }

        while (firstBlankSpace() - 1 != lastUsedSpace()) {
            blocks.swap(firstBlankSpace(), lastUsedSpace())
        }
        return blocks.checksum()
    }


    @UseFile("./input.txt")
    @Solved("6359491814941")
    override fun part2(input: TInput): Any? {
        val fragments = input
            .parse()
            .toMutableList();

        fun firstBlankSpace(ofMinLength: Int) = fragments.indexOfFirst {
            it.id == -1 && it.len >= ofMinLength
        }

        fun collapseWhitespaceAround(idx: Int) {
            var first = idx;
            var last = idx;
            while (first >= 0 && fragments[first].id == -1) {
                first--;
            }
            first++;
            while (last < fragments.size && fragments[last].id == -1) {
                last++;
            }

            last--;

            val whitespaceRange = first..last;

            val totalWhitespaceLength = whitespaceRange
                .map(fragments::get)
                .map(Fragment::len)
                .sum();

            fragments.replaceRange(whitespaceRange, Fragment(-1, totalWhitespaceLength));
        }

        fun swap(blankSpaceIdx: Int, usedSpaceIdx: Int) {
            assert(fragments[blankSpaceIdx].id == -1 && fragments[usedSpaceIdx].id != -1) { "invalid args" };
            fragments.swap(blankSpaceIdx, usedSpaceIdx);
            collapseWhitespaceAround(usedSpaceIdx);
        }

        fragments
            .filter {
                it.id != -1;
            }
            .reversed()
            .forEach {
                val whitespaceIdx = firstBlankSpace(it.len);
                if (whitespaceIdx == -1) {
                    return@forEach;
                }
                val curIdx = fragments.indexOf(it);
                if (curIdx < whitespaceIdx) {
                    return@forEach;
                }
                val whitespace = fragments[whitespaceIdx];
                if (whitespace.len == it.len) {
                    swap(whitespaceIdx, curIdx);
                } else {
                    // if the whitespace is more than we need, split it into used + extra
                    val used = it.len;
                    val remaining = whitespace.len - used;
                    fragments[whitespaceIdx] = Fragment(-1, remaining);
                    fragments.add(whitespaceIdx, Fragment(-1, used));
                    // +1 because we added one element and curIdx is always after whitespaceIdx
                    swap(whitespaceIdx, curIdx + 1);
                }

            }
        return fragments
            .toBlocks()
            .checksum();
    }
}