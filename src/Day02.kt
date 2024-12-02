fun main() {
    fun parseInput(input: List<String>): List<List<Int>> =
        input.map { line -> line.trim().split(" ").map { level -> level.toInt() } }

    // Part 1
    fun List<Int>.isValidReport(): Boolean =
        windowed(2) { partialReport -> partialReport[1] - partialReport[0] }.all { (1..3).contains(it) } ||
                windowed(2) { partialReport -> partialReport[0] - partialReport[1] }.all { (1..3).contains(it) }

    fun calcSafeReports(reports: List<List<Int>>): Int =
        reports.count { report -> report.isValidReport() }

    // Part 2
    fun calcSafeReportsWithTolerance(reports: List<List<Int>>): Int =
        reports.count { report ->
            report.isValidReport() || report.indices.any { index ->
                report.filterIndexed { i, _ -> i != index }.isValidReport()
            }
        }

    val testInput = readInput("Day02")
    val reports = parseInput(testInput)
    calcSafeReports(reports).println()
    calcSafeReportsWithTolerance(reports).println()
}
