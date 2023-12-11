fun main() {
    fun extrapolate(numbers: List<Long>, left: Boolean = false): Long {
        val steps = mutableListOf<Long>().apply {
            if (left) {
                add(0, numbers.first())
            } else {
                add(0, numbers.last())
            }
        }
        val diffs = numbers.toMutableList()
        do {
            val asd = mutableListOf<Long>()
            for (i in 1..diffs.indices.last) {
                asd.add(diffs[i] - diffs[i - 1])
            }
            if (left) {
                steps.add(0, asd.first())
            } else {
                steps.add(0, asd.last())
            }
            diffs.apply {
                clear()
                addAll(asd)
            }
        } while (diffs.any { it != 0L })

        if (left) {
            return steps.fold(0L) { a,b -> b - a}
        }

        return steps.fold(0L) { acc, l -> acc + l }
    }

    fun part1(input: List<String>): Long {
        return input.map { it.split(" ").map(String::toLong) }.sumOf { extrapolate(it) }
    }

    fun part2(input: List<String>): Long {
        return input.map { it.split(" ").map(String::toLong) }.sumOf { extrapolate(it, true) }
    }

    val input = readInput("Day09")
//    part1(input).println()
    part2(input).println()
}