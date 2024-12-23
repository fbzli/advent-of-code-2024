import utils.day

fun main() = day(23) {

	part1 {
		val network = readLines().map { it.split("-") }
			.flatMap { (a, b) -> listOf(a to b, b to a) }
			.groupBy({ (a, _) -> a }, { (_, b) -> b })
			.mapValues { (_, v) -> v.toSet() }

		buildSet {
			network.keys.forEach { a ->
				network.getValue(a).forEach { b ->
					network.getValue(b).forEach { c ->
						if (a in network.getValue(c)) {
							listOf(a, b, c).takeIf { it.any { it[0] == 't' } }?.sorted()?.also(::add)
						}
					}
				}
			}
		}.count()
	}

	part2 {
		val network = readLines().map { it.split("-") }
			.flatMap { (a, b) -> listOf(a to b, b to a) }
			.groupBy({ (a, _) -> a }, { (_, b) -> b })
			.mapValues { (_, v) -> v.toSet() }
		network.findLargestClique().sorted().joinToString(",")
	}

}

/**
 * Bron-Kerbosch algorithm to find the largest clique in a graph.
 */
private fun <T> Map<T, Set<T>>.findLargestClique(
	r: Set<T> = emptySet(),
	p: Set<T> = keys,
	x: Set<T> = emptySet(),
	largestClique: MutableSet<T> = mutableSetOf(),
): Set<T> {
	if (p.isEmpty() && x.isEmpty()) {
		if (r.size > largestClique.size) {
			largestClique.clear()
			largestClique.addAll(r)
		}
		return largestClique
	}
	val pM = p.toMutableSet()
	val xM = x.toMutableSet()
	for (v in p) {
		val neighbors = getValue(v)
		findLargestClique(
			r + v,
			pM intersect neighbors,
			xM intersect neighbors,
			largestClique,
		)
		pM -= v
		xM += v
	}
	return largestClique
}
