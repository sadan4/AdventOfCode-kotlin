package zip.sadan

import zip.sadan.util.direction.Direction
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid

fun main() {
    val g = ArrayList<ArrayList<Int>>()
    for (x in 0..7) {
        g.add(ArrayList())
        for (y in 1..8) {
            g[x].add(y+(8*x))
        }
    }
    println(g)
    val gr = RectangularGrid(g, Coord.ZERO)
    println(gr)
    println(Direction.S.toShift())
    println(Direction.N.toShift())
    println(gr.formatHlPrint(*gr.coordsOfLine(Coord(3, 3), Direction.SE).toTypedArray()))
    println(gr.formatHlPrint(*gr.coordsOfLine(Coord(3, 3), Direction.NE).toTypedArray()))
}