package zip.sadan.util.collections.pair

fun <A, B> Pair<A, B>.mapFirst(mapper: (A) -> A) = mapper(first) to second