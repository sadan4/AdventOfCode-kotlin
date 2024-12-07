package zip.sadan.util.array

fun <T> List<T>.dropOne(index: Int) = this.subList(0, index) + this.subList(index + 1, this.size)