import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val regex = "\\s+".toRegex()
        val locationPairs = input.map {
            val parts = it.trim().split(regex)
            if (parts.size >= 2) {
                parts[0].toInt() to parts[1].toInt()
            } else {
                throw IllegalArgumentException()
            }
        }
        mutableListOf<Int>()
        return locationPairs.fold(mutableListOf<Int>() to mutableListOf<Int>()) { acc, pair ->
            acc.first.add(pair.first)
            acc.second.add(pair.second)
            acc
        }
    }

    // Part 1
    fun calcDistanceScore(leftLocations: List<Int>, rightLocations: List<Int>): Int =
        leftLocations.sorted().zip(rightLocations.sorted()).sumOf { abs(it.first - it.second) }

    // Part 2
    fun calcSimilarityScore(leftLocations: List<Int>, rightLocations: List<Int>): Int =
        leftLocations.sumOf { leftLocation -> leftLocation * rightLocations.filter { it == leftLocation }.size }

    val testInput = readInput("Day01")
    val (leftLocations, rightLocations) = parseInput(testInput)
    calcDistanceScore(leftLocations, rightLocations).println()
    calcSimilarityScore(leftLocations, rightLocations).println()
}
