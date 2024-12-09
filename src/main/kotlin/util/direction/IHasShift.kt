package zip.sadan.util.direction

import zip.sadan.util.twoD.Coord

interface IHasShift {
    fun toShift(): Coord
}