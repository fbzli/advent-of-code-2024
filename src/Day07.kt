import utils.day
import utils.mapToInts

fun main() = day(7) {

	part1 {
		readLines().sumOf { line ->
			val input = line.split(": ")
			val testValue = input[0].toLong()
			val numbers = input[1].split(" ").mapToInts()

			fun findResult(result: Long, intermediate: Long, values: List<Int>): Boolean {
				if (values.isEmpty()) return intermediate == result
				val next = values.first().toLong()
				val rest = values.drop(1)
				return findResult(result, intermediate + next, rest) ||
						findResult(result, intermediate * next, rest)
			}

			if (findResult(testValue, 0, numbers)) testValue else 0L
		}
	}

	part2 {
		readLines().sumOf { line ->
			val input = line.split(": ")
			val testValue = input[0].toLong()
			val numbers = input[1].split(" ").mapToInts()

			fun findResult(result: Long, intermediate: Long, values: List<Int>): Boolean {
				if (values.isEmpty()) return intermediate == result
				val next = values.first().toLong()
				val rest = values.drop(1)
				return findResult(result, intermediate + next, rest) ||
						findResult(result, intermediate * next, rest) ||
						findResult(result, "$intermediate$next".toLong(), rest)
			}

			if (findResult(testValue, 0, numbers)) testValue else 0L
		}
	}

}
