package util.input

/**
 * use a seprate test file specified by fileName
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UseFile(val fileName: String) {
}
