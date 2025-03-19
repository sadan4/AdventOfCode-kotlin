package zip.sadan.util.collections.list

import zip.sadan.util.twoD.Coord


fun <T> List<List<T>>.window2d(upperLeft: Coord, bottomRight: Coord): List<List<T>> {
    val (x1, y1) = upperLeft
    val (x2, y2) = bottomRight
    return this
        .slice(y1..y2)
        .map {
            it.slice(x1..x2)
        }
}