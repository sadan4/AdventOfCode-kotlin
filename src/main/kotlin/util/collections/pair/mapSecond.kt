package zip.sadan.util.collections.pair

fun <A, B> Pair<A, B>.mapSecond(mapper: (B) -> B) = first to mapper(second)