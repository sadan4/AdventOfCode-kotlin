package zip.sadan.util.collections.set

fun <T> HashSet<T>.containsAny(things: Iterable<T>): Boolean = things.any {
    this.contains(it)
}