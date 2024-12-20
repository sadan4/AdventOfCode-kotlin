package zip.sadan

import util.input.UseFile
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

    public fun runDay1(): Pair<Long, Any?> {
        val fn = this::class.functions.find {
            it.name == "part1"
        } ?: throw RuntimeException("part1 not found")
        val args = this.generateInput<Any>(fn)
        var ret: Any?
        val time = measureNanoTime {
            try {
                ret = fn.call(this, args)
            } catch (e: Throwable) {
                ret = "Error running day 1:\n${e.stackTraceToString()}"
            }
        }
        return time to ret
    }

    public fun runDay2(): Pair<Long, Any?> {
        val fn = this::class.functions.find {
            it.name == "part2"
        } ?: throw RuntimeException("part2 not found")
        val args = this.generateInput<Any>(fn)
        var ret: Any?
        val time = measureNanoTime {
            try {
                ret = fn.call(this, args)
            } catch (e: Throwable) {
                ret = "Error running day 2:\n${e.stackTraceToString()}"
            }
        }
        return time to ret
    }
}