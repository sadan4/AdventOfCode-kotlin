package zip.sadan.util

class Cycle<T>(private val items: List<T>) {
    private var curIndex = items.size - 1
    fun use(): T {
        curIndex = (curIndex + 1) % items.size
        return items[curIndex]
    }
}