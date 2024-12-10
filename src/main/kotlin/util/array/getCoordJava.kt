package zip.sadan.util.array

import zip.sadan.util.twoD.Coord
import java.util.List;


operator fun <T> List<List<T>>.get(c: Coord): T = this.get(c.y).get(c.x)

operator fun <T> List<List<T>>.set(c: Coord, value: T): T = this.get(c.y).set(c.x, value)
