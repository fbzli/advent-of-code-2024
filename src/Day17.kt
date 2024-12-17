
import utils.day
import utils.mapToInts
import utils.printProgress
import utils.repeat

fun main() = day(17) {

	val ADV = 0
	val BXL = 1
	val BST = 2
	val JNZ = 3
	val BXC = 4
	val OUT = 5
	val BDV = 6
	val CDV = 7

	part1 {
		val register = (1..3).map { readLine().substringAfter(": ").toInt() }.toMutableList()
		readLine()
		val prog = readLine().substringAfter(": ").split(",").mapToInts()
		val output = mutableListOf<Int>()
		var instructionPointer = 0
		while (instructionPointer < prog.size) {
			val opcode = prog[instructionPointer]
			val operand = prog[instructionPointer + 1]
			instructionPointer += 2
			when (opcode) {
				ADV -> register.A = register.A shr operand.combo(register)
				BDV -> register.B = register.A shr operand.combo(register)
				CDV -> register.C = register.A shr operand.combo(register)
				BXL -> register.B = register.B xor operand
				BXC -> register.B = register.B xor register.C
				BST -> register.B = operand.combo(register) % 8
				JNZ -> if (register.A != 0) instructionPointer = operand
				OUT -> output += operand.combo(register) % 8
			}
		}
		output.joinToString(",")
	}

	part2("Register A: 2024\n" +
			"Register B: 0\n" +
			"Register C: 0\n" +
			"\n" +
			"Program: 0,3,5,4,3,0\n") {
		val register = (1..3).map { readLine().substringAfter(": ").toLong() }.toMutableList()
		readLine()
		val prog = readLine().substringAfter(": ").split(",").mapToInts()
		repeat { a ->
			register.A = a
			val output = mutableListOf<Int>()
			var instructionPointer = 0
			while (instructionPointer < prog.size) {
				val opcode = prog[instructionPointer]
				val operand = prog[instructionPointer + 1].toLong()
				instructionPointer += 2
				when (opcode) {
					ADV -> register.A = register.A shr operand.combo(register).toInt()
					BDV -> register.B = register.A shr operand.combo(register).toInt()
					CDV -> register.C = register.A shr operand.combo(register).toInt()
					BXL -> register.B = register.B xor operand
					BXC -> register.B = register.B xor register.C
					BST -> register.B = operand.combo(register) % 8
					JNZ -> if (register.A != 0L) instructionPointer = operand.toInt()
					OUT -> {
						val value = operand.combo(register).toInt() % 8
						if (prog.getOrNull(output.size) == value) {
							output += value
						} else {
							return@repeat
						}
					}
				}
			}
			if (prog == output)	return@part2 a
		}
	}

}

private fun Int.combo(registers: List<Int>): Int = when (this) {
	0, 1, 2, 3 -> this
	4, 5, 6 -> registers[this - 4]
	else -> error("Invalid combo operand: $this")
}

private fun Long.combo(registers: List<Long>): Long = when (this) {
	0L, 1L, 2L, 3L -> this.toLong()
	4L, 5L, 6L -> registers[this.toInt() - 4]
	else -> error("Invalid combo operand: $this")
}

private var MutableList<Int>.A
	get() = this[0]
	set(value) {
		this[0] = value
	}
private var MutableList<Int>.B
	get() = this[1]
	set(value) {
		this[1] = value
	}
private var MutableList<Int>.C
	get() = this[2]
	set(value) {
		this[2] = value
	}

private var MutableList<Long>.A
	get() = this[0]
	set(value) {
		this[0] = value
	}
private var MutableList<Long>.B
	get() = this[1]
	set(value) {
		this[1] = value
	}
private var MutableList<Long>.C
	get() = this[2]
	set(value) {
		this[2] = value
	}
