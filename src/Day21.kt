
import utils.*

fun main() = day(21) {

	part1 {
		readLines().sumOf { code ->
			var sequence = "A$code".windowed(2).joinToString("") { (buttonFrom, buttonTo) -> onNumpad(buttonFrom, buttonTo) }
			repeat(2) {
				sequence = "A$sequence".windowed(2).joinToString("") { (buttonFrom, buttonTo) -> onKeypad(buttonFrom, buttonTo) }
			}
			sequence.length * code.take(3).toInt()
		}
	}

	part2 {
		readLines().sumOf { code ->
			sequenceLength(code, 25) * code.take(3).toInt()
		}
	}

}

private val NUMPAD = listOf(
	listOf('7', '8', '9'),
	listOf('4', '5', '6'),
	listOf('1', '2', '3'),
	listOf(null, '0', 'A'),
).transpose()

private val DIRPAD = listOf(
	listOf(null, '^', 'A'),
	listOf('<', 'v', '>'),
).transpose()

private fun onNumpad(buttonFrom: Char, buttonTo: Char): String {
	val from = NUMPAD.indexOf(buttonFrom)!!
	val to = NUMPAD.indexOf(buttonTo)!!
	val delta = to - from
	return buildString {
		// go left first, then up/down, then right, unless we have to avoid the void
		if (from.y == 3 && to.x == 0) {
			append("^".repeat(-delta.y))
			append("<".repeat(-delta.x))
		} else if (from.x == 0 && to.y == 3) {
			append(">".repeat(delta.x))
			append("v".repeat(delta.y))
		} else {
			if (delta.x < 0) append("<".repeat(-delta.x))
			if (delta.y < 0) append("^".repeat(-delta.y))
			if (delta.y > 0) append("v".repeat(delta.y))
			if (delta.x > 0) append(">".repeat(delta.x))
		}
		append('A')
	}
}

private fun onKeypad(buttonFrom: Char, buttonTo: Char): String {
	val from = DIRPAD.indexOf(buttonFrom)!!
	val to = DIRPAD.indexOf(buttonTo)!!
	val delta = to - from
	return buildString {
		// go left first, then up/down, then right, unless we have to avoid the void
		if (from.y == 0 && to.x == 0) {
			append("v".repeat(delta.y))
			append("<".repeat(-delta.x))
		} else if (from.x == 0 && to.y == 0) {
			append(">".repeat(delta.x))
			append("^".repeat(-delta.y))
		} else {
			if (delta.x < 0) append("<".repeat(-delta.x))
			if (delta.y < 0) append("^".repeat(-delta.y))
			if (delta.y > 0) append("v".repeat(delta.y))
			if (delta.x > 0) append(">".repeat(delta.x))
		}
		append('A')
	}
}

private val cache = mutableMapOf<Pair<String, Int>, Long>()

private fun sequenceLength(sequence: String, level: Int, isNumpad: Boolean= true): Long {
	return cache.getOrPut(sequence to level) {
		"A$sequence".windowed(2).sumOf { (buttonFrom, buttonTo) ->
			val subsequence =
				if (isNumpad) onNumpad(buttonFrom, buttonTo)
				else onKeypad(buttonFrom, buttonTo)

			if (level == 0) {
				subsequence.length.toLong()
			} else {
				sequenceLength(subsequence, level - 1, false)
			}
		}
	}
}
