package zip.sadan.util.input

fun makeLines(x: String): List<String> {
    return x.split("\n").dropLastWhile {
        it.isEmpty()
    }
}