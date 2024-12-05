import utils.*

fun main() = day(5) {

	part1 {
		val (orderLines, bookLines) = lineSequence().split { it.isBlank() }
		val order = orderLines.map { it.split("|").mapToInts() }.groupBy({ it[0] }, { it[1] })
		val books = bookLines.map { it.split(",").mapToInts() }
		fun Int.mustBeBefore(other: Int): Boolean = order[this]?.contains(other) ?: false

		books.sumOf { pages ->
			val ordered = pages.windowed(2) { (a, b) -> a.mustBeBefore(b) }.all { it }
			if (ordered) pages.middle() else 0
		}
	}

	part2 {
		val (orderLines, bookLines) = lineSequence().split { it.isBlank() }
		val order = orderLines.map { it.split("|").mapToInts() }.groupBy({ it[0] }, { it[1] })
		val books = bookLines.map { it.split(",").mapToInts() }
		fun Int.mustBeBefore(other: Int): Boolean = order[this]?.contains(other) ?: false
		val comparator = Comparator<Int> { l, r -> r.mustBeBefore(l) - l.mustBeBefore(r) }

		books.sumOf { pages ->
			val ordered = pages.windowed(2) { (a, b) -> a.mustBeBefore(b) }.all { it }
			if (ordered) 0 else pages.sortedWith(comparator).middle()
		}
	}

}
