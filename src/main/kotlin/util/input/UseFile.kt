package util.input

import org.intellij.lang.annotations.Language

/**
 * use a seprate test file specified by fileName
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UseFile(@Language("file-reference") val fileName: String) {
}
