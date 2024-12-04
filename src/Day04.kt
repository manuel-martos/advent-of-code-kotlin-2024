fun main() {
    fun parseInput(input: List<String>): Array<CharArray> {
        return input.map { it.toCharArray() }.toTypedArray()
    }


    // Part 1
    fun countXMasWord(buffer: Array<CharArray>): Int {
        val maxRow = buffer.size - 4
        val maxCols = buffer[0].size - 4
        var count = 0
        for (row in buffer.indices) {
            for (col in buffer[row].indices) {
                // horizontal
                if (col <= maxCols && buffer[row][col] == 'X' && buffer[row][col + 1] == 'M' && buffer[row][col + 2] == 'A' && buffer[row][col + 3] == 'S') {
                    count++
                }
                if (col <= maxCols && buffer[row][col] == 'S' && buffer[row][col + 1] == 'A' && buffer[row][col + 2] == 'M' && buffer[row][col + 3] == 'X') {
                    count++
                }
                // vertical
                if (row <= maxRow && buffer[row][col] == 'X' && buffer[row + 1][col] == 'M' && buffer[row + 2][col] == 'A' && buffer[row + 3][col] == 'S') {
                    count++
                }
                if (row <= maxRow && buffer[row][col] == 'S' && buffer[row + 1][col] == 'A' && buffer[row + 2][col] == 'M' && buffer[row + 3][col] == 'X') {
                    count++
                }
                // diagonal
                if (col <= maxCols && row <= maxRow && buffer[row][col] == 'X' && buffer[row + 1][col + 1] == 'M' && buffer[row + 2][col + 2] == 'A' && buffer[row + 3][col + 3] == 'S') {
                    count++
                }
                if (col <= maxCols && row <= maxRow && buffer[row][col] == 'S' && buffer[row + 1][col + 1] == 'A' && buffer[row + 2][col + 2] == 'M' && buffer[row + 3][col + 3] == 'X') {
                    count++
                }
                // inverted diagonal
                if (col <= maxCols && row <= maxRow && buffer[row][col + 3] == 'X' && buffer[row + 1][col + 2] == 'M' && buffer[row + 2][col + 1] == 'A' && buffer[row + 3][col] == 'S') {
                    count++
                }
                if (col <= maxCols && row <= maxRow && buffer[row][col + 3] == 'S' && buffer[row + 1][col + 2] == 'A' && buffer[row + 2][col + 1] == 'M' && buffer[row + 3][col] == 'X') {
                    count++
                }
            }
        }
        return count
    }

    // Part 2
    fun countXMasShape(buffer: Array<CharArray>): Int {
        val maxRow = buffer.size - 3
        val maxCols = buffer[0].size - 3
        var count = 0
        for (row in buffer.indices) {
            for (col in buffer[row].indices) {
                // M S
                //  A
                // M S
                if (col <= maxCols && row <= maxRow && buffer[row][col] == 'M' && buffer[row][col + 2] == 'S' && buffer[row + 1][col + 1] == 'A' && buffer[row + 2][col] == 'M' && buffer[row + 2][col + 2] == 'S') {
                    count++
                }
                // S S
                //  A
                // M M
                if (col <= maxCols && row <= maxRow && buffer[row][col] == 'S' && buffer[row][col + 2] == 'S' && buffer[row + 1][col + 1] == 'A' && buffer[row + 2][col] == 'M' && buffer[row + 2][col + 2] == 'M') {
                    count++
                }
                // S M
                //  A
                // S M
                if (col <= maxCols && row <= maxRow && buffer[row][col] == 'S' && buffer[row][col + 2] == 'M' && buffer[row + 1][col + 1] == 'A' && buffer[row + 2][col] == 'S' && buffer[row + 2][col + 2] == 'M') {
                    count++
                }
                // M M
                //  A
                // S S
                if (col <= maxCols && row <= maxRow && buffer[row][col] == 'M' && buffer[row][col + 2] == 'M' && buffer[row + 1][col + 1] == 'A' && buffer[row + 2][col] == 'S' && buffer[row + 2][col + 2] == 'S') {
                    count++
                }
            }
        }
        return count
    }

    val testInput = readInput("Day04")
    val buffer = parseInput(testInput)
    countXMasWord(buffer).println()
    countXMasShape(buffer).println()

}
