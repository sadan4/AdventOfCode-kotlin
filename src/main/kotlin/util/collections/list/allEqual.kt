package zip.sadan.util.collections.list

fun <T> Iterable<T>.allEqual(): Boolean {
    val f = first();
    return all {
        it == f
    }
}