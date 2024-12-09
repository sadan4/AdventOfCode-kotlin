package zip.sadan.util.debug

import zip.sadan.util.IEnumValue

interface ITerminalCode: IEnumValue {
    val code
        get() = this.ordinal.toString()
}