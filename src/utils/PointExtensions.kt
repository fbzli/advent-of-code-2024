@file:Suppress("NOTHING_TO_INLINE")

package utils

import java.awt.Point

val EightNeighbors = listOf(
	Point(-1, -1), Point(0, -1), Point(+1, -1),
	Point(-1, 0), /* thats me */ Point(+1, 0),
	Point(-1, +1), Point(0, +1), Point(+1, +1)
)

operator fun Point.component1() = x

operator fun Point.component2() = y

operator fun Point.plus(p: Point) = Point(x + p.x, y + p.y)

operator fun Point.minus(p: Point) = Point(x - p.x, y - p.y)

operator fun Point.times(s: Int) = Point(x * s, y * s)

inline fun Point.transpose() = Point(y, x)
