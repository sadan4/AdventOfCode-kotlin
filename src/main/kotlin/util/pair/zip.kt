package zip.sadan.util.pair

fun <T> Pair<List<T>, List<T>>.zip() = if (first.size == second.size) first.mapIndexed { index, t ->
    listOf(t, second[index])
} else throw IllegalArgumentException("Lists must have the same size")