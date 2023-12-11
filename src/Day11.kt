data class Galaxy(val number: Int, val x: Long, val y: Long)

fun main() {
    fun parseGalaxies(input: List<String>, n: Int = 2) : List<Galaxy> {
        val newInput = mutableListOf<String>()

        val rows = input.indices.map(Int::toLong).associateWith { it }.toMutableMap()
        val cols = input[0].indices.map(Int::toLong).associateWith { it }.toMutableMap()

        input.forEachIndexed { index, s ->
            if (s.all { it == '.' }) {
                for (i in index.toLong() .. input.lastIndex) {
                    rows[i] = rows[i]!! + n - 1
                }
            }
        }

        input[0].indices.forEach { j ->
            val column = input.map { line -> line[j] }
            if (column.all { it == '.' }) {
                for (i in j.toLong() .. input[0].lastIndex) {
                    cols[i] = cols[i]!! + n - 1
                }
            }
        }

        val galaxies = mutableListOf<Galaxy>()
        var number = 1
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '#') {
                    galaxies.add(Galaxy(number++, rows[row.toLong()]!!, cols[col.toLong()]!!))
                }
            }
        }

        return galaxies
    }

    fun sumDistances(input: List<String>, n: Int = 2): Long {
        val galaxies = parseGalaxies(input, n)

        var distanceSumX = 0L
        var rowSum = 0L
        galaxies.map { it.x }
            .forEachIndexed { index, x ->
                distanceSumX += index * x - rowSum
                rowSum += x
            }

        var distanceSumY = 0L
        var colSum = 0L
        galaxies
            .sortedBy { it.y }
            .map { it.y }
            .forEachIndexed { index, y ->
                distanceSumY += index * y - colSum
                colSum += y
            }

        return distanceSumX + distanceSumY
    }

    fun part1(input: List<String>): Long {
        return sumDistances(input, 2)
    }

    fun part2(input: List<String>): Long {
        return sumDistances(input, 1_000_000)
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}