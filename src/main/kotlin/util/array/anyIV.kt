package zip.sadan.util.array

fun <T> List<T>.anyIV(func: (index: Number, value: T) -> Boolean): Boolean {
    for (i in this.indices) {
        if (func(i, this[i])) {
            return true
        }
    }
    return false
}