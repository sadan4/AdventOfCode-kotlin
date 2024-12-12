package zip.sadan.util.direction

import zip.sadan.util.twoD.Coord

enum class Linear : IHasShift, IHasCardinal, IHasInverse<Linear>, IHasCompliment<Linear> {
    N {
        override fun toCardinal(): Direction = Direction.N

        override fun toShift(): Coord = Direction.N.toShift()

        override fun compliment(): Linear = S

        override fun inverse(): Pair<Linear, Linear> = E to W
    },
    E {
        override fun toCardinal(): Direction = Direction.E

        override fun toShift(): Coord = Direction.E.toShift()

        override fun compliment(): Linear = W

        override fun inverse(): Pair<Linear, Linear> = N to S
    },
    S {
        override fun toCardinal(): Direction = Direction.S

        override fun toShift(): Coord = Direction.S.toShift()

        override fun compliment(): Linear = N

        override fun inverse(): Pair<Linear, Linear> = E to W
    },
    W {
        override fun toCardinal(): Direction = Direction.W

        override fun toShift(): Coord = Direction.W.toShift()

        override fun compliment(): Linear = E

        override fun inverse(): Pair<Linear, Linear> = N to S
    };

}
