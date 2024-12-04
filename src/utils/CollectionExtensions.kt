@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.awt.Point

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
 * Split each String in a single list into a list of its Chars, creating an XY matrix.
 */
inline fun List<String>.unzipChars(): List<List<Char>> {
	return unzip { s -> s.toCharArray().toList() }
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

/**
 * Get the element at the specified XY index or null if the index is out of bounds.
 */
inline fun <T> List<List<T>>.getOrNull(x: Int, y: Int): T? {
	return getOrNull(y)?.getOrNull(x)
}

/**
 * Get the element at the specified XY index or null if the index is out of bounds.
 */
inline fun <T> List<List<T>>.getOrNull(point: Point): T? {
	return getOrNull(point.y)?.getOrNull(point.x)
}

/**
 * Get the element at the specified XY index.
 */
inline operator fun <T> List<List<T>>.get(point: Point): T {
	return this[point.y][point.x]
}

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
inline fun <T> List<List<T>>.sumOfIndexed(selector: (xy: Point, value: T) -> Int): Long {
	var sum = 0L
	forEachIndexed { xy, value ->
		sum += selector(xy, value)
	}
	return sum
}

/**
 * Returns the number of elements matching the given [predicate].
 */
inline fun <T> List<List<T>>.countIndexed(predicate: (xy: Point, value: T) -> Boolean): Int {
	var count = 0
	forEachIndexed { xy, value ->
		if (predicate(xy, value)) {
			count++
		}
	}
	return count
}

/**
 * Performs the given [action] on each element, providing sequential index with the element.
 * @param [action] function that takes the index of an element and the element itself
 * and performs the action on the element.
 */
inline fun <T> List<List<T>>.forEachIndexed(action: (xy: Point, value: T) -> Unit) {
	for (y in indices) {
		for (x in this[y].indices) {
			action(Point(x, y), this[y][x])
		}
	}
}

