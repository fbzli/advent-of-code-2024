import utils.*
import java.awt.Point

fun main() = day(20) {

	part1 {
		val map = readLines().unzipChars()
		val timedMap = map.mapValues { if (it == '#') null else Int.MAX_VALUE }.mutable()
		val path = mutableListOf<Point>(map.indexOf('S')!!)
		var step = 0
		while (true) {
			val pos = path.last()
			timedMap[pos] = step++
			val next = pos.fourNeighbors().find { timedMap.getOrNull(it) == Int.MAX_VALUE }
			if (next != null) {
				path.add(next)
			} else {
				break
			}
		}

		path.sumOf { xy ->
			val currentTime = timedMap[xy]!!
			xy.run { listOf(`▲`(2), `▶`(2), `▼`(2), `◀`(2)) }
				.count { target -> (timedMap.getOrNull(target) ?: -1) > currentTime + 100 }
		}
	}

	part2 {
		val map = readLines().unzipChars()
		val path = mutableListOf<Point>()
		var next = map.indexOf('S')
		var prev: Point? = null
		while (next != null) {
			path.add(next)
			next = next.fourNeighbors().find { it != prev && map.getOrNull(it) != '#' }.also {
				prev = next
			}
		}

		path.dropLast(100).sumOfIndexedA { i, cheatStart ->
			path.subList(i + 100)
				.associateWith { cheatTarget -> cheatStart.manhattanTo(cheatTarget) }
				.filter { (_, manhattan) -> manhattan <= 20 }
				.count { (cheatTarget, manhattan) -> path.indexOf(cheatTarget) - i - manhattan >= 100 }
		}
	}

}
