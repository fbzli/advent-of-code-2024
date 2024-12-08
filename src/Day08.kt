
import utils.*
import java.awt.Point

fun main() = day(8) {

	part1 {
		val input = readLines().unzipChars()
		val antinodes = mutableSetOf<Point>()
		input.forEachIndexed { pos1: Point, freq1: Char ->
			if (freq1 != '.') {
				input.forEachIndexed { pos2: Point, freq2: Char ->
					if (freq1 == freq2 && pos2 != pos1) {
						val distance = pos2 - pos1
						val antinode1 = pos1 - distance
						if (input.containsIndex(antinode1)) {
							antinodes += antinode1
						}
						val antinode2 = pos2 + distance
						if (input.containsIndex(antinode2)) {
							antinodes += antinode2
						}
					}
				}
			}
		}
		antinodes.size
	}

	part2 {
		val input = readLines().unzipChars()
		val antinodes = mutableSetOf<Point>()
		input.forEachIndexed { pos1: Point, freq1: Char ->
			if (freq1 != '.') {
				input.forEachIndexed { pos2: Point, freq2: Char ->
					if (pos1 == pos2) {
						antinodes += pos1
					} else if (freq1 == freq2) {
						val distance = pos2 - pos1
						var antinode1 = pos1 - distance
						while (input.containsIndex(antinode1)) {
							antinodes += antinode1
							antinode1 -= distance
						}
						var antinode2 = pos2 + distance
						while (input.containsIndex(antinode2)) {
							antinodes += antinode2
							antinode2 += distance
						}
					}
				}
			}
		}
		antinodes.size
	}

}
