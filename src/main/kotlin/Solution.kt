package zip.sadan

import util.input.UseFile
import zip.sadan.util.debug.Solved
import zip.sadan.util.input.makeLines
import java.io.File
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.jvmErasure
import kotlin.system.measureNanoTime

abstract class Solution<T> {
    abstract val year: Number
    abstract val day: Number

    abstract fun part1(input: T): Any?

    abstract fun part2(input: T): Any?

    private fun <T> generateInput(fn: KFunction<*>): T {
        val fileName = (fn.findAnnotation<UseFile>() ?: UseFile("input.txt")).fileName
        val f = File("src/main/kotlin/solutions/y$year/d$day/$fileName")
        val ret: Any = when (fn.parameters[1].type.jvmErasure) {
            File::class -> f
            String::class -> f.readText()
            List::class -> makeLines(f.readText())
            else -> throw RuntimeException("Unsupported type: ${fn.parameters[0].type.jvmErasure}")
        }
        @Suppress("UNCHECKED_CAST")
        return ret as T
    }

    public var code = 0;
    public var didPart1Fail: String = "NOT_RUN";

    public fun runPart1(): Pair<Long, Any?> {
        val fn = this::class.functions.find {
            it.name == "part1"
        } ?: throw RuntimeException("part1 not found")
        val args = this.generateInput<Any>(fn)
        var ret: Any?
        val time = measureNanoTime {
            try {
                ret = fn.call(this, args)
                try {
                    val expected = fn.findAnnotation<Solved>()?.answer;
                    if (expected != null) {
                        if (ret?.toString() != expected) {
                            didPart1Fail = "Expected $expected, but got ${ret?.toString()}";
                        } else {
                            didPart1Fail = "OK"
                        }
                    }
                } catch (e: Throwable) {
                    ret = "Error checking part 1:\n${e.stackTraceToString()}"
                    didPart1Fail = "ERR";
                }
            } catch (e: Throwable) {
                ret = "Error running part 1:\n${e.stackTraceToString()}"
                code++;
            }
        }
        return time to ret
    }

    public var didPart2Fail: String = "NOT_RUN";

    public fun runPart2(): Pair<Long, Any?> {
        val fn = this::class.functions.find {
            it.name == "part2"
        } ?: throw RuntimeException("part2 not found")
        val args = this.generateInput<Any>(fn)
        var ret: Any?
        val time = measureNanoTime {
            try {
                ret = fn.call(this, args)
                try {
                    val expected = fn.findAnnotation<Solved>()?.answer;
                    if (expected != null) {
                        if (ret?.toString() != expected) {
                            didPart2Fail = "Expected $expected, but got ${ret?.toString()}";
                        } else {
                            didPart2Fail = "OK"
                        }
                    }
                } catch (e: Throwable) {
                    ret = "Error checking part 2:\n${e.stackTraceToString()}"
                    didPart2Fail = "ERR";
                }
            } catch (e: Throwable) {
                ret = "Error running part 2:\n${e.stackTraceToString()}"
                code++;
            }
        }
        return time to ret
    }
}