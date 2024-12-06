package utils

/**
 * Repeat the specified [action] indefinitely.
 */
inline fun repeat(action: () -> Unit): Nothing {
	while (true) {
		action()
	}
}
