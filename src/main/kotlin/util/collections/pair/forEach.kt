package zip.sadan.util.collections.pair

typealias PairOf<T> = Pair<T, T>

fun <T> PairOf<T>.forEach(fn: (T) -> Unit) {
    fn(first)
    fn(second)
}

fun <T> PairOf<T>.forEachIndexed(fn: (Int, T) -> Unit) {
    fn(0, first)
    fn(1, second)
}