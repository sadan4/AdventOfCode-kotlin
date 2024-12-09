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
    var d1time: Long? = null
    var day1ans: Any? = null;
    var d2time: Long? = null
    var day2ans: Any? = null;
    try {
         d1time = measureNanoTime {
            day1ans = today.runDay1()
        }
    } catch (e: Exception) {
        println("Error running day 1")
        e.printStackTrace()
    }
    try {
        d2time = measureNanoTime {
            day2ans = today.runDay2()
        }
    } catch (e: Exception) {
        println("Error running day 2")
        e.printStackTrace()
    }
    println("Part 1 time: ${d1time?.div(1e6) ?: "null"}ms")
    println("Part 1: ${day1ans ?: "null"}")
    println("Part 2 time: ${d2time?.div(1e6) ?: "null"}ms")
    println("Part 2: ${day2ans ?: "null"}")
}
