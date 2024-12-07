package zip.sadan.util.array

fun <T> List<T>.toPair(): Pair<T, T> {
    if(this.size != 2) {
        throw IllegalArgumentException("List must have exactly 2 elements");
    }
    return Pair(this[0], this[1]);
}