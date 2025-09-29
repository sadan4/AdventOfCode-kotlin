package zip.sadan.util.collections.list

fun <T> MutableList<T>.replaceRange(start: Int, exclusiveEnd: Int, item: T) = replaceRange(start..<exclusiveEnd, item);
fun <T> MutableList<T>.replaceRange(range: IntRange, item: T) {
    if (range.first > range.last) {
        throw IllegalArgumentException("range.start (${range.first}) must be <= range.endInclusive (${range.last})");
    }
    if (range.first == range.last) {
        return this.swap(range.first, range.last);
    }
    repeat(range.last - range.first) {
        this.removeAt(range.first + 1);
    }
    this[range.first] = item;
}