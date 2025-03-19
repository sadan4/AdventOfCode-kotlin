package zip.sadan.util.collections.list

fun <T> List<T>.middle(): T = if (this.size % 2 == 1) this[this.size / 2] else throw IllegalArgumentException("List size is not even")