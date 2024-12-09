import utils.day

fun main() = day(9) {

	part1 {
		val disk = readLine().map { it.digitToInt() }
			.flatMapIndexed { index: Int, value: Int ->
				if (index % 2 == 0) List(value) { index / 2 }
				else List(value) { '.' }
			}.toMutableList()
		var i = 0
		var j = disk.lastIndex
		while (i < j) {
			if (disk[i] == '.') {
				while (disk[j] == '.') j--
				disk[i] = disk[j]
				disk[j] = '.'
				j--
			}
			i++
		}
		disk.mapIndexed { index, value -> index * if (value == '.') 0L else (value as Int).toLong() }.sum()
	}

	part2 {
		val disk = readLine().map { it.digitToInt() }
			.flatMapIndexed { index: Int, value: Int ->
				if (index % 2 == 0) List(value) { index / 2 }
				else List(value) { '.' }
			}.toMutableList()
		var j = disk.lastIndex
		while (j > 0) {
			while (disk[j] == '.') j--
			var fileLength = 1
			var k = j -1
			while (k > 0 && disk[k] == disk[j]) {
				fileLength++
				k--
			}
			val place = disk.map { if (it == '.') '.' else '-' }.joinToString("").indexOf(".".repeat(fileLength))
			if (place != -1 && place < k) {
				disk.subList(place, place + fileLength).fill(disk[j])
				for (x in (k+1)..j) {
					disk[x] = '.'
				}
			}
			j -= fileLength
		}
		disk.mapIndexed { index, value -> index * if (value == '.') 0L else (value as Int).toLong() }.sum()
	}

}
