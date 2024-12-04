import utils.*

fun main() = day(4) {

	part1 {
		val input = readLines().unzipChars()
		input.sumOfIndexed { xy, _ ->
			EightNeighbors.count { direction ->
				"XMAS".mapIndexed { i, c -> i to c }.all { (i, char) ->
					input.getOrNull(xy + direction * i) == char
				}
			}
		}
	}

	@Suppress("NonAsciiCharacters", "LocalVariableName")
	part2 {
		val input = readLines().unzipChars()
		input.countIndexed { (x, y), c ->
			c == 'A' && run {
				val `◣` = input.getOrNull(x - 1, y + 1)
				val `◥` = input.getOrNull(x + 1, y - 1)
				val `◢` = input.getOrNull(x + 1, y + 1)
				val `◤` = input.getOrNull(x - 1, y - 1)
				(`◤` == 'M' && `◢` == 'S' || `◤` == 'S' && `◢` == 'M') && (`◣` == 'M' && `◥` == 'S' || `◣` == 'S' && `◥` == 'M')
			}
		}
	}

}
