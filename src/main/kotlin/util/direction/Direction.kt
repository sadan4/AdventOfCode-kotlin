package zip.sadan.util.direction

import zip.sadan.util.twoD.Coord

enum class Direction : IHasShift {
    N {
        override fun toShift(): Coord = Coord(0, 1)
    },
    NE {
        override fun toShift(): Coord = Coord(1, 1)
    },
    E {
        override fun toShift(): Coord = Coord(1, 0)
    },
    SE {
        override fun toShift(): Coord = Coord(1, -1)
    },
    S {
        override fun toShift(): Coord = Coord(0, -1)
    },
    SW {
        override fun toShift(): Coord = Coord(-1, -1)
    },
    W {
        override fun toShift(): Coord = Coord(-1, 0)
    },
    NW {
        override fun toShift(): Coord = Coord(-1, 1)
    };
}

fun abc(): Unit {
    Direction.NW.name
}

