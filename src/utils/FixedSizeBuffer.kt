package utils

class FixedSizeBuffer<V>(private val maxSize: Int) {
	private val buffer = MutableList<V?>(maxSize) { null }
	private var index = 0

	fun add(element: V) {
		buffer[index] = element
		index = (index + 1) % maxSize
	}

	fun getBuffer(): List<V> {
		return (buffer.slice(index..<maxSize) +
				buffer.slice(0..<index))
			.filterNotNull()
	}
}
