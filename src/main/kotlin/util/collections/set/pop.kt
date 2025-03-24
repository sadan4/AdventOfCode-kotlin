package zip.sadan.util.collections.set

/**
 * Returns an item from the set and removes it
 * @throws NoSuchElementException if there are no elements left
 */
fun <T> MutableSet<T>.pop(): T {
    if (this.isEmpty()) {
        throw NoSuchElementException("Calling pop on an empty set")
    }
    val item = this.iterator().next();
    this.remove(item);
    return item;
}