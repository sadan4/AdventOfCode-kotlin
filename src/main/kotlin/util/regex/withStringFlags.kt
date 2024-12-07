package zip.sadan.util.regex

fun Regex(pattern: String, flags: String) = Regex(pattern, flags.map {
    when (it) {
        'i' -> RegexOption.IGNORE_CASE
        'm' -> RegexOption.MULTILINE
        's' -> RegexOption.DOT_MATCHES_ALL
        else -> throw IllegalArgumentException("Unknown flag: $it")
    }
}.toSet())
