package zip.sadan.util.debug

class ColoredString(private val str: String) {
    private val codes = mutableListOf<String>()
    private fun makePrefix(): String = "\u001B[${codes.joinToString(";")}m"
    private fun makeSuffix(): String = "\u001B[${TerminalCode.RESET.code}m"

    public fun addCode(vararg code: TerminalCode): ColoredString {
        codes.addAll(code.map { it.code })
        return this
    }

    public override fun toString(): String {
        return makePrefix() + str + makeSuffix()
    }
    /**
     * colors bewteen 0 and 255
     */
    public fun setForegroundColor(r: Int, g: Int, b: Int): ColoredString {
        addCode(TerminalCode.SET_FOREGROUND_COLOR)
        codes.add("2")
        codes.add(r.toString())
        codes.add(g.toString())
        codes.add(b.toString())
        return this
    }

    public fun setBackgroundColor(r: Int, g: Int, b: Int): ColoredString {
        addCode(TerminalCode.SET_BACKGROUND_COLOR)
        codes.add("2")
        codes.add(r.toString())
        codes.add(g.toString())
        codes.add(b.toString())
        return this
    }

}