import utils.day
import utils.mapToInts
import utils.unzip
import utils.zip
import kotlin.math.abs

fun main() = day(1) {

	part1 {
		val input = readLines().map { it.split("   ") }
		val left = input.map { it[0].toInt() }.sorted()
		val right = input.map { it[1].toInt() }.sorted()
		left.zip(right).sumOf { (l, r) -> abs(l - r) }
	}

	part2 {
		val input = readLines().map { it.split("   ") }
		val left = input.map { it[0].toInt() }
		val right = input.map { it[1].toInt() }
		left.sumOf { l -> l * right.count { r -> r == l } }
	}

	// ALTERNATIVE SOLUTION

	part1 {
		readLines().unzip("   ")
			.map { it.mapToInts().sorted() }
			.zip { (l, r) -> abs(r - l) }
			.sum()
	}

	part2 {
		readLines().unzip("   ")
			.map { it.mapToInts() }
			.let { (left, right) ->
				left.sumOf { l -> l * right.count { r -> r == l } }
			}
	}

}
