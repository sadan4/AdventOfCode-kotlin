package zip.sadan

import zip.sadan.solutions.y24.d1.Code


val YEAR = System.getProperty("YEAR")
val DAY = System.getProperty("DAY")

fun main() {
    if (YEAR == null || DAY == null) {
        throw RuntimeException("YEAR and DAY must be set. run this code with `pnpm solve -y YEAR -d DAY`")
    }
    println("Running year $YEAR day $DAY")
    val test = Code()
    val today = Class
        .forName("zip.sadan.solutions.y$YEAR.d$DAY.Code")
        .getDeclaredConstructor()
        .newInstance() as Solution<*>
    var d1Ret: Pair<Long, Any?> = today.runDay1()
    var d2Ret: Pair<Long, Any?> = today.runDay2()
    println("Part 1 time: ${d1Ret.first.div(1e6)}ms")
    println("Part 1: \n${d1Ret.second ?: "null"}")
    println("Part 2 time: ${d2Ret.first.div(1e6)}ms")
    println("Part 2: \n${d2Ret.second ?: "null"}")
}
