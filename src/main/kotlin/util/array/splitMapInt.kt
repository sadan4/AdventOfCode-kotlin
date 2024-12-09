package zip.sadan.util.array;

typealias strarr = List<String>

fun strarr.splitMapInt(delimiter: String): List<List<Int>> {
    return this
        .map {
            it
                .split(delimiter)
                .map {
                    it
                        .trim()
                        .toInt()
                }
        }
}

fun strarr.splitMapIntPair(delimiter: String): List<Pair<Int, Int>> {
    return this
        .map {
            it
                .split(delimiter)
                .map {
                    it
                        .trim()
                        .toInt()
                }
                .toPair()
        }
}

fun strarr.splitMapInt(delimiter: Regex): List<List<Int>> {
    return this
        .map {
            it
                .split(delimiter)
                .map {
                    it
                        .trim()
                        .toInt()
                }
        }
}

fun strarr.splitMapIntPair(delimiter: Regex): List<Pair<Int, Int>> {
    return this
        .map {
            it
                .split(delimiter)
                .map {
                    it
                        .trim()
                        .toInt()
                }
                .toPair()
        }
}