package utils

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private var prevProgressPrintTime: Long = 0
private val mutex = Mutex()
private val progressBuffer = FixedSizeBuffer<Float>(5)

fun printProgress(current: Number, total: Number? = null) {
	val now = System.currentTimeMillis()
	if (now - prevProgressPrintTime < 1000) return
	runBlocking {
		mutex.withLock {
			prevProgressPrintTime = now
			if (total != null) {
				val progress = current.toFloat() / total.toFloat() * 100
				progressBuffer.add(progress)
				val progressString = "%.${progressBuffer.getDistinguishingDecimalCount()}f".format(progress)
				println("$progressString%")
			} else {
				println(current)
			}
		}
	}
}

private fun FixedSizeBuffer<Float>.getDistinguishingDecimalCount(): Int {
	return getBuffer()
		.windowed(2) { (a, b) ->
			(b - a).div(10).toString().split('.').getOrNull(1)?.takeWhile { it == '0' }?.length ?: 0
		}
		.maxOrNull() ?: 0
}
