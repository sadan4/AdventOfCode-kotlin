package zip.sadan.util.direction

import zip.sadan.util.twoD.Coord

enum class Diagonal : IHasCardinal, IHasShift {
    NE {
        override fun toCardinal(): Direction = Direction.NE

        override fun toShift(): Coord = Direction.NE.toShift()
    }, SE {
        override fun toCardinal(): Direction = Direction.SE

        override fun toShift(): Coord = Direction.SE.toShift()
    }, NW {
        override fun toCardinal(): Direction = Direction.NW

        override fun toShift(): Coord = Direction.NW.toShift()
    }, SW {
        override fun toCardinal(): Direction = Direction.SW

        override fun toShift(): Coord = Direction.SW.toShift()
    }
}