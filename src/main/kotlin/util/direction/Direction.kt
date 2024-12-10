package zip.sadan.util.direction

import zip.sadan.util.twoD.Coord

enum class Direction : IHasShift {

    N {
        private val shift = Coord(0, 1)
        override fun toShift(): Coord = shift
    },
    NE {
        private val shift = Coord(1, 1)
        override fun toShift(): Coord = shift
    },
    E {
        private val shift = Coord(1, 0)
        override fun toShift(): Coord = shift
    },
    SE {
        private val shift = Coord(1, -1)
        override fun toShift(): Coord = shift
    },
    S {
        private val shift = Coord(0, -1)
        override fun toShift(): Coord = shift
    },
    SW {
        private val shift = Coord(-1, -1)
        override fun toShift(): Coord = shift
    },
    W {
        private val shift = Coord(-1, 0)
        override fun toShift(): Coord = shift
    },
    NW {
        private val shift = Coord(-1, 1)
        override fun toShift(): Coord = shift
    };
}

