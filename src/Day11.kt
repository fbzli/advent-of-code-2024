import utils.day

fun main() = day(11) {

	part1 {
		val stones = readLine().split(" ").map { it.toLong() }
		(1..25).fold(stones) { acc, _ ->
			acc.flatMap { number ->
				val str = number.toString()
				when {
					number == 0L -> listOf(1L)
					str.length % 2 == 0 -> str.chunked(str.length / 2).map { it.toLong() }
					else -> listOf(number * 2024)
				}
			}
		}.count()
	}

	part2 {
		val stones = readLine().split(" ").map { it.toLong() }
		val n = 75
		val cache = List<MutableMap<Long, Long>>(n) { mutableMapOf() } // cache[iterationsLeft][number] = resulting stone count

		fun blink(number: Long, i: Int): Long {
			if (i == 0) return 1
			val j = i - 1
			cache[j][number]?.let { return it }
			val str = number.toString()
			val count = when {
				number == 0L -> blink(1L, j)
				str.length % 2 == 0 -> str.chunked(str.length / 2).sumOf { blink(it.toLong(), j) }
				else -> blink(number * 2024, j)
			}
			cache[j][number] = count
			return count
		}

		stones.sumOf { number ->
			blink(number, n)
		}
	}

}
