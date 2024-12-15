import utils.*
import java.awt.Point

// !!! requires Kotlin 2.1 with when-quards and K2 mode enabled

fun main() = day(15) {

	part1 {
		val input = lineSequence().split { it.isBlank() }
		val room = input[0].unzipChars().mutable()
		val moves = input[1].joinToString("")
		var bot = room.indexOf('@')!!
		moves.forEach { move ->
			if (room.canMove(bot, move)) {
				room.doPush(bot, move, '@')
				room[bot] = '.'
				bot = bot.moveByAsciiDirection(move)
			}
		}
		room.sumOfIndexed { xy, value ->
			if (value == 'O') xy.x + xy.y * 100 else 0
		}
	}

	part2 {
		val input = lineSequence().split { it.isBlank() }
		val room = input[0].map {
			it.flatMap { c ->
				when (c) {
					'@' -> listOf(c, '.')
					'O' -> listOf('[', ']')
					else -> listOf(c, c)
				}
			}.joinToString("")
		}.unzipChars().mutable()
		val moves = input[1].joinToString("")
		var bot = room.indexOf('@')!!
		moves.forEach { move ->
			if (room.canMove(bot, move)) {
				room.doPush(bot, move, '@')
				room[bot] = '.'
				bot = bot.moveByAsciiDirection(move)
			}
		}
		room.sumOfIndexed { xy, value ->
			if (value == '[') xy.x + xy.y * 100 else 0
		}
	}

}

private fun List<MutableList<Char>>.canMove(pos: Point, dir: Char): Boolean {
	val target = pos.moveByAsciiDirection(dir)
	val movingVertically = dir == '^' || dir == 'v'
	return when (this[target]) {
		'.' -> true
		'#' -> false
		'[' if (movingVertically) -> canMove(target, dir) && canMove(target.`▶`(), dir)
		']' if (movingVertically) -> canMove(target.`◀`(), dir) && canMove(target, dir)
		else -> canMove(target, dir)
	}
}

private fun List<MutableList<Char>>.doPush(pos: Point, dir: Char, pushed: Char) {
	val target = pos.moveByAsciiDirection(dir)
	val movesVertically = dir == '^' || dir == 'v'
	when (val objAtTarget = this[target]) {
		'.' -> this[target] = pushed
		'#' -> error("Cannot move to $target")
		'[' if (movesVertically) -> {
			doPush(target, dir, objAtTarget)
			this[target] = pushed
			val right = target.`▶`()
			doPush(right, dir, this[right])
			this[right] = '.'
		}
		']' if (movesVertically) -> {
			val left = target.`◀`()
			doPush(left, dir, this[left])
			this[left] = '.'
			doPush(target, dir, objAtTarget)
			this[target] = pushed
		}
		else -> {
			doPush(target, dir, objAtTarget)
			this[target] = pushed
		}
	}
}

private fun Point.moveByAsciiDirection(direction: Char): Point {
	return when (direction) {
		'^' -> `▲`()
		'>' -> `▶`()
		'v' -> `▼`()
		'<' -> `◀`()
		else -> error("Invalid direction '$direction' at $this")
	}
}
