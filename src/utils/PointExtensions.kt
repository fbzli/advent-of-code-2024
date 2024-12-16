@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "unused", "NonAsciiCharacters")

package utils

import java.awt.Point

operator fun Point.component1() = x
operator fun Point.component2() = y

operator fun Point.plus(p: Point) = Point(x + p.x, y + p.y)
operator fun Point.minus(p: Point) = Point(x - p.x, y - p.y)
operator fun Point.times(s: Int) = Point(x * s, y * s)
operator fun Point.div(s: Int) = Point(x / s, y / s)
infix fun Point.mod(p: Point) = Point(x.mod(p.x), y.mod(p.y))

inline fun Point.transpose() = Point(y, x)

inline fun Point.`▲`(d: Int = 1) = Point(x, y - d)
inline fun Point.`◥`(d: Int = 1) = Point(x + d, y - d)
inline fun Point.`▶`(d: Int = 1) = Point(x + d, y)
inline fun Point.`◢`(d: Int = 1) = Point(x + d, y + d)
inline fun Point.`▼`(d: Int = 1) = Point(x, y + d)
inline fun Point.`◣`(d: Int = 1) = Point(x - d, y + d)
inline fun Point.`◀`(d: Int = 1) = Point(x - d, y)
inline fun Point.`◤`(d: Int = 1) = Point(x - d, y - d)

val FourDirections = Point(0, 0).fourNeighbors()

fun Point.fourNeighbors() = listOf(`▲`(), `▶`(), `▼`(), `◀`())

val EightDirections = Point(0, 0).eightNeighbors()

fun Point.eightNeighbors() = listOf(`◤`(), `▲`(), `◥`(), `▶`(), `◢`(), `▼`(), `◣`(), `◀`())

fun Point.moveByAsciiDirection(direction: Char) = plus(direction.asciiAsVector())
