package zip.sadan

import zip.sadan.util.direction.Direction
import zip.sadan.util.twoD.Coord
import zip.sadan.util.twoD.RectangularGrid

fun main() {
    val g = ArrayList<ArrayList<Char>>()
    for (x in 0..15) {
        g.add(ArrayList())
        for (y in 1..15) {
            g[x].add('=')
        }
    }
    val gr = RectangularGrid(g, Coord.ZERO)
    println(gr.formatHlPrint(*gr.coordsOfLine(Coord(9, 6), Direction.SW).toList().toTypedArray()))
}