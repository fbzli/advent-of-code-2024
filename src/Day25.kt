import utils.day
import utils.split
import utils.unzipChars

fun main() = day(25) {

	part1 {
		val input = lineSequence().split { it.isBlank() }
		val locks = input.filter { it.first() == "#####" }.map { it.unzipChars().map { it.count { c -> c == '#' } } }
		val keys = input.filter { it.last() == "#####" }.map { it.unzipChars().map { it.count { c -> c == '#' } } }
		require(locks.size + keys.size == input.size)

		locks.sumOf { lock ->
			keys.count { key ->
				lock.zip(key).all { (l, k) -> l + k <= 7 }
			}
		}
	}

	part2 {
		'★'
	}

}
