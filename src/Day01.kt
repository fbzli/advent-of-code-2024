import utils.day
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
		val left = input.map { it[0].toInt() }.sorted()
		val right = input.map { it[1].toInt() }.sorted()
		left.sumOf { l -> l * right.count { r -> r == l } }
	}

}
