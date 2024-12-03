import utils.day

fun main() = day(3) {

	val regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()

	part1 {
		regex.findAll(readText()).sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
	}

	part2 {
		var input = readText()
		var sum = 0
		do {
			val enabled = input.substringBefore("don't()")
			sum += regex.findAll(enabled).sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
			input = input.substringAfter("don't()", "").substringAfter("do()", "")
		} while (input.isNotEmpty())
		sum
	}

}
