package utils

import java.io.BufferedReader
import java.io.File

inline fun day(day: Int, block: Day.() -> Unit) {
	Exception().stackTrace[1].run {
		val className = className.substringAfterLast('.')
		val dayId = className.substringAfter("Day").substringBefore("Kt")
		require(dayId.toInt() == day) { "Day '$day' does not match file name '$className'" }
	}
	val dayId = "%02d".format(day)
	println("DAY $dayId")
	println()
	Day(dayId).block()
}

class Day(dayId: String) {
	val file = File("src/Day$dayId.txt")

	inline fun part1(example: String? = null, part1: BufferedReader.() -> Any) {
		exec("PART 1", example, part1)
	}

	inline fun part2(example: String? = null, part2: BufferedReader.() -> Any) {
		exec("PART 2", example, part2)
	}

	inline fun exec(label: String, example: String?, block: BufferedReader.() -> Any) {
		val input = example?.reader()?.buffered() ?: file.inputStream().bufferedReader()
		val result = input.use { block(it) }
		if (result == Unit) return
		println("$label: $result")
		println()
	}
}
