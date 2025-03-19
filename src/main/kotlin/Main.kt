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
    val p1Ret: Pair<Long, Any?> = today.runPart1()
    val p2Ret: Pair<Long, Any?> = today.runPart2()
    println("Part 1 time: ${p1Ret.first.div(1e6)}ms")
    println("Part 1: \n${p1Ret.second ?: "null"}")
    println("Part 2 time: ${p2Ret.first.div(1e6)}ms")
    println("Part 2: \n${p2Ret.second ?: "null"}")
    var status = today.code;
    when (today.didPart1Fail) {
        "OK" -> println("Part 1 passed checks")
        "NOT_RUN" -> println("No checks for part 1")
        "ERR" -> println("Error checking part 1")
        else -> {
            status++;
            println("part 1 failed solution checks, ${today.didPart1Fail}")
        }
    }
    when (today.didPart2Fail) {
        "OK" -> println("Part 2 passed checks")
        "NOT_RUN" -> println("No checks for part 2")
        "ERR" -> println("Error checking part 2")
        else -> {
            status++;
            println("Part 2 failed solution checks, ${today.didPart2Fail}")
        }
    }
    exitProcess(status);
}
