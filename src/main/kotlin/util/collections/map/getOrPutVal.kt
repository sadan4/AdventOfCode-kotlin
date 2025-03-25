package zip.sadan.util.collections.map;


fun <K, V> MutableMap<K, V>.getOrPutVal(key: K, value: V) = this.getOrPut(key) { value }