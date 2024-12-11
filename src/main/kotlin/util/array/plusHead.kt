package zip.sadan.util.array

infix fun <T> T.addHead(other: List<T>): List<T> = listOf(this) + other