package zip.sadan.util.direction

import zip.sadan.util.twoD.Coord

enum class Linear : IHasShift, IHasCardinal {
    N {
        override fun toCardinal(): Direction = Direction.N

        override fun toShift(): Coord = Direction.N.toShift()
    },
    E {
        override fun toCardinal(): Direction = Direction.E

        override fun toShift(): Coord = Direction.E.toShift()
    },
    S {
        override fun toCardinal(): Direction = Direction.S

        override fun toShift(): Coord = Direction.S.toShift()
    },
    W {
        override fun toCardinal(): Direction = Direction.W

        override fun toShift(): Coord = Direction.W.toShift()
    };

}
