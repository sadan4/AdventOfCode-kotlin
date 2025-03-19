package zip.sadan.util.collections.list;

fun <T> List<T>.filterFirstUnique(): List<T> = this.filterIndexed { index, t ->
    index == this.indexOf(t)
}