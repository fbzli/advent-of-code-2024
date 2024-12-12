@file:Suppress("NonAsciiCharacters", "LocalVariableName")

import utils.*
import java.awt.Point

fun main() = day(12) {

	part1 {
		/** Flood fill the area of the matrix starting from [xy] with the corresponding lowercase char
		 * and return the area and length of perimeters of the area. */
		fun List<MutableList<Char>>.floodFill(xy: Point): Pair<Int, Int> {
			val value = this[xy]
			this[xy] = this[xy].lowercaseChar()
			var perimeters = 0
			var area = 1
			xy.fourNeighbors().forEach { neighbor ->
				if (this.getOrNull(neighbor) == value) {
					val (nArea, nPerimeters) = floodFill(neighbor)
					area += nArea
					perimeters += nPerimeters
				} else if (this.getOrNull(neighbor) == value.lowercaseChar()) {
					// nothing
				} else {
					perimeters++
				}
			}
			return area to perimeters
		}

		val map = readLines().unzipChars().mutable()
		map.sumOfIndexed { xy: Point, value: Char ->
			if (value.isUpperCase()) {
				val (area, perimeters) = map.floodFill(xy)
				area * perimeters
			} else {
				0
			}
		}
	}

	part2 {
		/** Flood fill the area of the matrix starting from [xy] with dots and return the area. */
		fun List<MutableList<Char>>.floodFill(xy: Point): Int {
			val value = this[xy]
			this[xy] = '.'
			return xy.fourNeighbors().sumOf { neighbor ->
				if (this.getOrNull(neighbor) == value) {
					floodFill(neighbor)
				} else {
					0
				}
			} + 1
		}

		/** Find the number of corners of the '.' area in the matrix. */
		fun List<MutableList<Char>>.countCorners(): Int {
			val isPoint = { xy: Point -> getOrNull(xy) == '.' }
			var corners = 0
			for (x in -1 until width) {
				for (y in -1 until height) {
					// 2-by-2 masks to match corners
					val xy = Point(x, y)
					val `◤` = isPoint(xy)
					val `◥` = isPoint(xy.`▶`())
					val `◣` = isPoint(xy.`▼`())
					val `◢` = isPoint(xy.`◢`())
					if (`◤` != `◢` && `◣` == `◥` || `◤` == `◢` && `◣` != `◥`) {
						corners++
					} else if (`◤` == `◢` && `◣` == `◥` && `◤` != `◥`) {
						corners += 2
					}
				}
			}
			return corners
		}

		val original = readLines().unzipChars()
		val progressed = original.mutable()
		progressed.sumOfIndexed { xy: Point, value: Char ->
			if (value != '.') {
				progressed.floodFill(xy)
				val map = original.mutable()
				val area = map.floodFill(xy)
				// for any polygon, the number of corners is equal to the number of edges.
				map.countCorners() * area
			} else {
				0
			}
		}
	}

}
