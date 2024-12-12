@file:Suppress("NonAsciiCharacters", "LocalVariableName")

import utils.*
import java.awt.Point

fun main() = day(12) {

	part1 {
		val map = readLines().unzipChars().mapValues { it.code }.mutable()
		map.sumOfIndexed { xy: Point, value: Int ->
			if (value > 0) {
				val (area, perimeters) = map.negateArea(xy)
				area * perimeters
			} else {
				0
			}
		}
	}

	part2 {
		/** Find the number of corners of the area with negative numbers in the matrix. */
		fun List<MutableList<Int>>.countCorners(): Int {
			val isInArea = { value: Int? -> (value ?: 0) < 0 }
			// 2-by-2 masks to match corners
			return windowed(size = Point(2,2), partial = true).sumOf {
				val `◤` = isInArea(it[0, 0])
				val `◥` = isInArea(it[1, 0])
				val `◣` = isInArea(it[0, 1])
				val `◢` = isInArea(it[1, 1])
				when {
					`◤` != `◢` && `◣` == `◥` || `◤` == `◢` && `◣` != `◥` -> 1
					`◤` == `◢` && `◣` == `◥` && `◤` != `◥` -> 2
					else -> 0
				}.toInt()
			}
		}

		val input = readLines().unzipChars().mapValues { it.code }
		val map = input.mutable()
		map.sumOfIndexed { xy: Point, value: Int ->
			if (value > 0) {
				map.negateArea(xy)
				val local = input.mutable()
				val (area, _) = local.negateArea(xy)
				// for any polygon, the number of corners is equal to the number of edges.
				val corners = local.countCorners()
				corners * area
			} else {
				0
			}
		}
	}

}

/**
 * Flood fill (floodFill) the same-valued area starting from [xy] by flipping the values negative.
 * @return the size of the filled area and the length of the perimeter.
 */
private fun List<MutableList<Int>>.negateArea(xy: Point): Pair<Int, Int> {
	val value = this[xy]
	this[xy] *= -1
	var perimeters = 0
	var area = 1
	xy.fourNeighbors().forEach { neighbor ->
		if (getOrNull(neighbor) == value) {
			val (nArea, nPerimeters) = negateArea(neighbor)
			area += nArea
			perimeters += nPerimeters
		} else if (getOrNull(neighbor) != -value) {
			perimeters++
		}
	}
	return area to perimeters
}
