package zip.sadan.util.math

val Long.isEven: Boolean
    get() = this % 2 == 0L

val Int.isEven: Boolean
    get() = this % 2 == 0

val Long.isOdd: Boolean
    get() = this % 2 == 1L

val Int.isOdd: Boolean
    get() = this % 2 == 1
