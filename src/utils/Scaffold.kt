package utils

import java.io.BufferedReader
import java.io.File

fun day(day: Int, block: Day.() -> Unit) {
	val dayId = "%02d".format(day)
	println("DAY $dayId")
	println()
	Day(dayId).block()
}

class Day(dayId: String) {
	private val file = File("src/Day$dayId.txt")

	fun part1(example: String? = null, part1: BufferedReader.() -> Any) {
		val input = example?.reader()?.buffered() ?: file.inputStream().bufferedReader()
		input.use {
			val result = part1(it)
			println("PART 1: $result")
			println()
		}
	}

	fun part2(example: String? = null, part2: BufferedReader.() -> Any) {
		val input = example?.reader()?.buffered() ?: file.inputStream().bufferedReader()
		input.use {
			val result = part2(it)
			println("PART 2: $result")
			println()
		}
	}
}
