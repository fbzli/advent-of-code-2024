import utils.*

fun main() = day(4) {

	part1 {
		val input = readLines().unzipChars()
		input.sumOfIndexed { xy, _ ->
			EightDirections.count { direction ->
				"XMAS".mapIndexed { i, c -> i to c }.all { (i, char) ->
					input.getOrNull(xy + direction * i) == char
				}
			}
		}
	}

	@Suppress("NonAsciiCharacters", "LocalVariableName")
	part2 {
		val input = readLines().unzipChars()
		input.countIndexed { A, c ->
			c == 'A' && run {
				val `◣` = input.getOrNull(A.`◣`())
				val `◥` = input.getOrNull(A.`◥`())
				val `◢` = input.getOrNull(A.`◢`())
				val `◤` = input.getOrNull(A.`◤`())
				(`◤` == 'M' && `◢` == 'S' || `◤` == 'S' && `◢` == 'M') && (`◣` == 'M' && `◥` == 'S' || `◣` == 'S' && `◥` == 'M')
			}
		}
	}

}
