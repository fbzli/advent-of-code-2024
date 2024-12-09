import utils.day
import utils.indexOfSubList

fun main() = day(9) {

	val freeSpace = List(10) { i -> List(i) { null } }

	part1 {
		val disk = readLine().map { it.digitToInt() }
			.flatMapIndexed { index: Int, length: Int ->
				if (index % 2 == 0) List(length) { index / 2L }
				else freeSpace[length]
			}.toMutableList()
		var l = 0
		var r = disk.lastIndex
		while (l < r) {
			if (disk[l] == null) {
				while (disk[r] == null) r--
				disk[l] = disk[r]
				disk[r] = null
				r--
			}
			l++
		}
		disk.mapIndexedNotNull { index, value -> value?.times(index) }.sum()
	}

	part2 {
		val disk = readLine().map { it.digitToInt() }
			.flatMapIndexed { index: Int, length: Int ->
				if (index % 2 == 0) List(length) { index / 2L }
				else freeSpace[length]
			}.toMutableList()
		val freeSpaceLeftLimit = MutableList(10) { 0 } // perf optimization, first possible index on the disk to find space of a given size
		var fileEnd = disk.lastIndex
		while (fileEnd > 0) {
			while (disk[fileEnd] == null) fileEnd--
			var fileStart = fileEnd
			while (fileStart > 0 && disk[fileStart - 1] == disk[fileEnd]) fileStart--
			val fileLength = fileEnd - fileStart + 1
			val spaceSearchStart = freeSpaceLeftLimit[fileLength]
			if (spaceSearchStart < fileStart) {
				val spaceStart = disk.indexOfSubList(freeSpace[fileLength], fromIndex = spaceSearchStart)
				if (spaceStart != -1 && spaceStart < fileStart) {
					disk.subList(spaceStart, spaceStart + fileLength).fill(disk[fileStart])
					disk.subList(fileStart, fileEnd + 1).fill(null)
					for (l in fileLength..9) {
						freeSpaceLeftLimit[l] = freeSpaceLeftLimit[l].coerceAtLeast(spaceStart + fileLength)
					}
				}
			}
			fileEnd = fileStart - 1
		}
		disk.mapIndexedNotNull { index, value -> value?.times(index) }.sum()
	}

}
