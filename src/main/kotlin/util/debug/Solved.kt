package zip.sadan.util.debug

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Solved(val answer: String) {
}
