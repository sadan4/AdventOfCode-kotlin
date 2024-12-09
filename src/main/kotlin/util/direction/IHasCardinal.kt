package zip.sadan.util.direction

import zip.sadan.util.IEnumValue
import zip.sadan.util.twoD.Coord

interface IHasCardinal: IHasShift, IEnumValue {
    fun toCardinal(): Direction = Direction.valueOf(this.name)
    override fun toShift(): Coord = this.toCardinal().toShift()
}