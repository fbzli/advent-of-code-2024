import utils.*
import java.awt.Point

fun main() = day(10) {

	part1 {
		val map = readLines().unzipChars().mapValues { it.digitToInt() }
		map.sumOfIndexed { xy, height ->
			if (height == 0) map.getPeaks(xy).toSet().size else 0
		}
	}

	part2 {
		val map = readLines().unzipChars().mapValues { it.digitToInt() }
		map.sumOfIndexed { xy, height ->
			if (height == 0) map.getPeaks(xy).size else 0
		}
	}

}

/** Get all reachable peaks, including duplicates if reachable through different paths */
private fun List<List<Int>>.getPeaks(start: Point): List<Point> {
	if (get(start) == 9) return listOf(start)
	return start.fourNeighbors().flatMap { xy ->
		if (getOrNull(xy) == get(start) + 1) getPeaks(xy) else emptyList()
	}
}
