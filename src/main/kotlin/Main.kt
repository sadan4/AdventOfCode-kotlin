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
    println(today.runDay1())
    println(today.runDay2())
}
