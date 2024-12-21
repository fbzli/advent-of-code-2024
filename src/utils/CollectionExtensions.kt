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
 * Returns a view of the portion of this list starting at the specified [fromIndex] (inclusive).
 */
inline fun <T> List<T>.subList(fromIndex: Int): List<T> {
	return subList(fromIndex.coerceAtMost(size - 1), size)
}

/**
 * Find the start index of the first occurrence of the specified subList in this list.
 * @param fromIndex inclusive, default 0
 * @param toIndex exclusive, default size
 * @return index of the first occurrence, or -1 if not found.
 */
inline fun <T> List<T>.indexOfSubList(subList: List<T>, fromIndex: Int = 0, toIndex: Int = size): Int {
	subList(fromIndex, toIndex).asSequence().windowed(subList.size).forEachIndexed { index: Int, window ->
		if (window == subList) return index + fromIndex
	}
	return -1
}

inline fun <T> List<T>.sumOfIndexedA(selector: (index: Int, value: T) -> Int): Long {
	var sum = 0L
	forEachIndexed { index, value ->
		sum += selector(index, value)
	}
	return sum
}

/**
 * Get the number of columns in this XY matrix.
 */
inline val <T> List<List<T>>.width: Int get() = size

/**
 * Get the number of rows in this XY matrix.
 */
inline val <T> List<List<T>>.height: Int get() = firstOrNull()?.size ?: 0

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
 * Set the element at the specified XY index.
 */
inline operator fun <T> List<MutableList<T>>.set(point: Point, value: T) {
	this[point.x][point.y] = value
}

/**
 * Make a mutable copy of the matrix.
 */
inline fun <T> List<List<T>>.mutable(): List<MutableList<T>> {
	return map { it.toMutableList() }
}

/**
 * Map values of the matrix to a new value.
 */
inline fun <T, R> List<List<T>>.mapValues(transform: (T) -> R): List<List<R>> {
	return map { it.map(transform) }
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
 * Find the index of the first occurrence (lowest x) of the specified value in the matrix.
 */
inline fun <T> List<List<T>>.indexOf(value: T): Point? {
	for (x in indices) {
		val y = this[x].indexOf(value)
		if (y != -1) {
			return Point(x, y)
		}
	}
	return null
}

/**
 * Transpose the matrix.
 */
inline fun <T> List<List<T>>.transpose(): List<List<T>> {
	return xyMatrix(height, width) { x, y -> this[y][x] }
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
 * Returns a list containing only the non-null results of applying the given [transform] function to each element in the original collection.
 */
inline fun <T, R> List<List<T>>.mapIndexedNotNull(transform: (xy: Point, value: T) -> R): List<R> {
	val list = mutableListOf<R>()
	forEachIndexed { xy, value ->
		transform(xy, value)?.let { list.add(it) }
	}
	return list
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

/**
 * Returns a sequence of snapshots of the window of the given size sliding along this sequence with the given step, where each snapshot is a xy matrix.
 */
inline fun <T> List<List<T>>.windowed(size: Point, step: Point = Point(1, 1), partial: Boolean = false) = Sequence {
	iterator {
		val startX = if (partial) -size.x + 1 else 0
		val startY = if (partial) -size.y + 1 else 0
		for (x in startX until width step step.x) {
			for (y in startY until height step step.y) {
				yield(List(size.x) { dx ->
					List(size.y) { dy ->
						getOrNull(x + dx, y + dy)
					}
				})
			}
		}
	}
}

/**
 * Create a mutable XY matrix with the specified dimensions and initialize each element with the [initializer] function.
 */
inline fun <T> mutableXyMatrix(width: Int, height: Int, initializer: (x: Int, y: Int) -> T): List<MutableList<T>> {
	return List(width) { x -> MutableList(height) { y -> initializer(x, y) } }
}

/**
 * Create an XY matrix with the specified dimensions and initialize each element with the [initializer] function.
 */
inline fun <T> xyMatrix(width: Int, height: Int, initializer: (x: Int, y: Int) -> T): List<List<T>> {
	return mutableXyMatrix(width, height, initializer)
}

/**
 * Create a mutable XY matrix with the specified dimensions and initialize each element with the [initializer] function.
 */
inline fun <T> mutableXyMatrix(size: Point, initializer: (x: Int, y: Int) -> T): List<MutableList<T>> {
	return mutableXyMatrix(size.x, size.y, initializer)
}

/**
 * Create an XY matrix with the specified dimensions and initialize each element with the [initializer] function.
 */
inline fun <T> xyMatrix(size: Point, initializer: (x: Int, y: Int) -> T): List<List<T>> {
	return mutableXyMatrix(size, initializer)
}

/**
 * Print an XY matrix of Chars.
 */
inline fun List<List<Char>>.print() {
	for (y in 0 until height) {
		for (x in 0 until width) {
			print(this[x][y])
		}
		println()
	}
}
