import utils.day
import utils.indexOfSubList
import utils.printProgress

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
		val prices = readLines().map { seed ->
			var secret = seed.toLong()
			buildList {
				add(secret % 10)
				repeat(2000) {
					secret = ((secret * 64) xor secret) % 16777216
					secret = ((secret / 32) xor secret) % 16777216
					secret = ((secret * 2048) xor secret) % 16777216
					add(secret % 10)
				}
			}
		}
		val monkeys = prices.map { it to it.windowed(2).map { (a, b) -> (b - a).toInt() } }
		val sequences = buildList {
			val deltaRangeGenerated = -3..3 // assuming the optimal delta sequence only contains -3 to 3
			val deltaRangeValid = -9..9
			for (a in deltaRangeGenerated) {
				for (b in deltaRangeGenerated) {
					for (c in deltaRangeGenerated) {
						for (d in deltaRangeGenerated) {
							if ((a + b + c + d) !in deltaRangeValid) continue
							add(listOf(a, b, c, d))
						}
					}
				}
			}
		}
		var i = 0
		printProgress(i++, sequences.size)
		sequences.parallelStream().map { sequence ->
			printProgress(i++, sequences.size)
			monkeys.sumOf { (prices, deltas) ->
				val deltaIndex = deltas.indexOfSubList(sequence).takeIf { it >= 0 } ?: return@sumOf 0
				val priceIndex = deltaIndex + 4
				prices[priceIndex].toInt()
			}
		}.max(Int::compareTo).get()
	}

}
