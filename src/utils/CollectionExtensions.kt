@file:Suppress("NOTHING_TO_INLINE", "unused")

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
 * Split a list into a list of lists separated by the elements defined through the selector function.
 */
inline fun <T> List<T>.split(selector: (T) -> Boolean) = iterator().split(selector)

/**
 * Split a sequence into a list of lists separated by the elements defined through the selector function.
 */
inline fun <T> Sequence<T>.split(selector: (T) -> Boolean) = iterator().split(selector)

/**
 * Split a list into a list of lists separated by the elements defined through the selector function.
 */
inline fun <T> Iterator<T>.split(selector: (T) -> Boolean): List<List<T>> {
	val result = mutableListOf<MutableList<T>>().also { it.add(mutableListOf()) }
	var group = 0
	for (element in this) {
		if (selector(element)) {
			group++
			result.add(mutableListOf())
		} else {
			result[group].add(element)
		}
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
 * Get the middle element of a list. Length must be odd.
 */
inline fun <T> List<T>.middle(): T {
	require(size % 2 == 1)
	return get(size / 2)
}

/**
 * Get the element at the specified XY index or null if the index is out of bounds.
 */
inline fun <T> List<List<T>>.getOrNull(x: Int, y: Int): T? {
	return getOrNull(x)?.getOrNull(y)
}

/**
 * Get the element at the specified XY index or null if the index is out of bounds.
 */
inline fun <T> List<List<T>>.getOrNull(point: Point): T? {
	return getOrNull(point.x, point.y)
}

/**
 * Get the element at the specified XY index.
 */
inline operator fun <T> List<List<T>>.get(x: Int, y: Int): T {
	return this[x][y]
}

/**
 * Get the element at the specified XY index.
 */
inline operator fun <T> List<List<T>>.get(point: Point): T {
	return this[point.x][point.y]
}

/**
 * Check if the specified XY index is within the bounds of the matrix.
 */
inline fun <T> List<List<T>>.containsIndex(x: Int, y: Int): Boolean {
	return x in indices && y in get(x).indices
}

/**
 * Check if the specified XY index is within the bounds of the matrix.
 */
inline fun <T> List<List<T>>.containsIndex(point: Point): Boolean {
	return containsIndex(point.x, point.y)
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
	for (x in indices) {
		for (y in this[x].indices) {
			action(Point(x, y), this[x][y])
		}
	}
}
