import utils.LongPoint
import utils.day

fun main() = day(13) {

	part1 {
		readLines().chunked(4).sumOf { (a, b, p) ->
			val A = a.split(",", "+").let { LongPoint(it[1].toLong(), it[3].toLong()) }
			val B = b.split(",", "+").let { LongPoint(it[1].toLong(), it[3].toLong()) }
			val P = p.split(",", "=").let { LongPoint(it[1].toLong(), it[3].toLong()) }
			solveLinearSystemInℕ(A, B, P)?.let { (a, b) -> a * 3L + b } ?: 0L
		}
	}

	part2 {
		readLines().chunked(4).sumOf { (a, b, p) ->
			val A = a.split(",", "+").let { LongPoint(it[1].toLong(), it[3].toLong()) }
			val B = b.split(",", "+").let { LongPoint(it[1].toLong(), it[3].toLong()) }
			val P = p.split(",", "=").let { LongPoint(it[1].toLong() + 10000000000000L, it[3].toLong() + 10000000000000L) }
			solveLinearSystemInℕ(A, B, P)?.let { (a, b) -> a * 3L + b } ?: 0L
		}
	}

}

/**
 * Solve the linear system of equations. a*A + b*B = R
 * @return the solution (a, b) in ℕ if it exists, null otherwise.
 */
private fun solveLinearSystemInℕ(A: LongPoint, B: LongPoint, R: LongPoint): LongPoint? {
	val det = A.x * B.y - B.x * A.y
	if (det == 0L) return null
	val a = R.x * B.y - B.x * R.y
	val b = A.x * R.y - R.x * A.y
	if (a % det != 0L || b % det != 0L) return null
	return LongPoint(a / det, b / det)
}

