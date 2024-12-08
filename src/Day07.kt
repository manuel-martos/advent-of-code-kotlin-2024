import kotlin.math.pow

data class Equation(
    val testValue: Long,
    val operands: List<Long>,
)

fun main() {
    fun parseInput(input: List<String>): List<Equation> {
        val regex = """^(\d+):((?: \d+)+)$""".toRegex()
        val equations = input
            .map { regex.matchEntire(it)!! }
            .map { Equation(
                testValue = it.groupValues[1].toLong(),
                operands = it.groupValues[2].trim().split(" ").map { it.toLong() },
            ) }
        return equations
    }

    // Part 1
    fun Equation.hasSolutionV1(): Boolean {
        val operatorsCount = operands.size - 1
        return (0..2.0.pow(operatorsCount.toDouble()).toLong()).map {
            it
                .toString(2)
                .padStart(operatorsCount, '0')
                .map { curChar ->
                    when (curChar) {
                        '0' -> '+'
                        else -> '*'
                    }
                }
        }.any { operators ->
            val result = operands.reduceIndexed { index, val1, val2 ->
                if (operators[index - 1] == '+')
                    val1 + val2
                else
                    val1 * val2
            }
            result == testValue
        }
    }

    fun solvePart1(equations: List<Equation>): Long =
        equations.filter { it.hasSolutionV1() }.sumOf { it.testValue }

    // Part 2
    fun Equation.hasSolutionV2(): Boolean {
        val operatorsCount = operands.size - 1
        return (0..3.0.pow(operatorsCount.toDouble()).toLong()).map {
            it
                .toString(3)
                .padStart(operatorsCount, '0')
                .map { curChar ->
                    when (curChar) {
                        '0' -> '+'
                        '1' -> '*'
                        else -> '|'
                    }
                }
        }.any { operators ->
            val result = operands.reduceIndexed { index, val1, val2 ->
                if (operators[index - 1] == '+')
                    val1 + val2
                else if (operators[index - 1] == '*')
                    val1 * val2
                else
                    (val1.toString() + val2.toString()).toLong()
            }
            result == testValue
        }
    }

    fun solvePart2(equations: List<Equation>): Long =
        equations.filter { it.hasSolutionV2() }.sumOf { it.testValue }

    val testInput = readInput("Day07")
    val equations = parseInput(testInput)
    solvePart1(equations).println()
    solvePart2(equations).println()
}
