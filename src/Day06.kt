fun CharArray.replace(oldChar: Char, newChar: Char): CharArray {
    // Create a copy of the original CharArray to avoid mutating it
    val result = this.copyOf()

    // Iterate through the array and replace characters
    for (i in result.indices) {
        if (result[i] == oldChar) {
            result[i] = newChar
        }
    }

    return result
}

fun main() {
    fun parseInput(input: List<String>): Array<CharArray> =
        input.map { it.toCharArray() }.toTypedArray()

    // Part 1
    fun getCurrentGuardPosition(map: Array<CharArray>): Pair<Int, Int> =
        map.mapIndexedNotNull { index, chars ->
            if (chars.contains('^') || chars.contains('v') || chars.contains('<') || chars.contains('>')) {
                index to chars.indexOfFirst { it == '^' || it == 'v' || it == '<' || it == '>' }
            } else {
                null
            }
        }.first()

    fun inBounds(map: Array<CharArray>, position: Pair<Int, Int>): Boolean =
        position.first in map.indices && position.second in map[0].indices

    fun nextStep(map: Array<CharArray>, currentGuardPosition: Pair<Int, Int>): Pair<Pair<Int, Int>, Char> {
        val guardRow = currentGuardPosition.first
        val guardCol = currentGuardPosition.second
        var guardSymbol = map[guardRow][guardCol]
        val maxRow = map.size - 1
        val maxCol = map[0].size - 1
        var moveForward = false
        var nextPosition: Pair<Int, Int>
        do {
            nextPosition = when {
                guardSymbol == '^' && (guardRow == 0 || map[guardRow - 1][guardCol] == '.') ->
                    guardRow - 1 to guardCol.also { moveForward = true }

                guardSymbol == '^' && map[guardRow - 1][guardCol] != '.' ->
                    guardRow to guardCol.also { guardSymbol = '>' }

                guardSymbol == '>' && (guardCol == maxCol || map[guardRow][guardCol + 1] == '.') ->
                    guardRow to guardCol + 1.also { moveForward = true }

                guardSymbol == '>' && map[guardRow][guardCol + 1] != '.' ->
                    guardRow to guardCol.also { guardSymbol = 'v' }

                guardSymbol == 'v' && (guardRow == maxRow || map[guardRow + 1][guardCol] == '.') ->
                    guardRow + 1 to guardCol.also { moveForward = true }

                guardSymbol == 'v' && map[guardRow + 1][guardCol] != '.' ->
                    guardRow to guardCol.also { guardSymbol = '<' }

                guardSymbol == '<' && (guardCol == 0 || map[guardRow][guardCol - 1] == '.') ->
                    guardRow to guardCol - 1.also { moveForward = true }

                guardSymbol == '<' && map[guardRow][guardCol - 1] != '.' ->
                    guardRow to guardCol.also { guardSymbol = '^' }

                else -> throw IllegalStateException()
            }
            if (!moveForward) {
                map[guardRow][guardCol] = guardSymbol
            } else if (inBounds(map, nextPosition)) {
                map[nextPosition.first][nextPosition.second] = guardSymbol
            }
        } while (!moveForward)
        map[guardRow][guardCol] = '.'
        return nextPosition to guardSymbol
    }

    fun isGuardInMap(currentMap: Array<CharArray>): Boolean =
        currentMap.any { it.contains('^') || it.contains('v') || it.contains('<') || it.contains('>') }

    fun updateMap(
        map: Array<CharArray>,
        oldGuardPosition: Pair<Int, Int>,
        newGuardPosition: Pair<Int, Int>,
        guardSymbol: Char
    ) {
        map[oldGuardPosition.first][oldGuardPosition.second] = '.'
        if (inBounds(map, newGuardPosition)) {
            map[newGuardPosition.first][newGuardPosition.second] = guardSymbol
        }
    }

    fun solvePart1(map: Array<CharArray>): Int {
        val visitedPositions = mutableSetOf<Pair<Int, Int>>()
        do {
            val currentGuardPosition = getCurrentGuardPosition(map)
            val (newGuardPosition, newGuardSymbol) = nextStep(map, currentGuardPosition)
            updateMap(map, currentGuardPosition, newGuardPosition, newGuardSymbol)
            visitedPositions.add(currentGuardPosition)
        } while (isGuardInMap(map))
        return visitedPositions.size
    }

    // Part 2
    fun checkIfLoop(map: Array<CharArray>): Boolean {
        val visitedPositions = mutableSetOf<Pair<Pair<Int, Int>, Char>>()
        var currentGuardPosition = getCurrentGuardPosition(map)
        do {
            val (newGuardPosition, newGuardSymbol) = nextStep(map, currentGuardPosition)
            updateMap(map, currentGuardPosition, newGuardPosition, newGuardSymbol)
            val newVisitedWithGuardSymbol = newGuardPosition to newGuardSymbol
            if (!visitedPositions.contains(newVisitedWithGuardSymbol)) {
                visitedPositions.add(newVisitedWithGuardSymbol)
                currentGuardPosition = newGuardPosition
            } else {
                return true
            }
        } while (isGuardInMap(map))
        return false
    }

    // This is a really slow implementation.
    fun solvePart2(originalMap: Array<CharArray>): Int {
        var count = 0
        val initialGuardPosition = getCurrentGuardPosition(originalMap)
        originalMap.indices.forEach { rowIndex ->
            originalMap[rowIndex].indices.forEach { colIndex ->
                val workingMap = originalMap.map { it.copyOf() }.toTypedArray()
                val newObstaclePosition = rowIndex to colIndex
                if (workingMap[rowIndex][colIndex] == '.' && initialGuardPosition != newObstaclePosition) {
                    workingMap[rowIndex][colIndex] = '#'
                    val result = checkIfLoop(workingMap)
                    if (result) {
                        count++
                    }
                }
            }
        }
        return count
    }

    val testInput = readInput("Day06")
    solvePart1(parseInput(testInput)).println()
    solvePart2(parseInput(testInput)).println()
}
