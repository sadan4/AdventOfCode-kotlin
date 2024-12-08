package zip.sadan.util.debug

fun <T> T.debug(fn: (T) -> Unit): T {
    fn(this)
    return this
}