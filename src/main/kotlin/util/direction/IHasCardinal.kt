package zip.sadan.util.direction

import zip.sadan.util.IEnumValue
import zip.sadan.util.twoD.Coord

interface IHasCardinal {
    fun toCardinal(): Direction
}