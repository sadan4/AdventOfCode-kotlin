package zip.sadan

import zip.sadan.solutions.y24.d1.Code
import kotlin.system.exitProcess


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
    val d1Ret: Pair<Long, Any?> = today.runDay1()
    val d2Ret: Pair<Long, Any?> = today.runDay2()
    println("Part 1 time: ${d1Ret.first.div(1e6)}ms")
    println("Part 1: \n${d1Ret.second ?: "null"}")
    println("Part 2 time: ${d2Ret.first.div(1e6)}ms")
    println("Part 2: \n${d2Ret.second ?: "null"}")
    var status = 0;
    when (today.didDay1Fail) {
        "OK" -> println("Day 1 passed checks")
        "NOT_RUN" -> println("No checks for day 1")
        "ERR" -> println("Error checking day 1")
        else -> {
            status++;
            println("Day 1 failed solution checks, ${today.didDay1Fail}")
        }
    }
    when (today.didDay2Fail) {
        "OK" -> println("Day 2 passed checks")
        "NOT_RUN" -> println("No checks for day 2")
        "ERR" -> println("Error checking day 2")
        else -> {
            status++;
            println("Day 2 failed solution checks, ${today.didDay2Fail}")
        }
    }
    exitProcess(status);
}
