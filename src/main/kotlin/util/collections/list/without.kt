package zip.sadan.util.collections.list

infix fun <T> Iterable<T>.without(element: T) = this.filter {
    it != element
}