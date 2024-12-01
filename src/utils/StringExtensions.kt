@file:Suppress("NOTHING_TO_INLINE")

package utils

/**
 * Split a String into 2 parts using a separator, returning a Pair.
 */
inline fun String.splitToPair(separator: String, ignoreCase: Boolean = false): Pair<String, String> {
	return split(separator, ignoreCase = ignoreCase, limit = 2).let { it[0] to it[1] }
}

/**
 * Replaces all occurrences of the keys in the map with the corresponding values.
 * The replacement is done in the order of the first occurrence of the keys.
 * @param map A map of strings to replace.
 * @param ignoreCase Whether to ignore the case when searching for the keys.
 * @return A new string with the replacements.
 */
fun String.replaceMap(map: Map<String, String>, ignoreCase: Boolean = false): String {
	val result = StringBuilder(length)
	var pos = 0
	do {
		val match = map.mapNotNull { (k, v) ->
			val i = indexOf(k, pos, ignoreCase)
			if (i >= 0) Triple(i, k, v) else null
		}.minByOrNull { it.first }

		if (match != null) {
			result.append(this, pos, match.first).append(match.third)
			pos = match.first + match.second.length.coerceAtLeast(1)
		}
	} while (match != null)

	return result.append(this, pos, length).toString()
}
