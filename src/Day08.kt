fun main() {
    fun parseInput(input: List<String>): Array<CharArray> =
        input.map { it.toCharArray() }.toTypedArray()

    // Part 1
    fun getFrequencies(map: Array<CharArray>): Set<Char> =
        map.flatMap { it.filter { it != '.' } }.toSet()

    fun getLocations(map: Array<CharArray>, frequency: Char): List<Pair<Int, Int>> =
        map.mapIndexedNotNull { row, chars ->
            val col = chars.indexOf(frequency)
            if (col != -1) {
                row to col
            } else null
        }

    fun inBounds(location: Pair<Int, Int>, maxRow: Int, maxCol: Int): Boolean =
        location.first >= 0 && location.second >= 0 && location.first < maxRow && location.second < maxCol

    fun solvePart1(map: Array<CharArray>): Int {
        val maxRow = map.size
        val maxCol = map[0].size
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val frequencies = getFrequencies(map)
        frequencies.forEach {
            val locations = getLocations(map, it)
            locations.indices.forEach { locIndex1 ->
                val loc1 = locations[locIndex1]
                locations.indices.forEach { locIndex2 ->
                    if (locIndex1 != locIndex2) {
                        val loc2 = locations[locIndex2]
                        val difRow = loc2.first - loc1.first
                        val difCol = loc2.second - loc1.second
                        val antiNodeRow1 = loc1.first - difRow
                        val antiNodeCol1 = loc1.second - difCol
                        val antiNodeRow2 = loc2.first + difRow
                        val antiNodeCol2 = loc2.second + difCol
                        if (inBounds(antiNodeRow1 to antiNodeCol1, maxRow, maxCol)) {
                            antiNodes.add(antiNodeRow1 to antiNodeCol1)
                        }
                        if (inBounds(antiNodeRow2 to antiNodeCol2, maxRow, maxCol)) {
                            antiNodes.add(antiNodeRow2 to antiNodeCol2)
                        }
                    }
                }
            }
        }
        return antiNodes.size
    }

    // Part 2
    fun solvePart2(map: Array<CharArray>): Int {
        val maxRow = map.size
        val maxCol = map[0].size
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val frequencies = getFrequencies(map)
        frequencies.forEach {
            val locations = getLocations(map, it)
            locations.indices.forEach { locIndex1 ->
                val loc1 = locations[locIndex1]
                locations.indices.forEach { locIndex2 ->
                    if (locIndex1 != locIndex2) {
                        val loc2 = locations[locIndex2]
                        val difRow = loc2.first - loc1.first
                        val difCol = loc2.second - loc1.second
                        var antiNodeRow1 = loc1.first - difRow
                        var antiNodeCol1 = loc1.second - difCol
                        while (inBounds(antiNodeRow1 to antiNodeCol1, maxRow, maxCol)) {
                            antiNodes.add(antiNodeRow1 to antiNodeCol1)
                            antiNodeRow1 -= difRow
                            antiNodeCol1 -= difCol
                        }
                        var antiNodeRow2 = loc2.first + difRow
                        var antiNodeCol2 = loc2.second + difCol
                        while (inBounds(antiNodeRow2 to antiNodeCol2, maxRow, maxCol)) {
                            antiNodes.add(antiNodeRow2 to antiNodeCol2)
                            antiNodeRow2 += difRow
                            antiNodeCol2 += difCol
                        }
                    }
                }
            }
            if (locations.size > 1) {
                antiNodes.addAll(locations)
            }
        }
        return antiNodes.size
    }

    val testInput = readInput("Day08")
    val map = parseInput(testInput)
    solvePart1(map).println()
    solvePart2(map).println()
}
