package zip.sadan.util.collections.list

infix fun <T> T.addHead(other: List<T>): List<T> = listOf(this) + other