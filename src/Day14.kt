
import utils.*
import java.awt.Point

fun main() = day(14) {

	part1 {
		val size = Point(101, 103)
		val secs = 100
		val bots = readLines()
			.map { it.split(",", " ", "=") }
			.map { Bot(Point(it[1].toInt(), it[2].toInt()), Point(it[4].toInt(), it[5].toInt())) }
			.map { it.pos = (it.pos + it.vel * secs).mod(size); it }
		val c = size / 2
		bots.count { it.pos.x < c.x && it.pos.y < c.y } *
				bots.count { it.pos.x > c.x && it.pos.y < c.y } *
				bots.count { it.pos.x < c.x && it.pos.y > c.y } *
				bots.count { it.pos.x > c.x && it.pos.y > c.y }
	}

	part2 {
		val size = Point(101, 103)
		val bots = readLines()
			.map { it.split(",", " ", "=") }
			.map { Bot(Point(it[1].toInt(), it[2].toInt()), Point(it[4].toInt(), it[5].toInt())) }
		val eon = 101 * 103 // upper limit estimate from manually observed cycles
		for (seconds in 1..eon) {
			printProgress(seconds, eon)
			bots.forEach { it.pos = (it.pos + it.vel).mod(size) }
			val room = xyMatrix(size) { x, y -> bots.any { it.pos.x == x && it.pos.y == y } }
			// heuristic: find 3x3 cluster of bots, apparently the tree is filled, not outlined
			room.forEachIndexed { xy: Point, hasBot: Boolean ->
				val isCluster = hasBot && xy.eightNeighbors().all { room.getOrNull(it) == true }
				if (isCluster) {
					room.zip { println(it.joinToString("") { if (it) "#" else " " }) }
					return@part2 seconds
				}
			}
		}
		-1
	}

}

private data class Bot(var pos: Point, val vel: Point)
