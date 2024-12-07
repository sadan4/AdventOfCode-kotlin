package zip.sadan.util.input

/**
 * converts a string seperated only by spaces to a list of integers
 */
fun String.numbers(): List<Int> = this
    .trim()
    .split(Regex(" +"))
    .map {
        it.toInt()
    }
fun List<String>.numbers(): List<List<Int>> = this.map {
    it.numbers()
}