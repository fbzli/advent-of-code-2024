import utils.day
import utils.skipLine

fun main() = day(19) {

	part1 {
		val availablePatterns = readLine().split(", ")
		skipLine()
		val patternMatcher = Regex("("+availablePatterns.joinToString("|")+")+")
		readLines().count { design ->
			design.matches(patternMatcher)
		}
	}

	part2 {
		val availablePatterns = readLine().split(", ")
		skipLine()

		val cache = mutableMapOf<String, Long>()
		fun countMatches(design: String): Long {
			return when {
				design.isEmpty() -> 1
				cache.contains(design) -> cache.getValue(design)
				else -> {
					availablePatterns.sumOf { prefix ->
						if (design.startsWith(prefix)) {
							countMatches(design.substring(prefix.length))
						} else {
							0
						}
					}.also {
						cache[design] = it
					}
				}
			}
		}

		readLines().sumOf { design ->
			countMatches(design)
		}
	}

}
