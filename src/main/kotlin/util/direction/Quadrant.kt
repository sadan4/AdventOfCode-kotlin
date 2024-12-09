package zip.sadan.util.direction

//         |
//   II    |     I
//         |
//---------|----------
//         |
//   III   |    IV
//         |
enum class Quadrant : IHasCardinal {
    I {
        override fun toCardinal(): Direction = Direction.NE
    },
    II {
        override fun toCardinal(): Direction = Direction.NW
    },
    III {
        override fun toCardinal(): Direction = Direction.SW
    },
    IV {
        override fun toCardinal(): Direction = Direction.SE
    }
}