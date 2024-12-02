import utils.day
import utils.mapToInts
import kotlin.math.abs

fun main() = day(2) {

	part1 {
		readLines().map { it.split(" ").mapToInts() }
			.filter { it.sorted() == it || it.sortedDescending() == it }
			.count { it.windowed(2).all { (a, b) -> abs(b - a) in 1..3 } }
	}

	part2 {
		readLines().map { it.split(" ").mapToInts() }
			.count { line ->
				line.indices.any { i ->
					val corrected = line.filterIndexed { j, _ -> i != j }
					(corrected.sorted() == corrected || corrected.sortedDescending() == corrected) &&
							corrected.windowed(2).all { (a, b) -> abs(b - a) in 1..3 }
				}
			}
	}

}
