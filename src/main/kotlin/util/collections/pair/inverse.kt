package zip.sadan.util.collections.pair

fun <A, B> Pair<A, B>.inverse(): Pair<B, A> = this.second to this.first;