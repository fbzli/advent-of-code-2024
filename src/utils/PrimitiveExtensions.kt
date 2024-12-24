@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.awt.Point

inline fun Boolean.toInt(): Int = if (this) 1 else 0
inline fun Boolean.toLong(): Long = if (this) 1 else 0

val AsciiDirections = listOf('^', '>', 'v', '<')

fun Char.asciiAsVector() = when (this) {
	'^' -> Point(0, -1)
	'>' -> Point(1, 0)
	'v' -> Point(0, 1)
	'<' -> Point(-1, 0)
	else -> error("Invalid direction '$this' at $this")
}

fun Char.asciiDirectionRotateRight(): Char {
	return when (this) {
		'^' -> '>'
		'>' -> 'v'
		'v' -> '<'
		'<' -> '^'
		else -> error("Invalid direction '$this'")
	}
}

operator fun String.component1() = this[0]
operator fun String.component2() = this[1]
operator fun String.component3() = this[2]
operator fun String.component4() = this[3]
operator fun String.component5() = this[4]
