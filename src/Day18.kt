import utils.*
import java.awt.Point
import java.util.*

fun main() = day(18) {

	part1 {
		val grid = mutableXyMatrix<Int?>(71, 71) { x, y -> Int.MAX_VALUE }
		readLines().take(1024).forEach { byte ->
			val (x, y) = byte.split(",").mapToInts()
			grid[x][y] = null
		}
		grid.score()!!
	}

	part2 {
		val lines = readLines()
		lines.forEachIndexed { i, block ->
			val grid = mutableXyMatrix<Int?>(71, 71) { x, y -> Int.MAX_VALUE }
			lines.take(i + 1).forEach { byte ->
				val (x, y) = byte.split(",").mapToInts()
				grid[x][y] = null
			}
			if (grid.score() == null) {
				return@part2 block
			}
		}
		error("No blocking byte found")
	}

}

private fun List<MutableList<Int?>>.score(): Int? {
	val queue = PriorityQueue<Pair<Int, Point>>(compareBy { it.first })
	queue.add(Pair(0, Point(0, 0)))
	while (queue.isNotEmpty()) {
		val (score, pos) = queue.poll()
		val nScore = score + 1
		pos.fourNeighbors().forEach { xy ->
			if ((getOrNull(xy) ?: -1) > nScore) {
				set(xy, nScore)
				queue.add(Pair(nScore, xy))
			}
		}
	}
	return this[width - 1, height - 1].takeIf { it != Int.MAX_VALUE }
}
