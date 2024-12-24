
import utils.day
import utils.split
import utils.swap
import utils.toLong
import kotlin.random.Random

fun main() = day(24) {

	part1 {
		val input = lineSequence().split { it.isBlank() }
		val wires = input[0].associate { it.split(": ").let { it[0] to (it[1].toInt() != 0) } }
		val gates = input[1].map { it.split(" ") }.associate { it[4] to Gate(it[0], it[1], it[2]) }

		gates.keys.filter { it[0] == 'z' }
			.sorted()
			.sumOf { outputWire -> gates.getValue(outputWire).evaluate(wires, gates).toLong() shl outputWire.drop(1).toInt() }
	}

	part2 {
		val input = lineSequence().split { it.isBlank() }
		val wires = input[0].associate { it.split(": ").let { it[0] to (it[1].toInt() != 0) } }
		val gates = input[1].map { it.split(" ") }.associate { it[4] to Gate(it[0], it[1], it[2]) }.toMutableMap()

		// output Graphviz code
		// https://dreampuf.github.io/GraphvizOnline/
		println("digraph G {")
		val xs = wires.filter { (wire, _) -> wire[0] == 'x' }.keys.sorted().joinToString(" -> ")
		println("  subgraph input_x {\n" +
				"    node [style=filled,color=lightgrey];\n" +
				"    $xs;\n" +
				"  }")
		val ys = wires.filter { (wire, _) -> wire[0] == 'y' }.keys.sorted().joinToString(" -> ")
		println("  subgraph input_y {\n" +
				"    node [style=filled,color=lightgrey];\n" +
				"    $ys;\n" +
				"  }")
		val gatesAnd = gates.filter { (_, gate) -> gate is Gate.And }.keys.sorted().joinToString("; ")
		println("  subgraph gates_and {\n" +
				"    node [style=filled,color=lightgreen];\n" +
				"    $gatesAnd;\n" +
				"  }")
		val gatesOr = gates.filter { (_, gate) -> gate is Gate.Or }.keys.sorted().joinToString("; ")
		println("  subgraph gates_or {\n" +
				"    node [style=filled,color=yellow];\n" +
				"    $gatesOr;\n" +
				"  }")
		val gatesXor = gates.filter { (_, gate) -> gate is Gate.Xor }.keys.sorted().joinToString("; ")
		println("  subgraph gates_xor {\n" +
				"    node [style=filled,color=lightskyblue];\n" +
				"    $gatesXor;\n" +
				"  }")
		val zs = gates.filter { (output, _) -> output[0] == 'z' }.keys.sorted().joinToString(" -> ")
		println("  subgraph output_z {\n" +
				"    $zs;\n" +
				"  }")
		gates.forEach { output, gate ->
			println("  " + gate.a + " -> " + output + "; " + gate.b + " -> " + output + ";")
		}
		println("}")

		// empirically determined swaps, used the visualisation above
		val swaps = mapOf(
			"qff" to "qnw",
			"z16" to "pbv",
			"z23" to "qqp",
			"z36" to "fbq",
		)
		swaps.forEach { a, b -> gates.swap(a, b) }

		// run some tests
		repeat(32) {
			val testX = Random.nextLong() and ((1L shl 45) - 1)
			val testY = Random.nextLong() and ((1L shl 45) - 1)
			val testZ = testX + testY
			val testWires = wires.mapValues { (wire, _) ->
				val number = if (wire[0] == 'x') testX else testY
				((number shr wire.drop(1).toInt()) and 1) != 0L
			}
			val simulatedZ = gates.keys.filter { it[0] == 'z' }
				.sorted()
				.sumOf { outputWire ->
					gates.getValue(outputWire).evaluate(testWires, gates).toLong() shl outputWire.drop(1).toInt()
				}
			require(simulatedZ == testZ) {
				println("testX =      ${testX.toString(2).padStart(47)}")
				println("testY =      ${testY.toString(2).padStart(47)}")
				println("testZ =      ${testZ.toString(2).padStart(47)}")
				println("simulatedZ = ${simulatedZ.toString(2).padStart(47)}")
			}
		}

		// final answer
		swaps.flatMap { listOf(it.key, it.value) }.sorted().joinToString(",")
	}

}

private sealed class Gate(open val a: String, open val b: String) {
	companion object {
		operator fun invoke(a: String, op: String, b: String): Gate {
			return when (op) {
				"AND" -> And(a, b)
				"OR" -> Or(a, b)
				"XOR" -> Xor(a, b)
				else -> error("Unknown gate: $op")
			}
		}
	}

	data class And(override val a: String, override val b: String) : Gate(a, b) {
		override fun compute(a: Boolean, b: Boolean) = a && b
	}

	data class Or(override val a: String, override val b: String) : Gate(a, b) {
		override fun compute(a: Boolean, b: Boolean) = a || b
	}

	data class Xor(override val a: String, override val b: String) : Gate(a, b) {
		override fun compute(a: Boolean, b: Boolean) = a xor b
	}

	abstract fun compute(a: Boolean, b: Boolean): Boolean

	//private var output: Boolean? = null
	fun evaluate(wires: Map<String, Boolean>, gates: Map<String, Gate>): Boolean {
		return /*output ?:*/ run {
			val a = wires[a] ?: gates.getValue(a).evaluate(wires, gates)
			val b = wires[b] ?: gates.getValue(b).evaluate(wires, gates)
			compute(a, b)//.also { output = it }
		}
	}
}
