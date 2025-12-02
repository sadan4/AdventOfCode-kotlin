fun countDigits(i: Long): Int {
    var cur = i / 10;
    var count = 1;

    while (cur != 0L) {
        count++;
        cur /= 10;
    }

    return count;
}

fun countDigits(i: Int): Int {
    var cur = i;
    var count = 1;

    while (cur != 0) {
        count++;
        cur /= 10;
    }

    return count;
}