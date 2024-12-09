package zip.sadan.solutions.y24.d4

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.twoD.Coord
import zip.sadan.util.array.Fill
import zip.sadan.util.array.mult
import zip.sadan.util.array.window2d
import zip.sadan.util.direction.Direction
import zip.sadan.util.twoD.SquareGrid

private fun List<String>.toBufferedCharArray(bufferSize: Int, bufferChar: Char): List<List<Char>> =
    Fill(this.size + (bufferSize * 2), '0').mult(bufferSize) + this
        .map {
            it
                .toCharArray()
                .toList()
        }
        .map { Fill(bufferSize, bufferChar) + it + Fill(bufferSize, bufferChar) } + Fill(
        this.size + (bufferSize * 2), '0'
    ).mult(bufferSize)

class Code : Solution<List<String>>() {
    override val year: Number = 24
    override val day: Number = 4

    override fun part1(input: List<String>): Any? {
        val mappedInput = input.toBufferedCharArray(3, '0')
        return mappedInput
            .map {
                it.mapIndexedNotNull { index, c ->
                    if (c == 'X') index else null
                }
            }
            .mapIndexed { y, ex ->
                ex.map { x ->
                    mappedInput.window2d(Coord(x - 3, y - 3), Coord(x + 3, y + 3))
                }
            }
            .filter {
                it.isNotEmpty()
            }
            .map {
                it
                    .map(::SquareGrid)
                    .map {
                        Direction
                            .entries
                            .toTypedArray()
                            .map { dir ->
                                it.getLine(dir, true) == "XMAS"
                            }
                    }
            }
            .sumOf {
                it.sumOf {
                    it.count {
                        it
                    }
                }
            }
    }

    @UseFile("input.txt")
    override fun part2(input: List<String>): Any? {
        val radius = 1
        val mappedInput = input.toBufferedCharArray(radius, '0')
        return mappedInput
            .map {
                it.mapIndexedNotNull { index, c ->
                    if (c == 'A') index else null
                }
            }
            .mapIndexed { y, As ->
                As.map { x ->
                    mappedInput.window2d(Coord(x - radius, y - radius), Coord(x + radius, y + radius))
                }
            }
            .filter {
                it.isNotEmpty()
            }
            .map {
                val diagonals = arrayOf(Pair(Direction.NE, Direction.SW), Pair(Direction.NW, Direction.SE))
                it
                    .map(::SquareGrid)
                    .map {
                        diagonals
                            .map { (first, second) ->
                                val str = "${it.getLine(first)}A${it.getLine(second)}"
                                str == "MAS" || str == "SAM"
                            }
                            .all {
                                it
                            }
                    }
            }
            .sumOf {
                it.count {
                    it
                }
            }
    }
}