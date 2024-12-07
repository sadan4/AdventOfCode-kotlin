package zip.sadan.util.debug

fun <T> T.log(message: String = ""): T {
    println(this.toString());
    println(message)
    return this
}