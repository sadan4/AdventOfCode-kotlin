package zip.sadan.util.array

typealias s = String

fun s.splitMapSplit(split1: String, Split2: String): List<List<String>> = this
    .split(split1)
    .map {
        it
            .trim()
            .split(Split2)
    }

fun s.splitMapSplit(split1: String, split2: Regex): List<List<String>> = this
    .split(split1)
    .map {
        it
            .trim()
            .split(split2)
    }

fun s.splitMapSplit(split1: Regex, split2: String): List<List<String>> = this
    .split(split1)
    .map {
        it
            .trim()
            .split(split2)
    }

fun s.splitMapSplit(split1: Regex, split2: Regex): List<List<String>> = this
    .split(split1)
    .map {
        it
            .trim()
            .split(split2)
    }