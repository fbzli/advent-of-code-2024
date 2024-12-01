@file:Suppress("NOTHING_TO_INLINE")

package utils

/**
 * Split each element in a single list into separate lists, creating an XY matrix.
 */
fun <T, R> List<T>.unzip(splitter: (T) -> List<R>): List<List<R>> {
	if (isEmpty()) return emptyList()
	val cols = splitter(first()).map { ArrayList<R>(size).apply { add(it) } }
	subList(1, size).forEachIndexed { ri, row ->
		val splits = splitter(row)
		require(splits.size == cols.size) { "Inconsistent column count on row ${ri + 1}" }
		splits.forEachIndexed { ci, cell ->
			cols[ci].add(cell)
		}
	}
	return cols
}

/**
 * Split each String in a single list into separate lists, creating an XY matrix.
 */
inline fun List<String>.unzip(separator: String): List<List<String>> {
	return unzip { s -> s.split(separator) }
}

/**
 * Zip a list of lists into a single list using a zipper function.
 */
fun <T, R> List<List<T>>.zip(zipper: (List<T>) -> R): List<R> {
	if (isEmpty()) return emptyList()
	val rowCount = first().size
	require(all { it.size == rowCount }) { "Inconsistent element count" }
	val result = ArrayList<R>(rowCount)
	for (r in 0 until rowCount) {
		result.add(zipper(map { it[r] }))
	}
	return result
}

/**
 * Map List to List of Ints.
 */
inline fun List<String>.mapToInts(): List<Int> {
	return map { it.toInt() }
}

/**
 * Take the first two elements of a list and return them as a Pair.
 */
inline fun <T> List<T>.toPair(): Pair<T, T> {
	require(size >= 2)
	return get(0) to get(1)
}
