import utils.*
import java.awt.Point
import java.util.*

fun main() = day(16) {

	part1 {
		val maze = readLines().unzipChars()
		val start = maze.indexOf('S')!!
		val end = maze.indexOf('E')!!
		val minScoreMaze = maze.computeTileScores(start)
		minScoreMaze[end].values.min()
	}

	part2 {
		val maze = readLines().unzipChars().mutable()
		val start = maze.indexOf('S')!!
		val end = maze.indexOf('E')!!
		val minScoreMaze = maze.computeTileScores(start)

		// backtrack
		val queue: Queue<Pair<Point, Char>> = LinkedList()
		val bestDirAtEnd = minScoreMaze[end].minBy { (_, v) -> v }.key
		queue.add(end to bestDirAtEnd)
		val best = mutableSetOf<Point>()
		while (queue.isNotEmpty()) {
			val (pos, dir) = queue.poll()
			best.add(pos)
			val score = minScoreMaze[pos][dir]!!
			AsciiDirections.forEach { prevDir ->
				val prevPos = pos - prevDir.asciiAsVector()
				if (prevDir == dir && minScoreMaze[prevPos].getOrDefault(prevDir, Int.MAX_VALUE) == score - 1 ||
					prevDir != dir && minScoreMaze[prevPos].getOrDefault(prevDir, Int.MAX_VALUE) == score - 1001
				) {
					queue.add(prevPos to prevDir)
				}
			}
		}
		best.count()
	}

}

private fun List<List<Char>>.computeTileScores(start: Point): List<List<MutableMap<Char, Int>>> {
	val minScoreMaze = xyMatrix(width, height) { x, y -> mutableMapOf<Char, Int>() }
	val queue = PriorityQueue<Triple<Int, Point, Char>>(compareBy { it.first })
	queue.add(Triple(0, start, '>'))
	while (queue.isNotEmpty()) {
		val (score, pos, dir) = queue.poll()
		if (score < (minScoreMaze[pos][dir] ?: Int.MAX_VALUE)) {
			minScoreMaze[pos][dir] = score
		} else {
			continue
		}
		AsciiDirections.forEach { nextDir ->
			if (nextDir == dir) {
				val newPos = pos.moveByAsciiDirection(nextDir)
				if (get(newPos) == '#') return@forEach
				queue.add(Triple(score + 1, newPos, nextDir))
			} else {
				queue.add(Triple(score + 1000, pos, nextDir))
			}
		}
	}
	return minScoreMaze
}
