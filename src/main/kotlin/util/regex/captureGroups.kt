package zip.sadan.util.regex

fun String.capture(regex: Regex, startAt: Int = 0) = regex.find(this, startAt)?.groups?.drop(1)

fun String.captureAll(regex: Regex, startAt: Int = 0) = regex.findAll(this, startAt).toList().map { it.groups.drop(1) }