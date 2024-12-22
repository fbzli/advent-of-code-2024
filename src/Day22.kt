import utils.day

fun main() = day(22) {

	part1 {
		readLines().sumOf { seed ->
			var secret = seed.toLong()
			repeat(2000) {
				secret = ((secret * 64) xor secret) % 16777216
				secret = ((secret / 32) xor secret) % 16777216
				secret = ((secret * 2048) xor secret) % 16777216
			}
			secret
		}
	}

	part2 {
		val monkeys = readLines().map { seed ->
			var secret = seed.toLong()
			val deltas = mutableListOf<Long>()
			val prices = buildList {
				add(secret % 10)
				repeat(2000) {
					secret = ((secret * 64) xor secret) % 16777216
					secret = ((secret / 32) xor secret) % 16777216
					secret = ((secret * 2048) xor secret) % 16777216
					add(secret % 10)
					deltas.add(get(size - 1) - get(size - 2))
				}
			}
			prices to deltas
		}
		val sequenceSum = mutableMapOf<List<Long>, Long>()
		monkeys.forEach { (prices, deltas) ->
			deltas.windowed(4)
				.mapIndexed { i, sequence -> i to sequence }
				.distinctBy { it.second }
				.forEach { (i, sequence) ->
					sequenceSum.compute(sequence) { sequence, oldValue ->
						(oldValue ?: 0) + prices[i + 4]
					}
				}
		}
		sequenceSum.maxOf { it.value }
	}

}
