package zip.sadan.util.array;

fun <T> List<T>.filterFirstUnique(): List<T> = this.filterIndexed { index, t ->
    index == this.indexOf(t)
}