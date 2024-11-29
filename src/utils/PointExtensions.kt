package utils

import java.awt.Point

operator fun Point.plus(p: Point) = Point(x + p.x, y + p.y)

operator fun Point.minus(p: Point) = Point(x - p.x, y - p.y)

fun Point.transpose() = Point(y, x)
