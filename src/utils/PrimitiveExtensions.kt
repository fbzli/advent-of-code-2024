@file:Suppress("NOTHING_TO_INLINE")

package utils

inline fun Boolean.toInt(): Int = if (this) 1 else 0

inline operator fun Boolean.unaryPlus(): Int = toInt()
inline operator fun Boolean.unaryMinus(): Int = -toInt()
inline operator fun Boolean.plus(other: Boolean): Int = toInt() + other.toInt()
inline operator fun Boolean.plus(other: Int): Int = toInt() + other
inline operator fun Boolean.minus(other: Boolean): Int = toInt() - other.toInt()

inline operator fun Int.plus(other: Boolean): Int = this + other.toInt()
inline operator fun Int.minus(other: Boolean): Int = this - other.toInt()
inline operator fun Int.times(other: Boolean): Int = if (other) this else 0

inline operator fun Long.plus(other: Boolean): Long = this + other.toInt()
inline operator fun Long.minus(other: Boolean): Long = this - other.toInt()
inline operator fun Long.times(other: Boolean): Long = if (other) this else 0
