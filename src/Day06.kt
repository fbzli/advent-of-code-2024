import utils.*

fun main() = day(6) {

	part1 {
		val grid = readLines().unzipChars().mutable()
		var position = requireNotNull(grid.indexOf('^'))
		exit@ while (true) {
			var direction = grid[position]
			grid[position] = 'X'
			for (d in 1..4) {
				val nextPosition = position.moveByAsciiDirection(direction)
				when (grid.getOrNull(nextPosition)) {
					null -> break@exit
					'#' -> direction = direction.asciiDirectionRotateRight()
					else -> {
						grid[nextPosition] = direction
						position = nextPosition
						break
					}
				}
			}
		}
		grid.countIndexed { _, value -> value == 'X' }
	}

	part2 {
		val _grid = readLines().unzipChars()
		val start = requireNotNull(_grid.indexOf('^'))
		_grid.countIndexed { obstaclePoint, _ ->
			printProgress(obstaclePoint.x * _grid.width + obstaclePoint.y, _grid.width * _grid.height)
			if (obstaclePoint == start) return@countIndexed false
			val grid = _grid.mutable().also { it[obstaclePoint] = '#' }
			var position = start
			val visited = xyMatrix(grid.width, grid.height) { _, _ -> mutableSetOf<Char>() }
			repeat {
				if (!visited[position].add(grid[position])) return@countIndexed true
				var direction = grid[position]
				for (d in 1..4) {
					val nextPosition = position.moveByAsciiDirection(direction)
					when (grid.getOrNull(nextPosition)) {
						null -> return@countIndexed false
						'#' -> direction = direction.asciiDirectionRotateRight()
						else -> {
							grid[nextPosition] = direction
							position = nextPosition
							break
						}
					}
				}
			}

		}
	}

}
