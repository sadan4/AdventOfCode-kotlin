package zip.sadan.solutions.y24.d8

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.collections.list.without
import zip.sadan.util.debug.Solved
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import zip.sadan.util.twoD.times

typealias TInput = List<String>

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 8

    @Solved("291")
    @UseFile("./input.txt")
    override fun part1(input: TInput): Any? {
        val grid = RectangularGrid<Char>(input.map {
            it
                .toCharArray()
                .toList()
        })
        val allNodes = input
            .joinToString("")
            .toCharArray()
            .toHashSet()
        // we only need unique antinodes, overlapping ones dont matter
        val antinodes = HashSet<Coord>();
        // not a node, but a blank space
        allNodes.remove('.');
        for (nodeChar in allNodes) {
            val locs = grid.getAll(nodeChar);
            if (locs.size <= 1) {
                error("there should always be more than one isntance of a node in the grid");
            }
            for (antenna in locs) {
                for (other in locs without antenna) {
                    val shiftToThis = antenna - other;
                    assert(other + shiftToThis == antenna) { "Logic error, coords should equal" }
                    antinodes.add(shiftToThis + antenna)
                }
            }
        }
        return antinodes.count {
            it in grid
        }
    }

    @Solved("1015")
    @UseFile("./input.txt")
    override fun part2(input: TInput): Any? {
        val grid = RectangularGrid<Char>(input.map {
            it
                .toCharArray()
                .toList()
        })
        val allNodes = input
            .joinToString("")
            .toCharArray()
            .toHashSet()
        // we only need unique antinodes, overlapping ones dont matter
        val antinodes = HashSet<Coord>();
        // not a node, but a blank space
        allNodes.remove('.');
        for (nodeChar in allNodes) {
            val locs = grid.getAll(nodeChar);
            if (locs.size <= 1) {
                error("there should always be more than one isntance of a node in the grid");
            }
            for (antenna in locs) {
                for (other in locs without antenna) {
                    val shiftToThis = antenna - other;
                    var n = 1;
                    fun cur() = n * shiftToThis + other
                    while (cur() in grid) {
                        antinodes.add(cur())
                        n++;
                    }
                }
            }
        }
        return antinodes.count {
            it in grid
        }
    }
}