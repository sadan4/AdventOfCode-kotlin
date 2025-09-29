package zip.sadan.solutions.y24.d14

import util.input.UseFile
import zip.sadan.Solution
import zip.sadan.util.debug.Solved
import zip.sadan.util.regex.capture
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid
import kotlin.collections.map
import kotlin.math.ceil

private typealias TInput = List<String>

private data class Robot(internal val position: Coord, internal var velocity: Coord);

class Code : Solution<TInput>() {
    override val year: Number = 24
    override val day: Number = 14

    val ROBOT_REGEX = Regex("""p=(\d+),(\d+) v=(-?\d+),(-?\d+)""")

    val BOARD_SIZE = Coord(101, 103)

    private fun String.parseRobot(): Robot {
        val (px, py, vx, vy) = this
            .capture(ROBOT_REGEX)!!
            .map {
                it!!.value.toInt()
            }
        return Robot(Coord(px, py), Coord(vx, vy))
    }

    /**
     * @return a list of all the coords in each quadrant (NW, NE, SW, SE). In that order
     */
    private fun generateQuadrants(size: Coord): List<List<Coord>> {
        val xMiddle = size.x / 2
        val yMiddle = size.y / 2
        val nw = mutableListOf<Coord>()
        val ne = mutableListOf<Coord>()
        val sw = mutableListOf<Coord>()
        val se = mutableListOf<Coord>()
        List(size.x) { x ->
            List(size.y) { y ->
                Coord(x, y)
            }
        }
            .flatten()
            .forEach {
                if (it.x == xMiddle || it.y == yMiddle) {
                    return@forEach
                }
                if (it.x > xMiddle && it.y > yMiddle) {
                    se.add(it)
                } else if (it.x > xMiddle) {
                    ne.add(it)
                } else if (it.y > yMiddle) {
                    sw.add(it)
                } else {
                    nw.add(it)
                }
            }

        return listOf(nw, ne, sw, se)
    }

    private fun List<Coord>.normalizeRobotPosition() = this
        .map {
            var x = it.x % BOARD_SIZE.x
            var y = it.y % BOARD_SIZE.y
            if (x < 0) {
                x += BOARD_SIZE.x
            }
            if (y < 0) {
                y += BOARD_SIZE.y
            }
            Coord(x, y)
        }
    // HTML style coords

    @Solved("233709840")
    @UseFile("./input.txt")
    override fun part1(input: TInput): Any? {
        val robots = input
            .map {
                it.parseRobot()
            }
            .map {
                it.position + (it.velocity * 100)
            }
            .normalizeRobotPosition()
        val grid = RectangularGrid(List(BOARD_SIZE.y) {
            List(BOARD_SIZE.x) {
                0
            }
        })
        robots.forEach {
            if (it !in grid) {
                error("this should never happen")
            }
            grid[it]++
        }
        return generateQuadrants(BOARD_SIZE)
            .map {
                var acc = 0
                for (coord in it) {
                    acc += grid[coord]
                }
                acc
            }
            .reduce { a, b ->
                a * b
            }
    }

    // This is a bad problem
    // see: https://www.youtube.com/watch?v=hhRC8XrXY1o
    override fun part2(input: TInput): Any? {
        val robots = input.map {
            it.parseRobot()
        }
        for (i in listOf(28, 55)) {
            val grid = RectangularGrid(List(BOARD_SIZE.y) {
                List(BOARD_SIZE.x) {
                    ' '
                }
            })
            robots
                .map {
                    it.position + (it.velocity * i)
                }
                .normalizeRobotPosition()
                .forEach {
                    grid[it] = '#'
                }
            println("$i")
            println(grid)
            println("\n===================================================================================\n")
        }
        return null
    }
}