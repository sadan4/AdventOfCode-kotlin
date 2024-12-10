package zip.sadan.util.array

import zip.sadan.util.twoD.Coord

typealias MGrid<T> = MutableList<MutableList<T>>


operator fun <T> List<List<T>>.get(c: Coord): T = this[c.y][c.x]

operator fun <T> MGrid<T>.set(c: Coord, value: T): T = this[c.y].set(c.x, value)

