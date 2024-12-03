sealed interface Instruction {
    data class Multiply(val op1: Int, val op2: Int) : Instruction
    data object Do : Instruction
    data object DoNot : Instruction
}

fun main() {
    fun parseInput(input: List<String>): List<Instruction> {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()
        return input.flatMap { line ->
            val matches = regex.findAll(line).toList()
            matches.map {
                when {
                    it.value.startsWith("mul") -> Instruction.Multiply(
                        it.groupValues[1].toInt(),
                        it.groupValues[2].toInt()
                    )

                    it.value.startsWith("don't") -> Instruction.DoNot
                    it.value.startsWith("do") -> Instruction.Do
                    else -> throw IllegalStateException()
                }
            }
        }
    }


    // Part 1
    fun executeProgramV1(program: List<Instruction>): Int {
        return program.sumOf {
            if (it is Instruction.Multiply) it.op1 * it.op2 else 0
        }
    }

    // Part 2
    fun executeProgramV2(program: List<Instruction>): Int {
        var factor = 1
        return program.sumOf {
            factor = when (it) {
                is Instruction.Multiply -> return@sumOf factor * it.op1 * it.op2
                is Instruction.Do -> 1
                is Instruction.DoNot -> 0
            }
            0
        }
    }

    val testInput = readInput("Day03")
    val program = parseInput(testInput)
    executeProgramV1(program).println()
    executeProgramV2(program).println()
}
