@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.awt.Point

inline fun Boolean.toInt(): Int = if (this) 1 else 0

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
