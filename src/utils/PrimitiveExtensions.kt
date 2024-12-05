@file:Suppress("NOTHING_TO_INLINE")

package utils

inline fun Boolean.toInt(): Int = if (this) 1 else 0
