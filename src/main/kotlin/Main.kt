package zip.sadan

import zip.sadan.solutions.y24.d1.Code
import kotlin.system.measureNanoTime

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
    lateinit var d1Ret: Pair<Long, Any?>
    lateinit var d2Ret: Pair<Long, Any?>
    try {
            d1Ret = today.runDay1()
    } catch (e: Exception) {
        println("Error running day 1")
        e.printStackTrace()
    }
    try {
            d2Ret = today.runDay2()
    } catch (e: Exception) {
        println("Error running day 2")
        e.printStackTrace()
    }
    println("Part 1 time: ${d1Ret.first.div(1e6)}ms")
    println("Part 1: \n${d1Ret.second ?: "null"}")
    println("Part 2 time: ${d2Ret.first.div(1e6)}ms")
    println("Part 2: \n${d2Ret.second ?: "null"}")
}
