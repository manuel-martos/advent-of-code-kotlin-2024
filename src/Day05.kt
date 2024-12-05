data class PrintProtocol(
    val rules: Set<Pair<Int, Int>>,
    val ordering: List<List<Int>>,
)

fun main() {
    fun parseInput(input: List<String>): PrintProtocol {
        val regex = """(\d+)\|(\d+)""".toRegex()
        val rules = mutableSetOf<Pair<Int, Int>>()
        val ordering = mutableListOf<List<Int>>()
        var parsingRules = true
        input.forEach { line ->
            if (parsingRules) {
                if (line.isNotEmpty()) {
                    val match = regex.find(line)!!
                    rules.add(match.groupValues[1].toInt() to match.groupValues[2].toInt())
                } else {
                    parsingRules = false
                }
            } else {
                ordering.add(line.trim().split(",").map { level -> level.toInt() })
            }
        }
        return PrintProtocol(
            rules = rules,
            ordering = ordering,
        )
    }

    // Part 1
    fun List<Int>.isValid(rules: Set<Pair<Int, Int>>): Boolean =
        this.windowed(2).all { pair -> rules.contains(pair[0] to pair[1]) }

    fun calcFirstPart(printProtocol: PrintProtocol): Int =
        printProtocol.ordering.filter { it.isValid(printProtocol.rules) }.sumOf { it[it.size / 2] }

    // Part 2
    fun List<Int>.findIndicesToFix(rules: Set<Pair<Int, Int>>): Pair<Int, Int> =
        rules
            .filter { contains(it.first) && contains(it.second) }
            .map { indexOf(it.first) to this.indexOf(it.second) }
            .first { it.first > it.second }

    fun calcSecondPart(printProtocol: PrintProtocol): Int =
        printProtocol.ordering
            .filter { !it.isValid(printProtocol.rules) }
            .map {
                val order = it.toMutableList()
                while (!order.isValid(printProtocol.rules)) {
                    val rule = order.findIndicesToFix(printProtocol.rules)
                    val temp = order[rule.first]
                    order[rule.first] = order[rule.second]
                    order[rule.second] = temp
                }
                order
            }
            .sumOf { it[it.size / 2] }

    val testInput = readInput("Day05")
    val printProtocol = parseInput(testInput)
    calcFirstPart(printProtocol).println()
    calcSecondPart(printProtocol).println()
}
