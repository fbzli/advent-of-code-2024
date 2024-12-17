package utils

/**
 * Repeat the specified [action] indefinitely.
 */
inline fun repeat(action: (Long) -> Unit): Nothing {
	var i = 0L
	while (true) {
		action(i++)
	}
}
