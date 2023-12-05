fun main() {
    data class Number(
        val value: Int,
        val row: Int,
        val columns: IntRange
    )

    fun extractNumbers(
        map: List<List<Char>>
    ): MutableSet<Number> {
        val numbers = mutableSetOf<Number>()
        var rangeStart = -1
        var number = ""
        for (i in map.indices) {
            for (j in map[i].indices) {
                if (j == 0 && number.isNotBlank()) {
                    numbers.add(
                        Number(number.toInt(), i - 1, rangeStart .. map[i].lastIndex)
                    )
                    rangeStart = -1
                    number = ""
                }

                val char = map[i][j]

                if (char.isDigit()) {
                    if (rangeStart == -1) {
                        rangeStart = j
                    }
                    number += char
                } else if (rangeStart != -1) {
                    numbers.add(
                        Number(number.toInt(), i, rangeStart until j)
                    )
                    rangeStart = -1
                    number = ""
                }
            }
        }
        if (number.isNotBlank()) {
            numbers.add(
                Number(number.toInt(), map.lastIndex, rangeStart..map[map.lastIndex].lastIndex)
            )
        }
        return numbers
    }

    fun part1(input: List<String>): Int {
        val map = input.map { line -> line.map { it } }

        val numbers = extractNumbers(map)

        val parts = mutableSetOf<Number>()
        for (i in map.indices) {
            for (j in map[i].indices) {
                val char = map[i][j]
                if (char.isDigit() || char == '.') continue

                numbers.firstOrNull {
                    it.row == i && it.columns.contains(j - 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i && it.columns.contains(j + 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i - 1 && it.columns.contains(j)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i + 1 && it.columns.contains(j)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i - 1 && it.columns.contains(j - 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i - 1 && it.columns.contains(j + 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i + 1 && it.columns.contains(j - 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i + 1 && it.columns.contains(j + 1)
                }?.also(parts::add)
            }
        }
        return parts.sumOf(Number::value)
    }

    fun part2(input: List<String>): Int {
        val map = input.map { line -> line.map { it } }

        val numbers = extractNumbers(map)

        val ratios = mutableListOf<Int>()

        for (i in map.indices) {
            for (j in map[i].indices) {
                val char = map[i][j]
                if (char != '*') continue

                val parts = mutableSetOf<Number>()

                numbers.firstOrNull {
                    it.row == i && it.columns.contains(j - 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i && it.columns.contains(j + 1)
                }?.also(parts::add)

                numbers.firstOrNull {
                    it.row == i - 1 && it.columns.contains(j)
                }?.also(parts::add)

                if (parts.size > 2) continue

                numbers.firstOrNull {
                    it.row == i + 1 && it.columns.contains(j)
                }?.also(parts::add)

                if (parts.size > 2) continue

                numbers.firstOrNull {
                    it.row == i - 1 && it.columns.contains(j - 1)
                }?.also(parts::add)

                if (parts.size > 2) continue

                numbers.firstOrNull {
                    it.row == i - 1 && it.columns.contains(j + 1)
                }?.also(parts::add)

                if (parts.size > 2) continue

                numbers.firstOrNull {
                    it.row == i + 1 && it.columns.contains(j - 1)
                }?.also(parts::add)

                if (parts.size > 2) continue

                numbers.firstOrNull {
                    it.row == i + 1 && it.columns.contains(j + 1)
                }?.also(parts::add)

                if (parts.size != 2) continue

                ratios.add(parts.map(Number::value).reduce { acc, n -> acc * n })
            }
        }

        return ratios.sum()
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}