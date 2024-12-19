
import utils.day
import utils.mapToInts
import utils.skipLine

fun main() = day(17) {

	val ADV = 0
	val BXL = 1
	val BST = 2
	val JNZ = 3
	val BXC = 4
	val OUT = 5
	val BDV = 6
	val CDV = 7

	val A = 0
	val B = 1
	val C = 2

	part1 {
		val register = (1..3).map { readLine().substringAfter(": ").toInt() }.toMutableList()
		skipLine()
		val prog = readLine().substringAfter(": ").split(",").mapToInts()
		val output = mutableListOf<Int>()
		var instructionPointer = 0
		while (instructionPointer < prog.size) {
			val opcode = prog[instructionPointer]
			val operand = prog[instructionPointer + 1]
			instructionPointer += 2
			when (opcode) {
				ADV -> register[A] = register[A] shr operand.combo(register)
				BDV -> register[B] = register[A] shr operand.combo(register)
				CDV -> register[C] = register[A] shr operand.combo(register)
				BXL -> register[B] = register[B] xor operand
				BXC -> register[B] = register[B] xor register[C]
				BST -> register[B] = operand.combo(register) % 8
				JNZ -> if (register[A] != 0) instructionPointer = operand
				OUT -> output += operand.combo(register) % 8
			}
		}
		output.joinToString(",")
	}

	part2 {
		val prog = readLines().last().substringAfter(": ").split(",").mapToInts()

		fun reconstruct(a: Long, targetOutputs: List<Int>): Long? {
			val targetOutput = targetOutputs.lastOrNull() ?: return a
			for (bits in 0L..7L) {
				val aPrime = (a shl 3) or bits
				// hardcoded specific input program
				var b = aPrime % 8
				b = b xor 2
				var c = aPrime shr b.toInt()
				b = b xor c
				b = b xor 3
				if (b % 8 == targetOutput.toLong()) {
					reconstruct(aPrime, targetOutputs.dropLast(1))?.let { return it }
				}
			}
			return null
		}

		reconstruct(0L, prog)!!
	}

}

private fun Int.combo(registers: List<Int>): Int = when (this) {
	0, 1, 2, 3 -> this
	4, 5, 6 -> registers[this - 4]
	else -> error("Invalid combo operand: $this")
}
