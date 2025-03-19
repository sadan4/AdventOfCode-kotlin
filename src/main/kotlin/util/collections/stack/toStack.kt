package zip.sadan.util.collections.stack
import java.util.Stack;

fun <T> Collection<T>.toStack(): Stack<T> {
    val ret = Stack<T>();
    ret.addAll(this)
    return ret;
}