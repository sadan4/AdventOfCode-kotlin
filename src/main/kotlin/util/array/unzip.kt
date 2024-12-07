package zip.sadan.util.array

fun <A, B> List<Pair<A, B>>.unzip(): Pair<List<A>, List<B>> = this.fold(Pair(ArrayList(), ArrayList())) { acc, pair ->
    Pair(acc.first + pair.first, acc.second + pair.second)
}

fun <T> List<List<T>>.unzip(): List<List<T>> {
    val toRet = Array<ArrayList<T>>(this[0].size) { ArrayList() }
    for (i in this[0].indices) {
        this.forEach {
            toRet[i].add(it[i])
        }
    }
    return toRet.toList()
}